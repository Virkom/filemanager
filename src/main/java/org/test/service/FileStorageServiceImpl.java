package org.test.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.test.model.FileInfo;
import org.test.response.FileUploadResponse;
import org.test.utils.PathUtils;

public class FileStorageServiceImpl implements FileStorageService {

    private final FileInfoStorage fileInfoStorage = new FileInfoLocalStorage();
    private final HashCalculator hashCalculator = new Md5HashCalculator();

    @Override
    public String saveFile(File tmpFile, String contentType, String contentDisposition) throws IOException {

        FileNameService fileNameService = new UuidFileNameService();
        String filename = fileNameService.generateNameByContentDisposition(contentDisposition);
        String ref = FilenameUtils.getBaseName(filename);

        String hash = hashCalculator.getHash(tmpFile);
        FileInfo fileInfo = new FileInfo(hash, ref, filename, tmpFile.length());
        FileInfo result = fileInfoStorage.addFileInfo(fileInfo);

        FileUploadResponse response;

        if (Objects.isNull(result)) {
            File file = new File(PathUtils.PATH_TO_FILES + filename);
            Files.copy(tmpFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.delete(tmpFile.toPath());
            response = new FileUploadResponse(ref, contentType, file.length());
        } else {
            response = new FileUploadResponse(result.getRef(), contentType, result.getSize());
        }

        return response.toJson();
    }

    @Override
    public Optional<FileInfo> getFileInfoByRef(String ref) {
        return fileInfoStorage.findByRef(ref);
    }

    @Override
    public String deleteFile(String ref) {
        String result = null;
        try {
            Optional<FileInfo> fileInfo = getFileInfoByRef(ref);
            if (fileInfo.isEmpty()) {
                throw new NoSuchFileException("No such file exists");
            }
            Files.deleteIfExists(Paths.get(PathUtils.PATH_TO_FILES + fileInfo.get().getName()));
            fileInfoStorage.deleteByHash(fileInfo.get().getHash());
        } catch (NoSuchFileException e) {
            // logger should be here
            result = "No such file exists";
        } catch (IOException e) {
            // logger should be here
            result = "Something went wrong";
        }

        return result;
    }

    @Override
    public void fillStorageOnApplicationStart() {
        File folder = new File(PathUtils.PATH_TO_FILES);
        File[] listOfFiles = folder.listFiles();

        if (Objects.isNull(listOfFiles) || listOfFiles.length == 0) {
            return;
        }

        Arrays.stream(listOfFiles)
                .filter(File::isFile)
                .forEach(file -> {
                    try {
                        String hash = hashCalculator.getHash(file);

                        FileInfo fileInfo = new FileInfo(
                                hash,
                                FilenameUtils.getBaseName(file.getName()),
                                file.getName(),
                                file.length()
                        );

                        fileInfoStorage.addFileInfo(fileInfo);
                    } catch (IOException e) {
                        // logger should be here
                    }
                });
    }
}

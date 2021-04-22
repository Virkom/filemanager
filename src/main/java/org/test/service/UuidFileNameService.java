package org.test.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.test.utils.PathUtils;

public class UuidFileNameService implements FileNameService {

    @Override
    public String generateNameByContentDisposition(String contentDisposition) {
        String fileName = "default";
        Pattern p = Pattern.compile(".+filename=\"(.+?)\".*");

        if (Objects.nonNull(contentDisposition)) {
            Matcher m = p.matcher(contentDisposition);
            if (m.find()) {
                fileName = m.group(1);
            }
        }

        String extension = FilenameUtils.getExtension(fileName);
        String ref = UUID.randomUUID().toString();

        if (extension.isEmpty()) {
            return ref;
        }

        return ref + "." + extension;
    }

    @Override
    public String getRefByFilename(String filename) {
        return FilenameUtils.getBaseName(filename);
    }

    @Override
    public String getFileNameByRef(String ref) throws FileNotFoundException {
        File folder = new File(PathUtils.PATH_TO_FILES);
        File[] listOfFiles = folder.listFiles();

        if (Objects.isNull(listOfFiles) || listOfFiles.length == 0) {
            throw new FileNotFoundException("File " + ref + " not found");
        }

        Optional<File> file = Arrays.stream(listOfFiles)
                .filter(it -> ref.equals(FilenameUtils.getBaseName(it.getName())))
                .findFirst();

        if (file.isPresent()) {
            return file.get().getName();
        } else {
            throw new FileNotFoundException("File " + ref + " not found");
        }
    }
}

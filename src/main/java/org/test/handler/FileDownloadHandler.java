package org.test.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.PathTemplateMatch;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;

import org.test.model.FileInfo;
import org.test.service.FileStorageService;
import org.test.utils.PathUtils;

/**
 * Handle file downloading request
 */
public class FileDownloadHandler implements HttpHandler {

    private final FileStorageService fileStorageService;

    public FileDownloadHandler(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) {

        PathTemplateMatch pathTemplateMatch = httpServerExchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
        String ref = pathTemplateMatch.getParameters().get(PathUtils.FILE_ID_PLACEHOLDER);

        String result = null;

        Optional<FileInfo> fileInfo = fileStorageService.getFileInfoByRef(ref);
        if (fileInfo.isPresent()) {

            String fileName = fileInfo.get().getName();
            try {
                httpServerExchange.setStatusCode(200);
                httpServerExchange.getResponseHeaders()
                        .put(Headers.CONTENT_TYPE, "application/octet-stream")
                        .put(Headers.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

                File file = new File(PathUtils.PATH_TO_FILES + fileName);
                OutputStream outputStream = httpServerExchange.getOutputStream();
                InputStream inputStream = new FileInputStream(file);

                byte[] buf = new byte[8192];
                int c;
                while ((c = inputStream.read(buf, 0, buf.length)) > 0) {
                    outputStream.write(buf, 0, c);
                    outputStream.flush();
                }

                outputStream.close();
                inputStream.close();

            } catch (FileNotFoundException e) {
                // logger should be here
                result = "No such file exists";
            } catch (IOException e) {
                // logger should be here
                result = "Something went wrong";
            }
        } else {
            result = "No such file exists";
        }

        if (Objects.nonNull(result)) {
            httpServerExchange.setStatusCode(400);
            httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            httpServerExchange.getResponseSender().send(result);
        }
    }
}

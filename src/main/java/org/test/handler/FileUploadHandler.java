package org.test.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.Headers;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.test.response.FileUploadResponse;
import org.test.service.FileNameService;
import org.test.service.UuidFileNameService;
import org.test.utils.PathUtils;

/**
 * Handle file uploading request
 */
public class FileUploadHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) {

        FormData attachment = httpServerExchange.getAttachment(FormDataParser.FORM_DATA);
        FormData.FormValue fileValue = attachment.get(PathUtils.FILE_FORM_DATA_PARAM).getFirst();

        Path tempFile = fileValue.getFileItem().getFile();

        String result;
        String contentType;
        try {
            FileNameService fileNameService = new UuidFileNameService();
            String filename = fileNameService.generateNameByContentDisposition(fileValue.getHeaders().get("Content-Disposition").getFirst());
            String ref = fileNameService.getRefByFilename(filename);

            String origContentType = fileValue.getHeaders().get("Content-Type").getFirst();
            long size = fileValue.getFileItem().getFileSize();

            Path file = new File(PathUtils.PATH_TO_FILES + filename).toPath();
            Files.copy(tempFile, file, StandardCopyOption.REPLACE_EXISTING);
            Files.delete(tempFile);

            FileUploadResponse response = new FileUploadResponse(ref, origContentType, size);
            result = response.toJson();
            contentType = "application/json";
            httpServerExchange.setStatusCode(200);
        } catch (IOException e) {
            // logger should be here
            httpServerExchange.setStatusCode(400);
            result = "Something went wrong.";
            contentType = "text/plain";
        }

        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, contentType);
        httpServerExchange.getResponseSender().send(result);
    }
}

package org.test.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.Headers;
import java.io.File;
import java.io.IOException;

import org.test.service.FileStorageService;
import org.test.utils.PathUtils;

/**
 * Handle file uploading request
 */
public class FileUploadHandler implements HttpHandler {

    private final FileStorageService fileStorageService;

    public FileUploadHandler(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) {

        FormData attachment = httpServerExchange.getAttachment(FormDataParser.FORM_DATA);
        FormData.FormValue fileValue = attachment.get(PathUtils.FILE_FORM_DATA_PARAM).getFirst();

        File tmpFile = fileValue.getFileItem().getFile().toFile();

        String result;
        String contentType;
        try {
            String origContentType = fileValue.getHeaders().get("Content-Type").getFirst();
            String contentDisposition = fileValue.getHeaders().get("Content-Disposition").getFirst();
            result = fileStorageService.saveFile(tmpFile, origContentType, contentDisposition);

            contentType = "application/json";
            httpServerExchange.setStatusCode(200);
        } catch (IOException e) {
            // logger should be here
            httpServerExchange.setStatusCode(400);
            result = "Something went wrong";
            contentType = "text/plain";
        }

        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, contentType);
        httpServerExchange.getResponseSender().send(result);
    }
}

package org.test.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.PathTemplateMatch;
import java.util.Objects;

import org.test.service.FileStorageService;
import org.test.utils.PathUtils;

/**
 * Handle file deleting request
 */
public class FileDeleteHandler implements HttpHandler {

    private final FileStorageService fileStorageService;

    public FileDeleteHandler(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) {
        PathTemplateMatch pathTemplateMatch = httpServerExchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
        String ref = pathTemplateMatch.getParameters().get(PathUtils.FILE_ID_PLACEHOLDER);

        String result = fileStorageService.deleteFile(ref);

        if (Objects.nonNull(result)) {
            httpServerExchange.setStatusCode(400);
            httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            httpServerExchange.getResponseSender().send(result);
        } else {
            httpServerExchange.setStatusCode(204);
        }
    }
}

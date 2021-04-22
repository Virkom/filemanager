package org.test.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.PathTemplateMatch;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Objects;

import org.test.service.FileNameService;
import org.test.service.UuidFileNameService;
import org.test.utils.PathUtils;

/**
 * Handle file deleting request
 */
public class FileDeleteHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) {
        PathTemplateMatch pathTemplateMatch = httpServerExchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
        String ref = pathTemplateMatch.getParameters().get(PathUtils.FILE_ID_PLACEHOLDER);

        FileNameService fileNameService = new UuidFileNameService();

        String result = null;
        try {
            String fileName = fileNameService.getFileNameByRef(ref);
            Files.deleteIfExists(Paths.get(PathUtils.PATH_TO_FILES + fileName));
        } catch (NoSuchFileException e) {
            // logger should be here
            result = "No such file exists";
        } catch (IOException e) {
            // logger should be here
            result = "Something went wrong";
        }

        if (Objects.nonNull(result)) {
            httpServerExchange.setStatusCode(400);
            httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            httpServerExchange.getResponseSender().send(result);
        }

        httpServerExchange.setStatusCode(204);
    }
}

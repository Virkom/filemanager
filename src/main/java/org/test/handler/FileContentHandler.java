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

import org.test.service.FileNameService;
import org.test.service.UuidFileNameService;
import org.test.utils.PathUtils;

/**
 * Handle file downloading request
 */
public class FileContentHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) {

        PathTemplateMatch pathTemplateMatch = httpServerExchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
        String ref = pathTemplateMatch.getParameters().get(PathUtils.FILE_ID_PLACEHOLDER);

        FileNameService fileNameService = new UuidFileNameService();

        String result = null;
        try {
            String fileName = fileNameService.getFileNameByRef(ref);

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

        if (Objects.nonNull(result)) {
            httpServerExchange.setStatusCode(400);
            httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            httpServerExchange.getResponseSender().send(result);
        }
    }
}

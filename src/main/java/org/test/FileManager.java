package org.test;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.form.EagerFormParsingHandler;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.server.handlers.form.MultiPartParserDefinition;

import org.test.handler.FileContentHandler;
import org.test.handler.FileDeleteHandler;
import org.test.handler.FileUploadHandler;
import org.test.handler.PageNotFoundHandler;
import org.test.utils.PathUtils;

/**
 * Simple HTTP server for storing and serving arbitrary files in the local filesystem
 */
public class FileManager {

    public static void main(String[] args) {

        HttpHandler routes = new RoutingHandler()
                .post(PathUtils.FILE_UPLOAD_PATH, new BlockingHandler(
                        new EagerFormParsingHandler(
                                FormParserFactory.builder()
                                        .addParsers(new MultiPartParserDefinition())
                                        .build()
                        ).setNext(new FileUploadHandler())))
                .get(PathUtils.FILE_DOWNLOAD_PATH, new BlockingHandler(new FileContentHandler()))
                .delete(PathUtils.FILE_DELETE_PATH, new FileDeleteHandler())
                .setFallbackHandler(new PageNotFoundHandler());

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0", routes)
                .build();

        server.start();
    }
}

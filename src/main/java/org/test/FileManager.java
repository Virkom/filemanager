package org.test;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.form.EagerFormParsingHandler;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.server.handlers.form.MultiPartParserDefinition;

import org.test.handler.FileDeleteHandler;
import org.test.handler.FileDownloadHandler;
import org.test.handler.FileUploadHandler;
import org.test.handler.PageNotFoundHandler;
import org.test.service.FileStorageService;
import org.test.service.FileStorageServiceImpl;
import org.test.utils.PathUtils;

/**
 * Simple HTTP server for storing and serving arbitrary files in the local filesystem
 */
public class FileManager {

    public static void main(String[] args) {

        // fill storage when application starts
        FileStorageService fileStorageService = new FileStorageServiceImpl();
        fileStorageService.fillStorageOnApplicationStart();

        HttpHandler routes = new RoutingHandler()
                .post(PathUtils.FILE_UPLOAD_PATH, new BlockingHandler(
                        new EagerFormParsingHandler(
                                FormParserFactory.builder()
                                        .addParsers(new MultiPartParserDefinition())
                                        .build()
                        ).setNext(new FileUploadHandler(fileStorageService))))
                .get(PathUtils.FILE_DOWNLOAD_PATH, new BlockingHandler(new FileDownloadHandler(fileStorageService)))
                .delete(PathUtils.FILE_DELETE_PATH, new FileDeleteHandler(fileStorageService))
                .setFallbackHandler(new PageNotFoundHandler());

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0", routes)
                .build();

        server.start();
    }
}

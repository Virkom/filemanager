package org.test.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * Handle unknown (not implemented) pages
 */
public class PageNotFoundHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) {
        httpServerExchange.setStatusCode(404);
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        httpServerExchange.getResponseSender().send("Page Not Found");
    }
}

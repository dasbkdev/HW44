package kg.attractor.java.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;

public class BaseServer {

    private final HttpServer server;

    public BaseServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", this::handle);
    }

    public void start() {
        server.start();
        System.out.println("http://localhost:" + server.getAddress().getPort());
    }

    private void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.equals("/")) path = "/index.html";

        File file = new File("data" + path);
        if (!file.exists() || file.isDirectory()) {
            byte[] data = "404 Not found".getBytes();
            exchange.sendResponseHeaders(404, data.length);
            exchange.getResponseBody().write(data);
            exchange.close();
            return;
        }

        byte[] data = Files.readAllBytes(file.toPath());
        exchange.getResponseHeaders().add("Content-Type", getContentType(path));
        exchange.sendResponseHeaders(200, data.length);
        exchange.getResponseBody().write(data);
        exchange.close();
    }

    private String getContentType(String path) {
        String p = path.toLowerCase();
        if (p.endsWith(".html") || p.endsWith(".ftl")) return "text/html; charset=utf-8";
        if (p.endsWith(".css")) return "text/css; charset=utf-8";
        if (p.endsWith(".png")) return "image/png";
        if (p.endsWith(".jpg") || p.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }
}
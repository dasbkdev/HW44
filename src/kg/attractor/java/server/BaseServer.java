package kg.attractor.java.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class BaseServer {

    public interface RouteHandler {
        void handle(HttpExchange exchange) throws IOException;
    }

    private final HttpServer server;
    private final Map<String, RouteHandler> routes;
    private final Configuration freemarker;

    public BaseServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        routes = new HashMap<>();
        freemarker = initFreeMarker();

        server.createContext("/", this::handleIncoming);
        registerGet("/", exchange -> sendFile(exchange, "index.html"));
        registerGet("/sample.html", exchange -> sendFile(exchange, "sample.html"));

        registerFileHandler(".css");
        registerFileHandler(".html");
        registerFileHandler(".jpg");
        registerFileHandler(".png");
    }

    public void start() {
        server.start();
        System.out.println("http://localhost:" + server.getAddress().getPort());
    }

    public void registerGet(String path, RouteHandler handler) {
        routes.put(makeKey("GET", path), handler);
    }

    public void registerPost(String path, RouteHandler handler) {
        routes.put(makeKey("POST", path), handler);
    }

    public void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) throws IOException {
        try {
            Template temp = freemarker.getTemplate(templateFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                temp.process(dataModel, writer);
                writer.flush();
                byte[] data = stream.toByteArray();
                sendBytes(exchange, 200, "text/html; charset=utf-8", data);
            }
        } catch (Exception e) {
            byte[] data = "500".getBytes();
            sendBytes(exchange, 500, "text/plain; charset=utf-8", data);
        }
    }

    private void handleIncoming(HttpExchange exchange) throws IOException {
        String key = makeKey(exchange.getRequestMethod(), normalizePath(exchange.getRequestURI().getPath()));
        RouteHandler handler = routes.get(key);
        if (handler != null) {
            handler.handle(exchange);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String ext = getExt(path);
        if (ext != null) {
            RouteHandler fileHandler = routes.get(makeKey(exchange.getRequestMethod(), ext));
            if (fileHandler != null) {
                fileHandler.handle(exchange);
                return;
            }
        }

        byte[] data = "404 Not found".getBytes();
        sendBytes(exchange, 404, "text/plain; charset=utf-8", data);
    }

    private void registerFileHandler(String ext) {
        registerGet(ext, exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";
            sendFile(exchange, path.startsWith("/") ? path.substring(1) : path);
        });
    }

    private void sendFile(HttpExchange exchange, String relativePath) throws IOException {
        File file = new File("data/" + relativePath);
        if (!file.exists() || file.isDirectory()) {
            byte[] data = "404 Not found".getBytes();
            sendBytes(exchange, 404, "text/plain; charset=utf-8", data);
            return;
        }

        byte[] data = Files.readAllBytes(file.toPath());
        sendBytes(exchange, 200, getContentType(relativePath), data);
    }

    private void sendBytes(HttpExchange exchange, int code, String contentType, byte[] data) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", contentType);
        exchange.sendResponseHeaders(code, data.length);
        exchange.getResponseBody().write(data);
        exchange.close();
    }

    private String getContentType(String path) {
        String p = path.toLowerCase();
        if (p.endsWith(".css")) return "text/css; charset=utf-8";
        if (p.endsWith(".html") || p.endsWith(".ftl")) return "text/html; charset=utf-8";
        if (p.endsWith(".png")) return "image/png";
        if (p.endsWith(".jpg") || p.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }

    private String normalizePath(String path) {
        if (path == null || path.isEmpty()) return "/";
        return path;
    }

    private String getExt(String path) {
        int i = path.lastIndexOf('.');
        if (i == -1) return null;
        return path.substring(i).toLowerCase();
    }

    private String makeKey(String method, String pathOrExt) {
        return method + " " + pathOrExt;
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("data"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public java.util.Map<String, String> getFormData(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        return parseQuery(body);
    }

    private java.util.Map<String, String> parseQuery(String query) {
        java.util.Map<String, String> data = new java.util.HashMap<>();
        if (query == null || query.isEmpty()) return data;

        String[] parts = query.split("&");
        for (String part : parts) {
            String[] kv = part.split("=", 2);
            String key = urlDecode(kv[0]);
            String value = kv.length > 1 ? urlDecode(kv[1]) : "";
            data.put(key, value);
        }
        return data;
    }

    private String urlDecode(String s) {
        try {
            return java.net.URLDecoder.decode(s, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            return s;
        }
    }

}
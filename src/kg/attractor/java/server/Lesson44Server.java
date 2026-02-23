package kg.attractor.java.server;

import com.sun.net.httpserver.HttpExchange;
import kg.attractor.java.model.Book;
import kg.attractor.java.service.LibraryService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lesson44Server {

    private final BaseServer server;
    private final LibraryService libraryService;

    public Lesson44Server() throws IOException {
        server = new BaseServer(9889);
        libraryService = new LibraryService();
        registerRoutes();
    }

    public void start() {
        server.start();
    }

    private void registerRoutes() {
        server.registerGet("/books", this::handleBooks);
        server.registerGet("/book", this::handleBook);
    }

    private void handleBooks(HttpExchange exchange) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("books", libraryService.getBooks());
        data.put("library", libraryService);
        server.renderTemplate(exchange, "books.ftl", data);
    }

    private void handleBook(HttpExchange exchange) throws IOException {
        String idStr = getQueryParam(exchange, "id");
        int id = idStr == null ? 1 : Integer.parseInt(idStr);

        Book book = libraryService.getBookById(id);

        Map<String, Object> data = new HashMap<>();
        data.put("book", book);
        data.put("library", libraryService);
        server.renderTemplate(exchange, "book.ftl", data);
    }

    private String getQueryParam(HttpExchange exchange, String name) {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || query.isEmpty()) return null;

        String[] parts = query.split("&");
        for (String part : parts) {
            String[] kv = part.split("=");
            if (kv.length == 2 && kv[0].equals(name)) return kv[1];
        }
        return null;
    }
}
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
        server.registerGet("/employees", this::handleEmployees);
        server.registerGet("/employee", this::handleEmployee);
        server.registerGet("/register", this::handleRegisterGet);
        server.registerPost("/register", this::handleRegisterPost);
        server.registerGet("/login", this::handleLoginGet);
        server.registerPost("/login", this::handleLoginPost);
        server.registerGet("/profile", this::handleProfileGet);
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

    private void handleEmployees(HttpExchange exchange) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("employees", libraryService.getEmployees());
        server.renderTemplate(exchange, "employees.ftl", data);
    }

    private void handleEmployee(HttpExchange exchange) throws IOException {
        String idStr = getQueryParam(exchange, "id");
        int id = idStr == null ? 1 : Integer.parseInt(idStr);

        Map<String, Object> data = new HashMap<>();
        data.put("employee", libraryService.getEmployeeById(id));
        data.put("library", libraryService);
        server.renderTemplate(exchange, "employee.ftl", data);
    }
    private void handleRegisterGet(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        server.renderTemplate(exchange, "register.ftl", data);
    }

    private void handleRegisterPost(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        java.util.Map<String, String> form = server.getFormData(exchange);

        String identifier = form.get("identifier");
        String fullName = form.get("fullName");
        String password = form.get("password");

        boolean ok = libraryService.registerUser(identifier, fullName, password);

        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("success", ok);
        data.put("identifier", identifier == null ? "" : identifier);
        data.put("fullName", fullName == null ? "" : fullName);

        server.renderTemplate(exchange, "register_result.ftl", data);
    }

    private void handleLoginGet(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("error", "");
        server.renderTemplate(exchange, "login.ftl", data);
    }

    private void handleLoginPost(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        java.util.Map<String, String> form = server.getFormData(exchange);

        String identifier = form.get("identifier");
        String password = form.get("password");

        var user = libraryService.login(identifier, password);

        if (user == null) {
            java.util.Map<String, Object> data = new java.util.HashMap<>();
            data.put("error", "User does not exist or wrong password");
            server.renderTemplate(exchange, "login.ftl", data);
            return;
        }

        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("user", user);
        server.renderTemplate(exchange, "profile.ftl", data);
    }

    private void handleProfileGet(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        var user = libraryService.getLastLoggedInUser();

        java.util.Map<String, Object> data = new java.util.HashMap<>();
        if (user == null) {
            data.put("user", new kg.attractor.java.model.Employee(0, "unknown@user", "Some user", ""));
        } else {
            data.put("user", user);
        }
        server.renderTemplate(exchange, "profile.ftl", data);
    }

}
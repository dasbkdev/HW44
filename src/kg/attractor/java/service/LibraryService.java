package kg.attractor.java.service;

import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryService {

    private final java.util.List<kg.attractor.java.model.Employee> users = new java.util.ArrayList<>();
    private int nextUserId = 1;
    private final List<Employee> employees;
    private final List<Book> books;
    private kg.attractor.java.model.Employee lastLoggedInUser;
    private final Map<String, Employee> sessions = new HashMap<>();

    public LibraryService() {
        employees = new ArrayList<>();
        employees.add(new Employee(1, "Ivan Ivanov", Arrays.asList(2), Arrays.asList(1)));
        employees.add(new Employee(2, "Petr Petrov", Arrays.asList(3), Arrays.asList()));

        books = new ArrayList<>();
        books.add(new Book(1, "Java Basics", "James Gosling", "images/1.jpg", "available", null));
        books.add(new Book(2, "Clean Code", "Robert Martin", "images/1.jpg", "issued", 1));
        books.add(new Book(3, "Effective Java", "Joshua Bloch", "images/1.jpg", "issued", 2));
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee getEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) return employee;
        }
        return null;
    }

    public String getEmployeeNameById(Integer id) {
        if (id == null) return "";
        for (Employee employee : employees) {
            if (employee.getId() == id) return employee.getFullName();
        }
        return "";
    }

    public boolean registerUser(String identifier, String fullName, String password) {
        if (identifier == null || identifier.isEmpty()) return false;
        if (fullName == null || fullName.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;

        for (kg.attractor.java.model.Employee user : users) {
            if (identifier.equalsIgnoreCase(user.getIdentifier())) return false;
        }

        users.add(new kg.attractor.java.model.Employee(nextUserId++, identifier, fullName, password));
        return true;
    }

    public kg.attractor.java.model.Employee login(String identifier, String password) {
        if (identifier == null || password == null) return null;

        for (kg.attractor.java.model.Employee user : users) {
            if (identifier.equalsIgnoreCase(user.getIdentifier()) && password.equals(user.getPassword())) {
                lastLoggedInUser = user;
                return user;
            }
        }
        return null;
    }

    public kg.attractor.java.model.Employee getLastLoggedInUser() {
        return lastLoggedInUser;
    }

    public String createSession(Employee user) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, user);
        return sessionId;
    }

    public Employee getUserBySession(String sessionId) {
        if (sessionId == null) return null;
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        if (sessionId == null) return;
        sessions.remove(sessionId);
    }

    public int countIssuedBooksToUser(int userId) {
        int count = 0;
        for (Book book : books) {
            Integer issuedTo = book.getIssuedToEmployeeId();
            if (issuedTo != null && issuedTo == userId) count++;
        }
        return count;
    }

    public boolean issueBook(int bookId, int userId) {
        Book book = getBookById(bookId);
        if (book == null) return false;

        if (book.getIssuedToEmployeeId() != null) return false;

        if (countIssuedBooksToUser(userId) >= 2) return false;

        book.setIssuedToEmployeeId(userId);
        book.setStatus("issued");
        return true;
    }

    public boolean returnBook(int bookId, int userId) {
        Book book = getBookById(bookId);
        if (book == null) return false;

        Integer issuedTo = book.getIssuedToEmployeeId();
        if (issuedTo == null) return false;
        if (issuedTo != userId) return false;

        book.setIssuedToEmployeeId(null);
        book.setStatus("available");
        return true;
    }

}
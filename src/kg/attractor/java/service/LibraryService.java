package kg.attractor.java.service;

import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LibraryService {

    private final List<Employee> users = new ArrayList<>();
    private int nextUserId = 1;

    private final List<Employee> employees;
    private final List<Book> books;

    private Employee lastLoggedInUser;
    private final Map<String, Employee> sessions = new HashMap<>();

    private final Map<Integer, List<Integer>> userCurrentBooks = new HashMap<>();
    private final Map<Integer, List<Integer>> userPastBooks = new HashMap<>();

    public LibraryService() {
        employees = new ArrayList<>();
        employees.add(new Employee(1, "Ivan Ivanov", Arrays.asList(2), Arrays.asList(1)));
        employees.add(new Employee(2, "Petr Petrov", Arrays.asList(3), Arrays.asList()));

        books = new ArrayList<>();
        books.add(new Book(1, "Java Basics", "James Gosling", "Java language basics for beginners.", "images/1.jpg", "available", null));
        books.add(new Book(2, "Clean Code", "Robert Martin", "A handbook of agile software craftsmanship.", "images/1.jpg", "issued", 1));
        books.add(new Book(3, "Effective Java", "Joshua Bloch", "Best practices for the Java platform.", "images/1.jpg", "issued", 2));
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
        for (Employee user : users) {
            if (user.getId() == id) return user.getFullName();
        }
        return "";
    }

    public boolean registerUser(String identifier, String fullName, String password) {
        if (identifier == null || identifier.isEmpty()) return false;
        if (fullName == null || fullName.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;

        for (Employee user : users) {
            if (identifier.equalsIgnoreCase(user.getIdentifier())) return false;
        }

        Employee user = new Employee(nextUserId++, identifier, fullName, password);
        users.add(user);

        userCurrentBooks.put(user.getId(), new ArrayList<>());
        userPastBooks.put(user.getId(), new ArrayList<>());

        return true;
    }

    public Employee login(String identifier, String password) {
        if (identifier == null || password == null) return null;

        for (Employee user : users) {
            if (identifier.equalsIgnoreCase(user.getIdentifier()) && password.equals(user.getPassword())) {
                lastLoggedInUser = user;
                return user;
            }
        }
        return null;
    }

    public Employee getLastLoggedInUser() {
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

        userCurrentBooks.putIfAbsent(userId, new ArrayList<>());
        userPastBooks.putIfAbsent(userId, new ArrayList<>());

        List<Integer> current = userCurrentBooks.get(userId);
        if (!current.contains(bookId)) current.add(bookId);

        List<Integer> past = userPastBooks.get(userId);
        if (!past.contains(bookId)) past.add(bookId);

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

        List<Integer> current = userCurrentBooks.get(userId);
        if (current != null) current.remove((Integer) bookId);

        return true;
    }

    public List<Book> getCurrentBooksForUser(int userId) {
        List<Integer> ids = userCurrentBooks.get(userId);
        List<Book> result = new ArrayList<>();
        if (ids == null) return result;

        for (Integer id : ids) {
            Book book = getBookById(id);
            if (book != null) result.add(book);
        }
        return result;
    }

    public List<Book> getPastBooksForUser(int userId) {
        List<Integer> ids = userPastBooks.get(userId);
        List<Book> result = new ArrayList<>();
        if (ids == null) return result;

        for (Integer id : ids) {
            Book book = getBookById(id);
            if (book != null) result.add(book);
        }
        return result;
    }

    public boolean isUserRegistered(int userId) {
        for (Employee user : users) {
            if (user.getId() == userId) return true;
        }
        return false;
    }
}
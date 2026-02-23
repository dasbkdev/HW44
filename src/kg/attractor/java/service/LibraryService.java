package kg.attractor.java.service;

import kg.attractor.java.model.Book;
import kg.attractor.java.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {

    private final List<Employee> employees;
    private final List<Book> books;

    public LibraryService() {
        employees = new ArrayList<>();
        employees.add(new Employee(1, "Ivan Ivanov"));
        employees.add(new Employee(2, "Petr Petrov"));

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

    public String getEmployeeNameById(Integer id) {
        if (id == null) return "";
        for (Employee employee : employees) {
            if (employee.getId() == id) return employee.getFullName();
        }
        return "";
    }
}
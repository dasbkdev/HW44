package kg.attractor.java.model;

import java.util.List;

public class Employee {
    private int id;
    private String fullName;
    private List<Integer> currentBooks;
    private List<Integer> pastBooks;
    private String identifier;
    private String password;

    public Employee(int id, String fullName, List<Integer> currentBooks, List<Integer> pastBooks) {
        this.id = id;
        this.fullName = fullName;
        this.currentBooks = currentBooks;
        this.pastBooks = pastBooks;
    }

    public int getId() {
        return id;
    }

    public Employee(int id, String identifier, String fullName, String password) {
        this.id = id;
        this.identifier = identifier;
        this.fullName = fullName;
        this.password = password;
        this.currentBooks = java.util.List.of();
        this.pastBooks = java.util.List.of();
    }

    public String getFullName() {
        return fullName;
    }

    public List<Integer> getCurrentBooks() {
        return currentBooks;
    }

    public List<Integer> getPastBooks() {
        return pastBooks;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;}
}


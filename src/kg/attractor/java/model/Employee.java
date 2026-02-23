package kg.attractor.java.model;

import java.util.List;

public class Employee {
    private int id;
    private String fullName;
    private List<Integer> currentBooks;
    private List<Integer> pastBooks;

    public Employee(int id, String fullName, List<Integer> currentBooks, List<Integer> pastBooks) {
        this.id = id;
        this.fullName = fullName;
        this.currentBooks = currentBooks;
        this.pastBooks = pastBooks;
    }

    public int getId() {
        return id;
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
}
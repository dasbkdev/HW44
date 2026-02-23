package kg.attractor.java.model;

public class Book {

    private int id;
    private String title;
    private String author;
    private String image;
    private String status;

    public Book(int id, String title, String author, String image, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }
}
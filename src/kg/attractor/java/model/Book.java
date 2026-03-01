package kg.attractor.java.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String image;
    private String status;
    private Integer issuedToEmployeeId;

    public Book(int id, String title, String author, String image, String status, Integer issuedToEmployeeId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.status = status;
        this.issuedToEmployeeId = issuedToEmployeeId;
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

    public Integer getIssuedToEmployeeId() {
        return issuedToEmployeeId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIssuedToEmployeeId(Integer issuedToEmployeeId) {
        this.issuedToEmployeeId = issuedToEmployeeId;
    }
}
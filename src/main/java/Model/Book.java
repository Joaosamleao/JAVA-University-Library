package Model;

public class Book {
    
    private Integer idBook;

    private String title;
    private String author;
    private int publishedYear;
    private String category;
    private String isbn;

    private boolean available;

    public Book() {

    }

    public Book(String title, String author, int publishedYear, String category, String isbn) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.category = category;
        this.isbn = isbn;
    }

    // Getters
    public Integer getIdBook() { return idBook; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublishedYear() { return publishedYear; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }
    public boolean getAvailable() { return available; }

    // Setters
    public void setIdBook(Integer idBook) { this.idBook = idBook; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }
    public void setCategory(String category) { this.category = category; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAvailable(boolean available) { this.available = available; }

}
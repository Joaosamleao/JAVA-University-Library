package Model;

public class Book {
    
    private int idBook;

    private String author;
    private String publishedYear;
    private String category;
    private String isbn;

    private boolean available;

    public Book(String author, String publishedYear, String category, String isbn) {
        this.author = author;
        this.publishedYear = publishedYear;
        this.category = category;
        this.isbn = isbn;
    }

    // Getters
    public int getIdBook() { return idBook; }
    public String getAuthor() { return author; }
    public String getPublishedYear() { return publishedYear; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }
    public boolean getAvailable() { return available; }

    // Setters
    public void setIdBook(int idBook) { this.idBook = idBook; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublishedYear(String publishedYear) { this.publishedYear = publishedYear; }
    public void setCategory(String category) { this.category = category; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAvailable(boolean available) { this.available = available; }

}
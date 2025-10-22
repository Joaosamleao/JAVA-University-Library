package DTO;

public class BookUpdateDTO {
    
    private final String title;
    private final String author;
    private final int publishedYear;
    private final String category;
    private final String isbn;

    public BookUpdateDTO(String title, String author, int publishedYear, String category, String isbn) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.category = category;
        this.isbn = isbn;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublishedYear() { return publishedYear; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }

}

package Model;

import java.time.LocalDate;

public class Loan {
    
    private Long idLoan;

    private User user;
    private Book book;
    
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    
    public Loan(User user, Book book) {
        if (user == null || book == null) {
            throw new IllegalArgumentException("User or Book cannot be null!");
        }
        this.user = user;
        this.book = book;
        this.loanDate = LocalDate.now();
        this.expectedReturnDate = this.loanDate.plusDays(15);
    }

    // Getters
    public Long getIdLoan() { return idLoan; }
    public User getrUser() { return user; }
    public Book getBook() { return book; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getExpectedReturnDate() { return expectedReturnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }

    // Setters
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }

}

package Model;

import java.time.LocalDate;

public class Loan {
    
    private Integer idLoan;

    private final User user;
    private final Book book;
    
    private final LocalDate loanDate;
    private final LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    
    public Loan(User user, Book book) {
        this.user = user;
        this.book = book;
        this.loanDate = LocalDate.now();
        this.expectedReturnDate = this.loanDate.plusDays(15);
    }

    // Getters
    public Integer getIdLoan() { return idLoan; }
    public User getrUser() { return user; }
    public Book getBook() { return book; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getExpectedReturnDate() { return expectedReturnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }

    // Setters
    public void setidLoan(Integer idLoan) { this.idLoan = idLoan; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }

}

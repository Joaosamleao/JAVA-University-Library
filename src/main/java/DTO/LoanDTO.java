package DTO;

import java.time.LocalDate;

public class LoanDTO {

    private Integer userId;
    private Integer copyId;
    
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;

    public LoanDTO() {
        
    }

    public LoanDTO(Integer userId, Integer copyId) {
        this.userId = userId;
        this.copyId = copyId;
        this.loanDate = LocalDate.now();
        this.expectedReturnDate = this.loanDate.plusDays(15);
    }

    // Getters
    public Integer getUserId() { return userId; }
    public Integer getCopyId() { return copyId; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getExpectedReturnDate() { return expectedReturnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }

    // Setters
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setCopyId(Integer copyId) { this.copyId = copyId; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }
    public void setExpectedReturnDate(LocalDate expectedDate) { this.expectedReturnDate = expectedDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }

}

package DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FineDTO {
    
    private Integer loanId;
    private Integer userId;

    private BigDecimal amount;

    private LocalDate issueDate;
    private LocalDate paymentDate;

    public FineDTO() {

    }

    public FineDTO(Integer loanId, Integer userId, BigDecimal amount) {
        this.loanId = loanId;
        this.userId = userId;
        this.amount = amount;
        this.issueDate = LocalDate.now();
    }

    //Getters
    public Integer getIdUser() { return userId; }
    public Integer getLoan() { return loanId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getPaymentDate() { return paymentDate; }

    //Setters
    public void setIdLoan(Integer loanId) { this.loanId = loanId; }
    public void setIdUser(Integer userId) { this.userId = userId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

}

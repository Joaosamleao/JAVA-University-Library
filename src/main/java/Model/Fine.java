package Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Fine {
    
    private Integer idFine;
    private Integer loanId;
    private Integer userId;

    private BigDecimal amount;

    private LocalDate issueDate;
    private LocalDate paymentDate;

    public Fine() {

    }

    public Fine(Integer loanId, Integer userId, BigDecimal amount) {
        this.loanId = loanId;
        this.userId = userId;
        this.amount = amount;
        this.issueDate = LocalDate.now();
    }

    //Getters
    public Integer getIdFine() { return idFine; }
    public Integer getIdUser() { return userId; }
    public Integer getLoan() { return loanId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getPaymentDate() { return paymentDate; }

    //Setters
    public void setIdFine(Integer idFine) { this.idFine = idFine; }
    public void setIdLoan(Integer loanId) { this.loanId = loanId; }
    public void setIdUser(Integer userId) { this.userId = userId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

}

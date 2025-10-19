package Model;

import java.time.LocalDate;

public class Fine {
    
    private Integer idFine;
    private final Loan loan;
    private final double amount;
    private final LocalDate issueDate;

    public Fine(Loan loan, double amount) {
        this.loan = loan;
        this.amount = amount;
        this.issueDate = LocalDate.now();
    }

    //Getters
    public Integer getIdFine() { return idFine; }
    public Loan getLoan() { return loan; }
    public double getAmount() { return amount; }
    public LocalDate getIssueDate() { return issueDate; }

    //Setters
    public void setIdFine(Integer idFine) { this.idFine = idFine; }

}

package Model;

import java.time.LocalDate;

public class Fine {
    
    private Long idFine;
    private Loan loan;
    private double amount;
    private LocalDate issueDate;

    public Fine(Loan loan, double amount) {
        this.loan = loan;
        this.amount = amount;
        this.issueDate = LocalDate.now();
    }

    //Getters
    public Long getIdFine() { return idFine; }
    public Loan getLoan() { return loan; }
    public double getAmount() { return amount; }
    public LocalDate getIssueDate() { return issueDate; }

    //Setters

}

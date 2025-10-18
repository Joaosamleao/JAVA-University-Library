package Model;

import java.time.LocalDate;

public class Fine {
    
    private int idFine;
    private Loan loan;
    private double amount;
    private LocalDate issueDate;

    public Fine(Loan loan, double amount) {
        this.loan = loan;
        this.amount = amount;
        this.issueDate = LocalDate.now();
    }

    //Getters
    public int getIdFine() { return idFine; }
    public Loan getLoan() { return loan; }
    public double getAmount() { return amount; }
    public LocalDate getIssueDate() { return issueDate; }

    //Setters
    public void setIdFine(int idFine) { this.idFine = idFine; }

}

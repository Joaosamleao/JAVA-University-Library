package View.Panels.LoanPanels;

import javax.swing.JPanel;

import Controller.LoanController;

public class ActiveLoansPanel extends JPanel {
    
    private final LoanController loanController;

    public ActiveLoansPanel(LoanController loanController) {
        this.loanController = loanController;
    }

}

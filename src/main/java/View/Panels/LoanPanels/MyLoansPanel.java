package View.Panels.LoanPanels;

import javax.swing.JPanel;

import Controller.LoanController;

public class MyLoansPanel extends JPanel {
    
    private final LoanController loanController;

    public MyLoansPanel(LoanController loanController) {
        this.loanController = loanController;
    }

}

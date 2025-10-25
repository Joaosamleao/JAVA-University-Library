package View.Panels.LoanPanels;

import javax.swing.JPanel;

import Controller.LoanController;

public class LoanDetailsPanel extends JPanel {
    
    private final LoanController loanController;

    public LoanDetailsPanel(LoanController loanController) {
        this.loanController = loanController;
    }

}

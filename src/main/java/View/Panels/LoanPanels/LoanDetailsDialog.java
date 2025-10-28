package View.Panels.LoanPanels;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.LoanController;

public class LoanDetailsDialog extends JDialog {
    
    private final Integer loanId;
    private final LoanController loanController;

    private JTextField actualReturnDateField;
    private JButton saveButton;
    private JButton cancelButton;

    private boolean isSaved = false;

    public LoanDetailsDialog(Frame parent, boolean modal, Integer loanId, LoanController loanController) {
        super(parent, modal);
        this.loanController = loanController;
        this.loanId = loanId;
        initComponents();
    }

    private void initComponents() {
        setTitle("Conclude Loan");
        setSize(350, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Return Date:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        actualReturnDateField = new JTextField(15); add(actualReturnDateField, gbc);
        
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(buttonPanel, gbc);

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());

        pack();
    }

    private void onSave() {
        System.out.println("On save is called with ID: " + loanId);
        String dateString = actualReturnDateField.getText().trim();

        if (dateString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "WARNING: All fields are mandatory", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate convertedDate = loanController.formatDateString(dateString);
        if (convertedDate == null) {
            this.isSaved = false;
            return;
        } 
        boolean success = loanController.completeLoan(loanId, convertedDate);
        
        if (success) {
            this.isSaved = true;
            this.dispose();
        } else {
            this.isSaved = false;
        }
        
    }

    private void onCancel() {
        this.dispose();
        this.isSaved = false;
    }

    public boolean isSaved() {
        return isSaved;
    }

}

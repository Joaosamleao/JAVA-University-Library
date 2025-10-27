package View.Panels.LoanPanels;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.BookCopyController;
import Controller.LoanController;
import Controller.UserController;
import DTO.LoanDTO;

public class LoanCreatePanel extends JPanel {
    
    private final LoanController loanController;
    private final UserController userController;
    private final BookCopyController copyController;

    private JTextField userRegistrationField;
    private JTextField copyBarcodeField;
    private JButton saveButton;
    private JButton cancelButton;

    public LoanCreatePanel(LoanController loanController, UserController userController, BookCopyController copyController) {
        this.loanController = loanController;
        this.userController = userController;
        this.copyController = copyController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Register a New Loan"), gbc);
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("User Registration:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        userRegistrationField = new JTextField(30); add(userRegistrationField, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Copy Barcode:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        copyBarcodeField = new JTextField(30); add(copyBarcodeField, gbc);
        
        saveButton = new JButton("Register Loan");
        cancelButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);

        add(buttonPanel, gbc);

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());
    }

    private void onSave() {
        Integer userId = userController.findUserByRegistration(userRegistrationField.getText().trim()).getIdUser();
        Integer copyId = copyController.requestCopyByBarcode(copyBarcodeField.getText().trim()).getCopyId();

        boolean hasEmptyOrNull = Stream.of(userId, copyId).anyMatch(s -> s == null);
        if (hasEmptyOrNull) {
            JOptionPane.showMessageDialog(this, "WARNING: All fields are mandatory", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LoanDTO loanData = new LoanDTO(userId, copyId);
        loanController.createLoanRequest(loanData);
    }

    private void onCancel() {
        userRegistrationField.setText("");
        copyBarcodeField.setText("");
    }

}

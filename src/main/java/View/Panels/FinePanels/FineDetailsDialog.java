package View.Panels.FinePanels;

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

import Controller.FineController;
import DTO.FineDTO;

public class FineDetailsDialog extends JDialog {
    
    private final Integer fineId;
    private final FineController fineController;

    private JTextField paymentDateField;
    private JButton saveButton;
    private JButton cancelButton;

    private boolean isSaved = false;

    public FineDetailsDialog(Frame parent, boolean modal, Integer fineId, FineController fineController) {
        super(parent, modal);
        this.fineId = fineId;
        this.fineController = fineController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Pay fine");
        setSize(350, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Return Date:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        paymentDateField = new JTextField(15); add(paymentDateField, gbc);
        
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
        String dateString = paymentDateField.getText().trim();

        if (dateString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "WARNING: All fields are mandatory", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate convertedDate = fineController.formatDateString(dateString);
        FineDTO fineData = new FineDTO();
        fineData.setPaymentDate(convertedDate);
        fineController.completeFine(fineId, fineData);

        this.isSaved = true;
        this.dispose();
    }

    private void onCancel() {
        this.isSaved = false;
        this.dispose();
    }

    public boolean isSaved() {
        return isSaved;
    }

}

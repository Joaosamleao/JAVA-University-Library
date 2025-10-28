package View.Panels.BookCopyPanels;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.BookCopyController;
import DTO.CopyDTO;

public class BookCopyEditDialog extends JDialog {
    
    private final Integer copyId;
    private final BookCopyController copyController;

    private JTextField barcodeField;
    private JTextField locationCodeField;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;

    private boolean isSaved = false;

    public BookCopyEditDialog(Frame parent, boolean modal, Integer copyId, BookCopyController copyController) {
        super(parent, modal);
        this.copyId = copyId;
        this.copyController = copyController;
        initComponents();
        fillExistingData();
    }

    private void initComponents() {
        setTitle("Update Copy Info");
        setSize(350, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Barcode:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        barcodeField = new JTextField(15); add(barcodeField, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Location code:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        locationCodeField = new JTextField(15); add(locationCodeField, gbc);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(buttonPanel, gbc);

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());
        deleteButton.addActionListener(e -> onDelete());

        pack();
    }

    private void fillExistingData() {
        CopyDTO existingData = copyController.requestCopyById(copyId);
        barcodeField.setText(existingData.getBarcode());
        locationCodeField.setText(existingData.getLocationCode());
    }

    private void onSave() {
        String barcode = barcodeField.getText().trim();
        String locationCode = locationCodeField.getText().trim();

        if (barcode.isEmpty() || locationCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "WARNING: All fields are mandatory");
            return;
        }

        CopyDTO copyData = new CopyDTO(barcode, locationCode);
        boolean success = copyController.requestCopyEdit(copyId, copyData);

        if (success) {
            this.isSaved = true;
            this.dispose();
        } else {
            this.isSaved = false;
        }
        
    }

    private void onCancel() {
        this.isSaved = false;
        this.dispose();
    }

    private void onDelete() {
        copyController.requestDelete(copyId);
    }

    public boolean isSaved() {
        return isSaved;
    }

}

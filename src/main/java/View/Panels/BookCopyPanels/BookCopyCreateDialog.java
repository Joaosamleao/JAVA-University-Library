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
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;

public class BookCopyCreateDialog extends JDialog {
    
    private final BookCopyController copyController;
    private final Integer bookId;

    private JTextField barcodeField;
    private JTextField locationCodeField;
    private JButton saveButton;
    private JButton cancelButton;

    private boolean isSaved = false;


    public BookCopyCreateDialog(Frame parent, boolean modal, Integer bookId, BookCopyController copyController) {
        super(parent, modal);
        this.bookId = bookId;
        this.copyController = copyController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Add New Copy");
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, gbc);

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());

        pack();
    }

    private void onSave() {

        String barcode = barcodeField.getText().trim();
        String locationCode = locationCodeField.getText().trim();

        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "WARNING: A barcode is mandatory");
            return;
        }

        try {
            copyController.createCopyRequest(bookId, barcode, locationCode);
            this.isSaved = true;
            this.dispose();
        } catch (DataCreationException | BusinessRuleException e) {
            JOptionPane.showMessageDialog(this, "ERROR: Couldn't save copy: " + e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            this.isSaved = false;
        } catch (DataAccessException e) {
            JOptionPane.showMessageDialog(this, "UNEXPECTED ERROR: Couldn't save to database", "Fatal Error", JOptionPane.ERROR_MESSAGE);
            this.isSaved = false;
        }

    }

    private void onCancel() {
        this.isSaved = false;
        this.dispose();
    }

    public boolean isSaved() {
        return isSaved;
    }

}

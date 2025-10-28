package View.Panels.BookPanels;

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

import Controller.BookController;
import DTO.BookDTO;

public class BookCreatePanel extends JPanel {

    
    
    private final BookController bookController;

    private JTextField titleField;
    private JTextField authorField;
    private JTextField publishedYearField;
    private JTextField categoryField;
    private JTextField isbnField;
    private JButton saveButton;
    private JButton cancelButton;

    public BookCreatePanel(BookController bookController) {
        this.bookController = bookController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Register a New Book"), gbc);
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        titleField = new JTextField(30); add(titleField, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Author:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; authorField = new JTextField(30); add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Year:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; publishedYearField = new JTextField(4); add(publishedYearField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; categoryField = new JTextField(30); add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; isbnField = new JTextField(30); add(isbnField, gbc);

        saveButton = new JButton("Save Book");
        cancelButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);

        add(buttonPanel, gbc);

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());
    }

    private void onSave() {

        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String publishedYear = publishedYearField.getText().trim();
        String category = categoryField.getText().trim();
        String isbn = isbnField.getText().trim();

        boolean hasEmptyOrNull = Stream.of(title, author, publishedYear, category, isbn).anyMatch(s -> s == null || s.isEmpty());
        if (hasEmptyOrNull) {
            JOptionPane.showMessageDialog(this, "WARNING: All fields are mandatory", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer publishedYearInt = bookController.checkPublishedYear(publishedYear);
        BookDTO bookDTO = new BookDTO(title, author, publishedYearInt, category, isbn);

        boolean success = bookController.createBookRequest(bookDTO);

        if (success) {
            onCancel();
        }

        onCancel();
    }

    private void onCancel() {
        titleField.setText("");
        authorField.setText("");
        publishedYearField.setText("");
        categoryField.setText("");
        isbnField.setText("");
    }

}

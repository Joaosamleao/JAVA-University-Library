package View.Panels.BookPanels;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.BookController;
import DTO.BookDTO;

public class BookEditDialog extends JDialog {
    
    private final Integer bookId;
    private final BookController bookController;

    private JTextField titleField;
    private JTextField authorField;
    private JTextField publishedYearField;
    private JTextField categoryField;
    private JTextField isbnField;
    private JButton saveButton;
    private JButton cancelButton;

    private boolean isSaved = false;

    public BookEditDialog(Frame parent, boolean modal, Integer bookId, BookController bookController) {
        super(parent, modal);
        this.bookId = bookId;
        this.bookController = bookController;
        initComponents();
        fillExistingData();
    }

    private void initComponents() {
        setTitle("Edit Book Info");
        setSize(350, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        titleField = new JTextField(15); add(titleField, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Author:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        authorField = new JTextField(15); add(authorField, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Published Year:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        publishedYearField = new JTextField(15); add(publishedYearField, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        categoryField = new JTextField(15); add(categoryField, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        isbnField = new JTextField(15); add(isbnField, gbc);
        
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

    private void fillExistingData() {
        BookDTO existingData = bookController.requestBookById(bookId);
        titleField.setText(existingData.getTitle());
        authorField.setText(existingData.getAuthor());
        publishedYearField.setText(String.valueOf(existingData.getPublishedYear()));
        categoryField.setText(existingData.getCategory());
        isbnField.setText(existingData.getIsbn());
    }

    private void onSave() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String publishedYear = publishedYearField.getText().trim();
        String category = categoryField.getText().trim();
        String isbn = isbnField.getText().trim();

        boolean hasEmptyOrNull = Stream.of(title, author, publishedYear, category, isbn).anyMatch(s -> s == null || s.isEmpty());
        if (hasEmptyOrNull) {
            JOptionPane.showMessageDialog(this, "WARNING: All fields are mandatory");
            return;
        }

        int publishedYearInt = Integer.parseInt(publishedYear);

        BookDTO bookData = new BookDTO(title, author, publishedYearInt, category, isbn);
        bookController.requestBookEdit(bookId, bookData);
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

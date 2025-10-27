package View.Panels.BookPanels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Controller.BookController;
import Controller.BookCopyController;
import DTO.BookDTO;
import DTO.UserDTO;
import Model.Enum.UserType;
import View.Panels.BookCopyPanels.BookCopyCreateDialog;
import View.Panels.BookCopyPanels.BookCopyEditDialog;

public class BookDetailsPanel extends JPanel {
    
    private final BookController bookController;
    private final BookCopyController copyController;
    private final UserDTO user;

    private BookDTO currentBook;
    private Integer currentBookId;

    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel publishedYearLabel;
    private JLabel categoryLabel;
    private JLabel isbnLabel;

    private JTable copiesTable;
    private DefaultTableModel copiesTableModel;

    private JButton editButton;
    private JButton addCopyButton;

    public BookDetailsPanel(BookController bookController, BookCopyController copyController, UserDTO user) {
        this.bookController = bookController;
        this.user = user;
        this.copyController = copyController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel bookInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        titleLabel = new JLabel();
        authorLabel = new JLabel();
        publishedYearLabel = new JLabel();
        categoryLabel = new JLabel();
        isbnLabel = new JLabel();

        bookInfoPanel.add(new JLabel("Title")); bookInfoPanel.add(titleLabel);
        bookInfoPanel.add(new JLabel("Author:")); bookInfoPanel.add(authorLabel);
        bookInfoPanel.add(new JLabel("Published Year:")); bookInfoPanel.add(publishedYearLabel);
        bookInfoPanel.add(new JLabel("Category:")); bookInfoPanel.add(categoryLabel);
        bookInfoPanel.add(new JLabel("ISBN:")); bookInfoPanel.add(isbnLabel);
        add(bookInfoPanel, BorderLayout.NORTH);

        String[] copyColumnNames = {"Copy ID", "Barcode", "Status", "Location Code"};
        copiesTableModel = new DefaultTableModel(copyColumnNames, 0);
        copiesTable = new JTable(copiesTableModel);
        JScrollPane scrollPane = new JScrollPane(copiesTable);
        add(scrollPane, BorderLayout.CENTER);

        copiesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (user.getUserType() == UserType.LIBRARIAN || e.getClickCount() == 2) {
                    int selectedRow = copiesTable.getSelectedRow();
                    if (selectedRow != -1) {
                        Integer copyId = (Integer) copiesTableModel.getValueAt(selectedRow, 0);
                        openEditCopyDialog(copyId);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        editButton = new JButton("Edit Book");
        addCopyButton = new JButton("Add Copy");


        if (user.getUserType() == UserType.LIBRARIAN) {
            editButton.setVisible(true);
            addCopyButton.setVisible(true);

            editButton.addActionListener(e -> {
                if (currentBookId != null) {
                    openEditBookDialog();
                }
            });

            addCopyButton.addActionListener(e -> {
                if (currentBookId != null) {
                    openCreateCopyDialog();
                }
            });
        } else {
            editButton.setVisible(false);
            addCopyButton.setVisible(false);
        }
        buttonPanel.add(editButton);
        buttonPanel.add(addCopyButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadCopiesForCurrentBook() {
        try {
            System.out.print("loadCopies é chamado");
            List<Object[]> copyData = copyController.loadCopiesRequest(currentBookId);
            copiesTableModel.setRowCount(0);
            for (Object[] row : copyData) {
                copiesTableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "WARNING WARNING WARNING");
        }
    }

    public void loadBookDetails(Integer bookId) {
        System.out.println("Book ID became: " + bookId);
        this.currentBookId = bookId;
        try {
            currentBook = bookController.requestBookById(bookId);
            titleLabel.setText(currentBook.getTitle());
            authorLabel.setText(currentBook.getAuthor());
            publishedYearLabel.setText(String.valueOf(currentBook.getPublishedYear()));
            categoryLabel.setText(currentBook.getCategory());
            isbnLabel.setText(currentBook.getIsbn());

            loadCopiesForCurrentBook();
        } catch (Exception e) {
        }
    }

    private void openCreateCopyDialog() {
        System.out.println("Create Copy is Called");
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        BookCopyCreateDialog createDialog = new BookCopyCreateDialog(parentFrame, true, currentBookId, copyController);

        createDialog.setVisible(true);
        if (createDialog.isSaved()) {
            loadCopiesForCurrentBook();
            JOptionPane.showMessageDialog(this, "Succesfully added new copy", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void openEditBookDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        BookEditDialog editDialog = new BookEditDialog(parentFrame, true, currentBookId, bookController);

        editDialog.setVisible(true);
        if (editDialog.isSaved()) {
            loadBookDetails(currentBookId);
            JOptionPane.showMessageDialog(this, "Succesfully updated book info", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void openEditCopyDialog(Integer copyId) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        BookCopyEditDialog editDialog = new BookCopyEditDialog(parentFrame, true, copyId, copyController);

        editDialog.setVisible(true);
        if (editDialog.isSaved()) {
            loadCopiesForCurrentBook();
            JOptionPane.showMessageDialog(this, "Succesfully updated copy info", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

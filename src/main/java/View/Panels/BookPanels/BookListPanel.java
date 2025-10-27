package View.Panels.BookPanels;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.BookController;

public class BookListPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;

    private final BookController controller;

    public BookListPanel(BookController controller) {
        this.controller = controller;
        initComponents();
        loadBooks();
    }

    public final void loadBooks() {
        List<Object[]> bookData = controller.loadBooksRequest();
        displayBooks(bookData);
    }


    // Define as entidades do tipo Book e coloca-os dentro da tableModel
    public void displayBooks(List<Object[]> bookData) {
        tableModel.setRowCount(0);
        if (bookData != null) {
            for (Object[] rowData : bookData) {
                tableModel.addRow(rowData);
            }
        }
    }


    // Inicializa os componentes
    // Adiciona um listener que será utilizado para redirecionar o usuário para a View BookDetailsView
    private void initComponents() {
        this.setLayout(new BorderLayout(0, 10));
        String[] columns = {"ID", "Title", "Author", "Published Year", "Category", "ISBN"};
        tableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse Clicked is Called");
                if (e.getClickCount() == 2) {
                    System.out.println("Double click detected");
                    int selectedRow = table.rowAtPoint(e.getPoint());
                    System.out.print("Row at point: " + selectedRow);
                    if (selectedRow != -1) {
                        System.out.println("Request Details is Called");
                        Integer bookId = (Integer) tableModel.getValueAt(selectedRow, 0);
                        controller.requestBookDetailsView(bookId);
                    }
                }
            }
        });

        loadButton = new JButton("Reload Collection");
        loadButton.addActionListener(e -> loadBooks());
        add(loadButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

}

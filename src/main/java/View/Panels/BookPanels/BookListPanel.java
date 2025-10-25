package View.Panels.BookPanels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
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
    }

    public void loadBooks() {
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
        String[] columns = {"ID", "Title", "Author", "Published Year", "Category", "ISBN"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Integer bookId = (Integer) tableModel.getValueAt(selectedRow, 0);
                        controller.requestBookDetailsView(bookId);
                    }
                }
            }
        });
    }

    // Define que a tela será rolável
    //JScrollPane scrollPane = new JScrollPane(table);
    
}

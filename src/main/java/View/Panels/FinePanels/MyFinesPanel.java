package View.Panels.FinePanels;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import Controller.FineController;
import DTO.UserDTO;

public class MyFinesPanel extends JPanel {

    private final FineController fineController;
    private final UserDTO user;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;

    public MyFinesPanel(FineController fineController, UserDTO user) {
        this.fineController = fineController;
        this.user = user;
        initComponents();
        loadFines();
    }

    public final void loadFines() {
        List<Object[]> fineData = fineController.loadFinesByUser(user.getIdUser());
        displayFines(fineData);
    }

    public void displayFines(List<Object[]> fineData) {
        tableModel.setRowCount(0);
        if (fineData != null) {
            for (Object[] rowData : fineData) {
                tableModel.addRow(rowData);
            }
        }
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(0, 10));
        String[] columns = {"Fine ID", "User Registration", "Issue Date", "Amount"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        loadButton = new JButton("Reload Fines");
        loadButton.addActionListener(e -> loadFines());
        add(loadButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

}

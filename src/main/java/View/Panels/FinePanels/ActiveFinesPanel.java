package View.Panels.FinePanels;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Controller.FineController;

public class ActiveFinesPanel extends JPanel {
    
    private final FineController controller;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;

    public ActiveFinesPanel(FineController controller) {
        this.controller = controller;
        initComponents();
    }

    public void loadFines() {
        List<Object[]> fineData = controller.loadFinesRequest();
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
        String[] columns = {"Fine ID", "Loan ID", "User Registration", "Amount", "Issue Date"};
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
                if (e.getClickCount() == 2) {
                    int selectedRow = table.rowAtPoint(e.getPoint());
                    if (selectedRow != -1) {
                        Integer fineId = (Integer) tableModel.getValueAt(selectedRow, 0);
                        openFineDetailsDialog(fineId);
                    }
                }
            }
        });

        loadButton = new JButton("Reload Fines");
        loadButton.addActionListener(e -> loadFines());
        add(loadButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void openFineDetailsDialog(Integer id) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        FineDetailsDialog detailsDialog = new FineDetailsDialog(parentFrame, true, id, controller);

        detailsDialog.setVisible(true);
        if (detailsDialog.isSaved()) {
            loadFines();
            JOptionPane.showMessageDialog(this, "Succesfully closed fine", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}

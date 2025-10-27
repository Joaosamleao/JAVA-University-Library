package View.Panels.LoanPanels;

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

import Controller.LoanController;

public class ActiveLoansPanel extends JPanel {
    
    private final LoanController controller;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;

    public ActiveLoansPanel(LoanController controller) {
        this.controller = controller;
        initComponents();
    }

    public void loadLoans() {
        List<Object[]> loanData = controller.loadLoansRequest();
        displayLoans(loanData);
    }

    public void displayLoans(List<Object[]> loanData) {
        tableModel.setRowCount(0);
        if (loanData != null) {
            for (Object[] rowData : loanData) {
                tableModel.addRow(rowData);
            }
        }
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(0, 10));
        String[] columns = {"Loan ID", "User Registration", "Barcode", "Loan Date", "Expected Return Date"};
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
                        Integer loanId = (Integer) tableModel.getValueAt(selectedRow, 0);
                        System.out.print("Active loans got ID: " + loanId);
                        openLoanDetailsDialog(loanId);
                    }
                }
            }
        });

        loadButton = new JButton("Reload Loans");
        loadButton.addActionListener(e -> loadLoans());
        add(loadButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void openLoanDetailsDialog(Integer id) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        LoanDetailsDialog detailsDialog = new LoanDetailsDialog(parentFrame, true, id, controller);

        detailsDialog.setVisible(true);
        if (detailsDialog.isSaved()) {
            loadLoans();
            JOptionPane.showMessageDialog(this, "Succesfully closed loan", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}

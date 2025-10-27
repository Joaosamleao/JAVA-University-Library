package View.Panels.LoanPanels;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.LoanController;
import DTO.UserDTO;

public class MyLoansPanel extends JPanel {
    
    private final LoanController loanController;
    private final UserDTO user;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;

    public MyLoansPanel(LoanController loanController, UserDTO user) {
        this.loanController = loanController;
        this.user = user;
        initComponents();
        loadLoans();
    }

    public final void loadLoans() {
        List<Object[]> loanData = loanController.loadLoansByUser(user.getIdUser());
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

        loadButton = new JButton("Reload Loans");
        loadButton.addActionListener(e -> loadLoans());
        add(loadButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

}

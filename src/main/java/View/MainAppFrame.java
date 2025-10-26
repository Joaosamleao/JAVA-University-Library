package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.BookController;
import Controller.BookCopyController;
import Controller.LoanController;
import Controller.UserController;
import Model.Enum.UserType;
import Model.User;
import View.Panels.BookPanels.BookCreatePanel;
import View.Panels.BookPanels.BookDetailsPanel;
import View.Panels.BookPanels.BookListPanel;
import View.Panels.LoanPanels.ActiveLoansPanel;
import View.Panels.LoanPanels.LoanCreatePanel;
import View.Panels.LoanPanels.LoanDetailsPanel;
import View.Panels.LoanPanels.MyLoansPanel;

public class MainAppFrame extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private final BookController bookController;
    private final BookCopyController copyController;
    private final LoanController loanController;
    //private final UserController userController;

    private final User loggedUser;

    public MainAppFrame(User user, BookController bookController, BookCopyController copyController, LoanController loanController, UserController userController) {
        this.loggedUser = user;
        this.bookController = bookController;
        this.copyController = copyController;
        this.loanController = loanController;
        //this.userController = userController;
        initComponents();
    }

    private void initComponents() {
        setTitle("University X Library - User: " + loggedUser.getName());
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        BookListPanel bookListPanel = new BookListPanel(bookController);
        mainPanel.add(bookListPanel, "BOOK_LIST");

        BookCreatePanel bookCreatePanel = new BookCreatePanel(bookController);
        mainPanel.add(bookCreatePanel, "BOOK_CREATE");

        BookDetailsPanel bookDetailsPanel = new BookDetailsPanel(bookController, copyController, loggedUser);
        mainPanel.add(bookDetailsPanel, "BOOK_DETAILS");

        //BookEditDialog bookUpdatePanel = new BookEditDialog(bookController);
        //mainPanel.add(bookUpdatePanel, "BOOK_EDIT");

        //BookCopyCreateDialog bookCopyCreatePanel = new BookCopyCreateDialog(copyController);
        //mainPanel.add(bookCopyCreatePanel, "BOOK_COPY_CREATE");

        //BookCopyEditPanel bookCopyEditPanel = new BookCopyEditPanel(copyController);
        //mainPanel.add(bookCopyEditPanel, "BOOK_COPY_EDIT");

        ActiveLoansPanel activeLoansPanel = new ActiveLoansPanel(loanController);
        mainPanel.add(activeLoansPanel, "ACTIVE_LOANS");

        LoanDetailsPanel loanDetailsPanel = new LoanDetailsPanel(loanController);
        mainPanel.add(loanDetailsPanel, "LOAN_DETAILS");

        MyLoansPanel myLoansPanel = new MyLoansPanel(loanController);
        mainPanel.add(myLoansPanel, "MY_LOANS");

        LoanCreatePanel loanCreatePanel = new LoanCreatePanel(loanController);
        mainPanel.add(loanCreatePanel, "REGISTER_LOAN");

        // Lógica para os Panels do grupo FinePanels

        add(mainPanel, BorderLayout.CENTER);
        setJMenuBar(createMenuBar());

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        if (loggedUser.getUserType() == UserType.STUDENT || loggedUser.getUserType() == UserType.LIBRARIAN) {
            JMenu collection = new JMenu("Collection");
        
            JMenuItem viewCollection = new JMenuItem("View Collection");
            viewCollection.addActionListener(e -> cardLayout.show(mainPanel, "BOOK_LIST"));
            collection.add(viewCollection);

            if (loggedUser.getUserType() == UserType.LIBRARIAN) {
                JMenuItem createBook = new JMenuItem("Register Book");
                createBook.addActionListener(e -> cardLayout.show(mainPanel, "BOOK_CREATE"));
                collection.add(createBook);
            }

            menuBar.add(collection);
        }

        JMenu loanMenu = new JMenu("Loan");
        boolean addedLoanMenu = false;

        if (loggedUser.getUserType() == UserType.CLERK) {
            JMenuItem activeLoans = new JMenuItem("Active Loans");
            activeLoans.addActionListener(e -> cardLayout.show(mainPanel, "ACTIVE_LOANS"));
            loanMenu.add(activeLoans);

            JMenuItem registerLoan = new JMenuItem("Register Loan");
            registerLoan.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER_LOAN"));
            addedLoanMenu = true;
        }

        if (loggedUser.getUserType() == UserType.STUDENT) {
            JMenuItem myLoans = new JMenuItem("My Loans");
            myLoans.addActionListener(e -> cardLayout.show(mainPanel, "MY_LOANS"));
            addedLoanMenu = true;
        }

        if (addedLoanMenu) {
            menuBar.add(loanMenu);
        }

        // Lógica para os Panels do grupo FinePanels

        return menuBar;
    }

    public void switchToView(String panelName, Integer id) {
        Component panel = findPanelByName(panelName);

        if (panel instanceof BookDetailsPanel && id != null) {
            ((BookDetailsPanel) panel).loadBookDetails(id);
        }
        // Lógica para outras telas que precisem ser carregadas
        cardLayout.show(mainPanel, panelName);
    }

    private Component findPanelByName(String name) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(name)) {
                return comp;
            }
        }
        return null;
    }


    // Métodos para exibição de mensagens
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Operation Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);   
    }
}

package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

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
import DTO.UserDTO;
import Model.Enum.UserType;
import View.Panels.BookPanels.BookCreatePanel;
import View.Panels.BookPanels.BookDetailsPanel;
import View.Panels.BookPanels.BookListPanel;
import View.Panels.FinePanels.ActiveFinesPanel;
import View.Panels.FinePanels.MyFinesPanel;
import View.Panels.LoanPanels.ActiveLoansPanel;
import View.Panels.LoanPanels.LoanCreatePanel;
import View.Panels.LoanPanels.MyLoansPanel;

public class MainAppFrame extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private Map<String, JPanel> panelMap;

    private final BookController bookController;
    private final BookCopyController copyController;
    private final LoanController loanController;
    private final UserController userController;

    private final UserDTO loggedUser;

    public MainAppFrame(UserDTO user, BookController bookController, BookCopyController copyController, LoanController loanController, UserController userController) {
        this.loggedUser = user;
        this.bookController = bookController;
        this.copyController = copyController;
        this.loanController = loanController;
        this.userController = userController;
        initComponents();
    }

    private void initComponents() {
        setTitle("University X Library - User: " + loggedUser.getName());
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelMap = new HashMap<>();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        BookListPanel bookListPanel = new BookListPanel(bookController);
        mainPanel.add(bookListPanel, "BOOK_LIST");
        panelMap.put("BOOK_LIST", bookListPanel);

        BookCreatePanel bookCreatePanel = new BookCreatePanel(bookController);
        mainPanel.add(bookCreatePanel, "BOOK_CREATE");
        panelMap.put("BOOK_CREATE", bookCreatePanel);

        BookDetailsPanel bookDetailsPanel = new BookDetailsPanel(bookController, copyController, loggedUser);
        mainPanel.add(bookDetailsPanel, "BOOK_DETAILS");
        panelMap.put("BOOK_DETAILS", bookDetailsPanel);

        //BookEditDialog bookUpdatePanel = new BookEditDialog(bookController);
        //mainPanel.add(bookUpdatePanel, "BOOK_EDIT");

        //BookCopyCreateDialog bookCopyCreatePanel = new BookCopyCreateDialog(copyController);
        //mainPanel.add(bookCopyCreatePanel, "BOOK_COPY_CREATE");

        //BookCopyEditPanel bookCopyEditPanel = new BookCopyEditPanel(copyController);
        //mainPanel.add(bookCopyEditPanel, "BOOK_COPY_EDIT");

        ActiveLoansPanel activeLoansPanel = new ActiveLoansPanel(loanController);
        mainPanel.add(activeLoansPanel, "ACTIVE_LOANS");
        panelMap.put("ACTIVE_LOANS", activeLoansPanel);

        //LoanDetailsDialog loanDetailsPanel = new LoanDetailsDialog(loanController);
        //mainPanel.add(loanDetailsPanel, "LOAN_DETAILS");

        MyLoansPanel myLoansPanel = new MyLoansPanel(loanController, loggedUser);
        mainPanel.add(myLoansPanel, "MY_LOANS");
        panelMap.put("MY_LOANS", myLoansPanel);

        LoanCreatePanel loanCreatePanel = new LoanCreatePanel(loanController, userController, copyController);
        mainPanel.add(loanCreatePanel, "REGISTER_LOAN");
        panelMap.put("REGISTER_LOAN", loanCreatePanel);

        MyFinesPanel myFinesPanel = new MyFinesPanel();
        mainPanel.add(myFinesPanel, "MY_FINES");
        panelMap.put("MY_FINES", myFinesPanel);

        ActiveFinesPanel activeFinesPanel = new ActiveFinesPanel();
        mainPanel.add(activeFinesPanel, "ACTIVE_FINES");
        panelMap.put("ACTIVE_FINES", activeFinesPanel);

        // Lógica para os Panels do grupo FinePanels

        add(mainPanel, BorderLayout.CENTER);
        setJMenuBar(createMenuBar());

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        if (loggedUser.getUserType() == UserType.STUDENT || loggedUser.getUserType() == UserType.LIBRARIAN || loggedUser.getUserType() == UserType.CLERK) {
            JMenu collection = new JMenu("Collection");
        
            JMenuItem viewCollection = new JMenuItem("View Collection");
            viewCollection.addActionListener(e -> switchToView("BOOK_LIST", null));
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
            activeLoans.addActionListener(e -> switchToView("ACTIVE_LOANS", null));
            loanMenu.add(activeLoans);

            JMenuItem registerLoan = new JMenuItem("Register Loan");
            registerLoan.addActionListener(e -> switchToView("REGISTER_LOAN", null));
            loanMenu.add(registerLoan);
            addedLoanMenu = true;
        }

        if (loggedUser.getUserType() == UserType.STUDENT) {
            JMenuItem myLoans = new JMenuItem("My Loans");
            myLoans.addActionListener(e -> switchToView("MY_LOANS", null));
            loanMenu.add(myLoans);
            addedLoanMenu = true;
        }

        if (addedLoanMenu) {
            menuBar.add(loanMenu);
        }

        // Lógica para os Panels do grupo FinePanels

        JMenu fineMenu = new JMenu("Fines");
        boolean addedFinesMenu = false;

        if (loggedUser.getUserType() == UserType.CLERK) {
            JMenuItem activeFines = new JMenuItem("Active Fines");
            activeFines.addActionListener(e -> switchToView("ACTIVE_FINES", null));
            addedFinesMenu = true;
        }

        if (loggedUser.getUserType() == UserType.STUDENT) {
            JMenuItem myFines = new JMenuItem("My Fines");
            myFines.addActionListener(e -> switchToView("MY_FINES", null));
            addedFinesMenu = true;
        }

        if (addedFinesMenu) {
            menuBar.add(fineMenu);
        }

        return menuBar;
    }

    public void switchToView(String panelName, Integer id) {
        Component panel = findPanelByName(panelName);

        switch (panelName) {
            case "BOOK_LIST" -> {
                if (panel instanceof BookListPanel bookListPanel) {
                    bookListPanel.loadBooks();
                }
            }

            case "BOOK_DETAILS" -> {
                if (panel instanceof BookDetailsPanel && id != null) {
                    ((BookDetailsPanel) panel).loadBookDetails(id);
                }
            }

            case "ACTIVE_LOANS" -> {
                if (panel instanceof ActiveLoansPanel activeLoansPanel) {
                    activeLoansPanel.loadLoans();
                }
            }

            case "MY_LOANS" -> {
                if (panel instanceof MyLoansPanel myLoansPanel) {
                    myLoansPanel.loadLoans();
                }
            }
        }

        if (panel instanceof BookDetailsPanel && id != null) {
            System.out.println("Main App Frame Recieved the Request");
            ((BookDetailsPanel) panel).loadBookDetails(id);
        } 
        // Lógica para outras telas que precisem ser carregadas
        cardLayout.show(mainPanel, panelName);
    }

    private Component findPanelByName(String name) {
        return panelMap.get(name);
    }


    // Métodos para exibição de mensagens
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Operation Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);   
    }
}

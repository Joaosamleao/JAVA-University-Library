package App;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Controller.BookController;
import Controller.BookCopyController;
import Controller.FineController;
import Controller.LoanController;
import Controller.UserController;
import DTO.UserDTO;
import Repository.Impl.BookCopyImpl;
import Repository.Impl.BookImpl;
import Repository.Impl.FineImpl;
import Repository.Impl.LoanImpl;
import Repository.Impl.UserImpl;
import Repository.Interface.BookCopyRepository;
import Repository.Interface.BookRepository;
import Repository.Interface.FineRepository;
import Repository.Interface.LoanRepository;
import Repository.Interface.UserRepository;
import Service.BookCopyService;
import Service.BookService;
import Service.FineService;
import Service.LoanService;
import Service.UserService;
import Util.DatabaseConnection;
import View.LoginDialog;
import View.MainAppFrame;

public class Main {

    public static void main(String[] args) {
        Connection connection; 
        try {
            // --- 1. Estabelecer Conexão com o Banco ---
            System.out.println("Tentando conectar ao banco de dados...");
            connection = DatabaseConnection.getConnection();
            System.out.println("Conexão estabelecida com sucesso.");

            // --- 2. Instanciar DAOs (Repositórios) ---
            UserRepository userDAO = new UserImpl(connection);
            BookRepository bookDAO = new BookImpl(connection);
            BookCopyRepository bookCopyDAO = new BookCopyImpl(connection);
            LoanRepository loanDAO = new LoanImpl(connection);
            FineRepository fineDAO = new FineImpl(connection);

            // --- 3. Instanciar Services ---
            UserService userService = new UserService(userDAO);
            BookService bookService = new BookService(bookDAO);
            BookCopyService bookCopyService = new BookCopyService(bookCopyDAO);
            LoanService loanService = new LoanService(loanDAO); 
            FineService fineService = new FineService(fineDAO);

            // --- 4. Instanciar Controllers ---
            UserController userController = new UserController(userService);
            BookController bookController = new BookController(bookService);
            BookCopyController bookCopyController = new BookCopyController(bookCopyService);
            // Ensure LoanController constructor takes required services
            LoanController loanController = new LoanController(loanService, userService, bookCopyService, fineService); 
            FineController fineController = new FineController(fineService, userService);

            LoginDialog loginDialog = new LoginDialog(null, true, userController);
            System.out.println("Login Dialog foi chamado");
            loginDialog.setVisible(true);
            UserDTO loggedInUser = loginDialog.getAuthenticatedUser();
            if (loggedInUser == null) {
                System.out.println("Usuário chegou na main nulo");
            }

            SwingUtilities.invokeLater(() -> {
                System.out.println("Iniciando a janela principal...");
                MainAppFrame mainFrame = new MainAppFrame(loggedInUser, bookController, bookCopyController, loanController, userController, fineController);
                
                ((BookController) bookController).setMainFrame(mainFrame);
                ((BookCopyController) bookCopyController).setMainFrame(mainFrame);
                ((LoanController) loanController).setMainFrame(mainFrame);
                ((UserController) userController).setMainFrame(mainFrame);
                ((FineController) fineController).setMainFrame(mainFrame);

                mainFrame.setVisible(true);
                System.out.println("Janela principal exibida.");
            });

        } catch (SQLException | IOException e) {
            System.err.println("CRITICAL ERROR: Couldn't connect to the database. Application will exit.");
            JOptionPane.showMessageDialog(null, 
                "Falha crítica ao conectar com o banco de dados.\nVerifique a configuração e se o servidor MySQL está ativo.\nA aplicação será encerrada.", 
                "Erro Fatal de Conexão", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
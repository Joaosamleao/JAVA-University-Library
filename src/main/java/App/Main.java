package App;

// Importações de todas as camadas
import Repository.Impl.*;
import Repository.Interface.*;
import Service.*;
import Controller.*;
import View.*; // Ou o pacote onde estão LoginDialog e MainApplicationFrame

// Importações Java e Swing

import javax.swing.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;

import DTO.UserDTO;
import Model.Enum.UserType;
import Repository.Impl.BookCopyImpl;
import Repository.Impl.BookImpl;
import Repository.Impl.LoanImpl;
import Repository.Impl.UserImpl;
import Repository.Interface.BookCopyRepository;
import Repository.Interface.BookRepository;
import Repository.Interface.LoanRepository;
import Repository.Interface.UserRepository;
import Util.DatabaseConnection;

/**
 * Ponto de entrada principal da aplicação da Biblioteca.
 * Responsável por inicializar e conectar todas as camadas (MVCS)
 * e exibir a interface gráfica. Realiza a Injeção de Dependência manual.
 */
public class Main {

    public static void main(String[] args) {
        Connection connection = null; 
        try {
            // --- 1. Estabelecer Conexão com o Banco ---
            System.out.println("Tentando conectar ao banco de dados...");
            connection = DatabaseConnection.getConnection();
            System.out.println("Conexão estabelecida com sucesso.");

            // --- THE REST OF YOUR CODE ONLY RUNS IF CONNECTION SUCCEEDS ---

            // --- 2. Instanciar DAOs (Repositórios) ---
            UserRepository userDAO = new UserImpl(connection);
            BookRepository bookDAO = new BookImpl(connection);
            BookCopyRepository bookCopyDAO = new BookCopyImpl(connection); // Pass connection
            // Make sure LoanImpl constructor accepts Connection and DAOs correctly
            LoanRepository loanDAO = new LoanImpl(connection); 
            // FineDAO fineDAO = new FineDAOImpl(connection, loanDAO);

            // --- 3. Instanciar Services ---
            UserService userService = new UserService(userDAO);
            BookService bookService = new BookService(bookDAO);
            BookCopyService bookCopyService = new BookCopyService(bookCopyDAO); // Pass necessary DAOs
            LoanService loanService = new LoanService(loanDAO); 
            // FineService fineService = new FineServiceImpl(fineDAO, loanService);

            // --- 4. Instanciar Controllers ---
            UserController userController = new UserController(userService);
            BookController bookController = new BookController(bookService);
            BookCopyController bookCopyController = new BookCopyController(bookCopyService);
            // Ensure LoanController constructor takes required services
            LoanController loanController = new LoanController(loanService, userService, bookCopyService); 
            // FineController fineController = new FineControllerImpl(fineService);

            // --- 5. Processo de Login ---
            // (Your existing login logic - using UserDTO for now)
            UserDTO user = new UserDTO("Teste", "2410373", "joaosamleao@gmail.com", "12345", UserType.STUDENT);
            UserDTO user2 = new UserDTO("Teste", "2410373", "joaosamleao@gmail.com", "12345", UserType.CLERK);
            UserDTO user3 = new UserDTO("Teste", "2410373", "joaosamleao@gmail.com", "12345", UserType.LIBRARIAN);

            // --- 6. Iniciar a Janela Principal (Main Frame) ---
            SwingUtilities.invokeLater(() -> {
                System.out.println("Iniciando a janela principal...");
                MainAppFrame mainFrame = new MainAppFrame(user2, bookController, bookCopyController, loanController, userController);
                
                // Connect controllers back to MainFrame
                ((BookController) bookController).setMainFrame(mainFrame);
                ((BookCopyController) bookCopyController).setMainFrame(mainFrame);
                ((LoanController) loanController).setMainFrame(mainFrame);
                 ((UserController) userController).setMainFrame(mainFrame); // Add if needed

                mainFrame.setVisible(true);
                System.out.println("Janela principal exibida.");
            });

        // --- CORRECTED CATCH BLOCK ---
        } catch (SQLException | IOException e) {
            // Log the error properly
            System.err.println("CRITICAL ERROR: Couldn't connect to the database. Application will exit.");
            e.printStackTrace(); // Print stack trace for debugging
            // Show a user-friendly error message
            JOptionPane.showMessageDialog(null, 
                "Falha crítica ao conectar com o banco de dados.\nVerifique a configuração e se o servidor MySQL está ativo.\nA aplicação será encerrada.", 
                "Erro Fatal de Conexão", 
                JOptionPane.ERROR_MESSAGE);
            // EXIT the application
            System.exit(1); // Use a non-zero exit code to indicate an error
        } catch (Exception e) {
             // Catch any other unexpected errors during setup
            System.err.println("Unexpected error during application startup: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Ocorreu um erro inesperado ao iniciar a aplicação.\nVerifique o console para detalhes.", 
                "Erro Inesperado", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
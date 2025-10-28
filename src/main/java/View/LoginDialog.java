package View;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.UserController;
import DTO.UserDTO;

public class LoginDialog extends JDialog {
    
    private JTextField registrationField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    private final UserController controller;

    private UserDTO authenticatedUser;

    public LoginDialog(Frame frame, boolean modal, UserController controller) {
        super(frame, modal);
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        System.out.println("Init components do dialog foi chamado");
        setTitle("Login - University X Library");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(getParent());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Registration:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        registrationField = new JTextField(20); add(registrationField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        passwordField = new JPasswordField(20); add(passwordField, gbc);

        loginButton = new JButton("Login");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton);

        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        add(buttonPanel, gbc);

        loginButton.addActionListener(e -> attemptLogin());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                authenticatedUser = null;
            }
        });

        pack();
    }

    private void attemptLogin() {
        System.out.println("Tentativa de login detectada");
        String registration = registrationField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        System.out.println("Senha plana: " + password);
        this.authenticatedUser = controller.attemptLoginRequest(registration, password);

        if (this.authenticatedUser != null) {
            this.dispose();
        }
    }

    public UserDTO getAuthenticatedUser() {
        System.out.println("Retorno do usuário autenticado");
        return authenticatedUser;
    }
}

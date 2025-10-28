package Controller;

import DTO.UserDTO;
import Exceptions.AuthenticationException;
import Exceptions.DataAccessException;
import Exceptions.ResourceNotFoundException;
import Model.User;
import Service.UserService;
import View.MainAppFrame;

public class UserController {
    
    private final UserService service;
    private MainAppFrame mainFrame;
    
    public UserController(UserService service) {
        this.service = service;
    }

    public UserDTO findUserByRegistration(String registration) {
        try {
            User user = service.findUserByRegistration(registration);
            UserDTO userDTO = new UserDTO();
            userDTO.setIdUser(user.getIdUser());
            return userDTO;
        } catch (ResourceNotFoundException e) {
            mainFrame.showWarningMessage("WARNNIG: " + e.getMessage());
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database");
        }
        return null;
    }

        public void setMainFrame(MainAppFrame frame) {
        this.mainFrame = frame;
    }

    public UserDTO attemptLoginRequest(String registration, String password) {
        System.out.println("Chamada de autenticação chegou no Controller");
        try {
            User user = service.authenticateLogin(registration, password);
            UserDTO userData = new UserDTO(user.getName(), user.getRegistration(), user.getEmail(), user.getUserType());
            userData.setIdUser(user.getIdUser());
            System.out.println("Usuário chegou da service com o ID: " + userData.getIdUser());
            return userData;
        } catch (AuthenticationException e) {
            System.out.print("Falha na autenticação: " + e.getMessage());
        }
        return null;
    }

}

package Controller;

import DTO.UserDTO;
import Exceptions.DataAccessException;
import Exceptions.ResourceNotFoundException;
import Model.User;
import Service.UserService;
import View.MainAppFrame;

public class UserController {
    
    private final UserService service;
    private final MainAppFrame mainFrame;
    
    public UserController(UserService service, MainAppFrame mainFrame) {
        this.service = service;
        this.mainFrame = mainFrame;
    }

    public UserDTO findUserByRegistration(String registration) {
        try {
            User user = service.findUserByRegistration(registration);
            UserDTO userDTO = new UserDTO();
            userDTO.setIdUser(user.getIdUser());
            return userDTO;
        } catch (ResourceNotFoundException e) {
            mainFrame.showWarningMessage("WARNNIG: No users found with registration: " + registration);
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database: " + e.getMessage());
        }
        return null;
    } 

}

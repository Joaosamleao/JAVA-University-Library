package Controller;

import DTO.UserDTO;
import Exceptions.DataAccessException;
import Exceptions.ResourceNotFoundException;
import Model.User;
import Service.UserService;

public class UserController {
    
    private final UserService service;
    
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

        } catch (DataAccessException e) {

        }
        return null;
    } 

}

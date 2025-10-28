package DTO;

import Model.Enum.UserType;

public class UserDTO {

    private Integer idUser;

    private String name;
    private String registration;
    private String email;
    private String password;
    
    private UserType userType;

    public UserDTO() {

    }

    public UserDTO(String name, String registration, String email, UserType userType) {
        this.name = name;
        this.registration = registration;
        this.email = email;
        this.userType = userType;
    }

    // Getters
    public Integer getIdUser() { return idUser; }
    public String getName() { return name; }
    public String getRegistration() { return registration; }
    public String getEmail() { return email; }
    public UserType getUserType() { return userType; }
    public String getPassword() { return password; }

    // Setters
    public void setIdUser(Integer idUser) { this.idUser = idUser; } 
    public void setName(String name) { this.name = name; }
    public void setRegistration(String registration) { this.registration = registration; }
    public void setEmail(String email) { this.email = email; }

}

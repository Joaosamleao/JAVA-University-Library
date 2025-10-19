package Model;

import Model.Enum.UserType;

public class User {
    
    private Integer idUser;

    private String name;
    private String registration;
    private String email;
    
    private UserType userType;

    public User(String name, String registration, String email, UserType userType) {
        this.name = name;
        this.registration = registration;
        this.email = email;
        this.userType = userType;
    }

    // Getters
    public Integer getIdUser() { return idUser; }
    public String getName() { return name; }
    public String registration() { return registration; }
    public String email() { return email; }
    public UserType returnUserType() { return userType; }

    // Setters
    public void setIdUser(Integer idUser) { this.idUser = idUser; } 
    public void setName(String name) { this.name = name; }
    public void setRegistration(String registration) { this.registration = registration; }
    public void setEmail(String email) { this.email = email; }
    public void setUserType(UserType usertype) { this.userType = usertype; }

}

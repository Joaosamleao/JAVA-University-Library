package Repository.Interface;

import java.util.Optional;

import Model.User;

public interface UserRepository extends GenericRepository<User, Integer> {
    
    Optional<User> findUserByRegistration(String registration);

}

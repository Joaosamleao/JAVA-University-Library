package Service;

// Regras de negócio sobre a entidade User (Usuário)

import java.util.Optional;

import Exceptions.DataAccessException;
import Exceptions.ResourceNotFoundException;
import Model.User;
import Repository.Interface.UserRepository;


// Operações remetente ao usuário do tipo Administrator (Administrador):
// Cadastrar um usuário no sistema
// Remover um usuário do sistema
// Editar informações de um usuário do sistema

// Regras especiais p/ Administrator:
// Na inicialização do sistema, ele deve ter um usuário Administrator padrão que deverá ser removido quando um novo Administrator for cadastrado no sistema.

// Operações remetente a todos os tipos de usuário (Student, Clerk, Librarian, Administrator):
// Logar no sistema

public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Integer id) throws DataAccessException, ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new ResourceNotFoundException("ERROR: User not found with ID: " + id));
    }

    public User findUserByRegistration(String registration) throws DataAccessException, ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByRegistration(registration);
        return optionalUser.orElseThrow(() -> new ResourceNotFoundException("ERROR: User not found with registration: " + registration));
    }

}

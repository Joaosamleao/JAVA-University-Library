package Service;

// Regras de negócio sobre a entidade User (Usuário)

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import Exceptions.AuthenticationException;
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
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User findUserById(Integer id) throws DataAccessException, ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new ResourceNotFoundException("ERROR: User not found with ID: " + id));
    }

    public User findUserByRegistration(String registration) throws DataAccessException, ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByRegistration(registration);
        return optionalUser.orElseThrow(() -> new ResourceNotFoundException("ERROR: User not found with registration: " + registration));
    }

    public User authenticateLogin(String registration, String password) throws AuthenticationException, DataAccessException {
        System.out.println("Chamada de autenticação chegou na Service");
        Optional<User> optionalUser = userRepository.findUserByRegistration(registration);
        User user = optionalUser.orElseThrow(() -> new AuthenticationException("Invalid Credentials"));
        String storedHash = user.getPassword();
        System.out.println("Service achou o usuário com ID: " + user.getIdUser());

        if (storedHash == null || storedHash.isEmpty()) {
            throw new AuthenticationException("Authentication failed [HASH_MISSING]");
        }
        System.out.println("Senha na Service 1: " + password + "Tamanho: " + password.length() + " Hash: " + storedHash + "Tamanho: " + storedHash.length());
        boolean passwordsMatch = passwordEncoder.matches(password, storedHash);
        System.out.println(passwordsMatch);

        if (passwordsMatch) {
            return user;
        } else {
            throw new AuthenticationException("Invalid credentials");
        }
        
    }

}

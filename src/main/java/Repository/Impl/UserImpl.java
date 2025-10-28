package Repository.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Model.Enum.UserType;
import Model.User;
import Repository.Interface.UserRepository;

public class UserImpl implements UserRepository {
    
    private final Connection connection;

    public UserImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User create(User user) throws DataCreationException {
        String sql = "INSERT INTO users (name, registration, email, password,user_type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getRegistration());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getUserType().name());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("ERROR: User could not be created, no affected rows");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setIdUser(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("ERROR: User could not be created, no ID obtained");
                }
            }

        } catch (SQLException e) {
            throw new DataCreationException("ERROR: Couldn't create user", e);
        }
        return user;
    }

    @Override
    public Optional<User> findById(Integer id) throws DataAccessException {
        String sql = "SELECT * FROM users WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find user by ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByRegistration(String registration) throws DataAccessException {
        String sql = "SELECT * FROM users WHERE registration = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, registration);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find user by registration: " + registration, e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws DataAccessException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find any users", e);
        }
        return users;
    }

    @Override
    public void update(User user) throws DataAccessException {
        String sql = "UPDATE users SET name = ?, registration = ?, email = ?, password = ? WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getRegistration());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getIdUser());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("ERROR: Couldn't update book, no affected rows");
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't update user with ID: " + user.getIdUser(), e);
        }
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String sql = "DELETE FROM users WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't delete user with ID: " + id, e);
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setIdUser(rs.getInt("id_user"));
        user.setName(rs.getString("name"));
        user.setRegistration("registration");
        user.setEmail(rs.getString("email"));
        user.setUserType(UserType.valueOf(rs.getString("user_type").toUpperCase()));
        user.setPassword(rs.getString("password"));
        return user;
    }

}

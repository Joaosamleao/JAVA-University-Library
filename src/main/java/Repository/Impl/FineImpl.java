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
import Model.Fine;
import Repository.Interface.FineRepository;

public class FineImpl implements FineRepository {
    
    private final Connection connection;

    public FineImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Fine create(Fine fine) throws DataCreationException {
        String sql = "INSERT INTO fines (id_loan, id_user, amount, issue_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, fine.getLoan());
            ps.setInt(2, fine.getIdUser());
            ps.setBigDecimal(3, fine.getAmount());
            ps.setDate(4, java.sql.Date.valueOf(fine.getIssueDate()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("ERROR: Fine could not be created, no affected rows");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fine.setIdFine(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("ERROR: Fine could not be created, no ID obtanied");
                }
            }
        } catch (SQLException e) {
            throw new DataCreationException("ERROR: Couldn't create fine", e);
        }
        return fine;
    }

    @Override
    public Optional<Fine> findById(Integer id) throws DataAccessException {
        String sql = "SELECT * FROM fines WHERE id_fine = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToFine(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find fine by ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Fine> findFineByUserId(Integer id) {
        List<Fine> fines = new ArrayList<>();
        String sql = "SELECT * FROM fines WHERE id_user = ? ORDER BY id_fine";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1 , id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    fines.add(mapResultSetToFine(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find fines with user ID: " + id, e);
        }
        return fines;
    }

    @Override
    public Optional<Fine> findActiveFineByLoanId(Integer id) throws DataAccessException {
        String sql = "SELECT * FROM fines WHERE id_loan = ? AND payment_date IS NULL ORDER BY id_loan";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToFine(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find active fine with loan ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Fine> findAll() throws DataAccessException {
        List<Fine> fines = new ArrayList<>();
        String sql = "SELECT * FROM fines WHERE payment_date IS NULL ORDER BY id_fine";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                fines.add(mapResultSetToFine(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find any fines", e);
        }
        return fines;
    }

    @Override
    public void update(Fine fine) throws DataAccessException {
        String sql = "UPDATE fines SET payment_date = ? WHERE id_fine = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(fine.getPaymentDate()));
            ps.setInt(2, fine.getIdFine());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("ERROR: Couldn't update fine, no affected rows");
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't update fine with ID: " + fine.getIdFine(), e);
        }
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String sql = "DELETE * FROM fines WHERE id_fine = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't delete fine with ID: " + id + ", no affected rows", e);
        }
    }

    private Fine mapResultSetToFine(ResultSet rs) throws SQLException {
        Fine fine = new Fine();
        fine.setIdFine(rs.getInt("id_fine"));
        fine.setIdLoan(rs.getInt("id_loan"));
        fine.setIdUser(rs.getInt("id_user"));
        fine.setAmount(rs.getBigDecimal("amount"));
        fine.setIssueDate(rs.getDate("issue_date").toLocalDate());
        return fine;
    }

}
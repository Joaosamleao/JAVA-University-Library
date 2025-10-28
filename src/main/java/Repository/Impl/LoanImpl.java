package Repository.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Model.Loan;
import Repository.Interface.LoanRepository;

public class LoanImpl implements LoanRepository {
    
    private final Connection connection;

    public LoanImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Loan create(Loan loan) throws DataCreationException {
        String sql = "INSERT INTO loans (id_user, id_copy, loan_date, expected_return_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, loan.getUserId());
            ps.setInt(2, loan.getCopyId());
            ps.setDate(3, java.sql.Date.valueOf(loan.getLoanDate()));
            ps.setDate(4, java.sql.Date.valueOf(loan.getExpectedReturnDate()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("ERROR: Loan could not be created, no affected rows.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    loan.setidLoan(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("ERROR: Loan could not be created, no ID obtained");
                }
            }
            
        } catch (SQLException e) {
            throw new DataCreationException("ERROR: Couldn't create loan", e);
        }
        return loan;
    }

    @Override
    public Optional<Loan> findById(Integer id) throws DataAccessException {
        String sql = "SELECT * FROM loans WHERE id_loan = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLoan(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find book by ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Loan> findLoanByUserId(Integer id) throws DataAccessException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE id_user = ? AND actual_return_date IS NULL ORDER BY id_loan";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapResultSetToLoan(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find loans with user ID: " + id, e);
        }
        return loans;
    }

    @Override
    public Optional<Loan> findLoanByCopyId(Integer id) throws DataAccessException {
        String sql = "SELECT * FROM loans where id_copy = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1 , id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLoan(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find loans with copy ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Loan> findActiveLoanByCopyId(Integer id) throws DataAccessException {
        String sql = "SELECT * FROM loans WHERE id_copy = ? AND actual_return_date IS NULL";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLoan(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find active loan with copy ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Loan> findAll() throws DataAccessException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE actual_return_date IS NULL ORDER BY id_loan";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                loans.add(mapResultSetToLoan(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't find any loans", e);
        }
        return loans;
    }

    @Override
    public void update(Loan loan) throws DataAccessException {
        String sql = "UPDATE loans SET actual_return_date = ? WHERE id_loan = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(loan.getActualReturnDate()));
            ps.setInt(2, loan.getIdLoan());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("ERROR: Couldn't update book, no affected rows");
            }
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't update loan with ID: " + loan.getIdLoan(), e);
        }
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        String sql = "DELETE FROM loans WHERE id_loan = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("ERROR: Couldn't delete loan with ID: " + id + ", no affected rows", e);
        }
    }
    
    private Loan mapResultSetToLoan(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setidLoan(rs.getInt("id_loan"));
        loan.setUserId(rs.getInt("id_user"));
        loan.setCopyId(rs.getInt("id_copy"));
        loan.setLoanDate(rs.getDate("loan_date").toLocalDate());
        loan.setExpectedReturnDate(rs.getDate("expected_return_date").toLocalDate());

        Date sqlActualDate = rs.getDate("actual_return_date");

        if (sqlActualDate != null) {
            loan.setActualReturnDate(rs.getDate("actual_return_date").toLocalDate());
        }
        return loan;
    }

}

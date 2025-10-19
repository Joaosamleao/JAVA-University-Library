package Repository.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Model.Book;
import Model.BookCopy;
import Model.Enum.ItemStatus;
import Repository.Interface.BookCopyRepository;
import Repository.Interface.BookRepository;

public class BookCopyImpl implements BookCopyRepository {
    
    private final Connection connection;
    private final BookRepository bookRepository;

    public BookCopyImpl(Connection connection, BookRepository bookRepository) {
        this.connection = connection;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookCopy create(BookCopy bookCopy) {
        String sql = "INSERT INTO copies (id_book, barcode, status, location_code) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, bookCopy.getBook().getIdBook());
            ps.setString(2, bookCopy.getBarcode());
            ps.setString(3, bookCopy.getStatus().name());
            ps.setString(4, bookCopy.getLocationCode());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    bookCopy.setIdCopy(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
        return bookCopy;
    }

    @Override
    public Optional<BookCopy> findById(Integer id) {
        String sql = "SELECT * FROM copies WHERE id_copy = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetBookCopy(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookCopy> findByBarCode(String barcode) {
        String sql = "SELECT * FROM copies WHERE barcode = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, barcode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetBookCopy(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
        return Optional.empty();
    }

    @Override
    public List<BookCopy> findAll() {
        List<BookCopy> copies = new ArrayList<>();
        String sql = "SELECT * FROM copies";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                copies.add(mapResultSetBookCopy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
        return copies;
    }

    @Override
    public List<BookCopy> findByStatus(ItemStatus status) {
        return null;
    }

    @Override
    public void update(BookCopy bookCopy) {
        String sql = "UPDATE copies SET id_book = ?, barcode = ?, status = ?, location_code = ?, WHERE id_copy = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookCopy.getBook().getIdBook());
            ps.setString(2, bookCopy.getBarcode());
            ps.setString(3, bookCopy.getStatus().name());
            ps.setString(4, bookCopy.getLocationCode());
            ps.setInt(5, bookCopy.getIdCopy());
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM copies WHERE id_copy = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
    }

    private BookCopy mapResultSetBookCopy(ResultSet rs) throws SQLException {
        BookCopy copy = new BookCopy();
        copy.setIdCopy(rs.getInt("id_copy"));
        copy.setBarcode(rs.getString("barcode"));
        copy.setStatus(ItemStatus.valueOf(rs.getString("status")));
        copy.setLocationCode(rs.getString("location_code"));

        int bookId = rs.getInt("id_book");
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new SQLException("ERROR: Failed to find Book with ID: " + bookId));
        copy.setBook(book);
        return copy;
    }

}

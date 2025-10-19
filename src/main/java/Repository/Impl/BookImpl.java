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
import Repository.Interface.BookRepository;
public class BookImpl implements BookRepository {
    
    private final Connection connection;

    public BookImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, author, publication_year, category, isbn) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublishedYear());
            ps.setString(4, book.getCategory());
            ps.setString(5, book.getIsbn());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("ERROR: Book could not be created, no affected rows.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setIdBook(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("ERROR: Book could not be created, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Integer id) {
        String sql = "SELECT * FROM books WHERE id_book = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBook(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBook(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
        return books;
    }

    @Override
    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publication_year = ?, category = ?, isbn = ? WHERE id_book = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublishedYear());
            ps.setString(4, book.getCategory());
            ps.setString(5, book.getIsbn());
            ps.setInt(6, book.getIdBook());
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM books WHERE id_book = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Temporário
        }
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setIdBook(rs.getInt("id_book"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublishedYear(rs.getInt("publication_year"));
        book.setCategory(rs.getString("category"));
        book.setIsbn(rs.getString("isbn"));
        return book;
    }

}

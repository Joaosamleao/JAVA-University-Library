package Service;

import java.util.List;
import java.util.Optional;

import Model.Book;
import Repository.Exceptions.DataAccessException;
import Repository.Interface.BookRepository;
import Service.Exceptions.ResourceNotFoundException;

// Regras de Negócio sobre a entidade Book (Livro)

// Operações Remetentes ao usuário de tipo Librarian
// Cadastrar Livro
// Remover Livro
// Editar Livro

// Operações remetente ao usuário do tipo Student (Estudante):
// Visualizar o acervo de livros

// TO-DO 20/10/2025
// Create Book ✅
// Read Book ✅
// Update Book
// Delete Book ✅

// TO-DO 21/10/2025
// Update Book

public class BookService {

    private final BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(String title, String author, int publishedYear, String category, String isbn) throws DataAccessException {
        Book book = new Book(title, author, publishedYear, category, isbn);
        bookRepository.create(book);
        return book;
    }

    public Book findBookById(Integer id) throws DataAccessException, ResourceNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new ResourceNotFoundException("ERROR: Book not found with ID: " + id));
    }

    public Book findBookByIsbn(String isbn) throws DataAccessException, ResourceNotFoundException {
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        return optionalBook.orElseThrow(() -> new ResourceNotFoundException("ERROR: Book not found with ISBN: " + isbn));
    }

    public List<Book> readAllBooks() throws DataAccessException {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks;
    }

    public void updateBook(Book book) {
        // TO-DO
    }

    public void deleteBook(Book book) throws DataAccessException {
        bookRepository.delete(book.getIdBook());
    }

}

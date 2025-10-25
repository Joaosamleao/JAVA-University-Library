package Service;

import java.util.List;
import java.util.Optional;

import DTO.BookDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.ResourceNotFoundException;
import Model.Book;
import Repository.Interface.BookRepository;

// Regras de Negócio sobre a entidade Book (Livro)

// Operações Remetentes ao usuário de tipo Librarian
// Cadastrar Livro
// Remover Livro
// Editar Livro

// Operações remetente ao usuário do tipo Student (Estudante):
// Visualizar o acervo de livros

// TO-DO 21/10/2025
// Create Book ✅
// Read Book ✅
// Update Book ✅
// Delete Book ✅

public class BookService {

    private final BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(String title, String author, int publishedYear, String category, String isbn) throws DataAccessException, BusinessRuleException {
        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(isbn);
        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getIsbn().equals(isbn)) {
            throw new BusinessRuleException("ERROR: ISBN code must be unique");
        }

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
        return bookRepository.findAll();
    }

    public void updateBook(Integer id, BookDTO bookData) throws DataAccessException, ResourceNotFoundException, BusinessRuleException {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ERROR: Book not found with ID: " + id + ", couldn't update data"));
        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(bookData.getIsbn());

        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getIdBook().equals(id)) {
            throw new BusinessRuleException("ERROR: The ISBN: " + bookData.getIsbn() + " is already in use by another book");
        }

        existingBook.setTitle(bookData.getTitle());
        existingBook.setAuthor(bookData.getAuthor());
        existingBook.setPublishedYear(bookData.getPublishedYear());
        existingBook.setCategory(bookData.getCategory());
        existingBook.setIsbn(bookData.getIsbn());

        bookRepository.update(existingBook);
    }

    public void deleteBook(Integer id) throws DataAccessException {
        bookRepository.delete(id);
    }

}

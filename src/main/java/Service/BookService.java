package Service;

import java.util.List;
import java.util.Optional;

import DTO.BookDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.FormatErrorException;
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

    public Book createBook(BookDTO bookData) throws DataCreationException, BusinessRuleException {
        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(bookData.getIsbn());
        if (bookWithSameIsbn.isPresent()) {
            throw new BusinessRuleException("ISBN code must be unique");
        }

        try {
            isValidIsbnFormat(bookData.getIsbn());
        } catch (FormatErrorException e) {
            throw new BusinessRuleException(e.getMessage());
        }

        Book book = new Book(bookData.getTitle(), bookData.getAuthor(), bookData.getPublishedYear(), bookData.getCategory(), bookData.getIsbn());
        bookRepository.create(book);

        return book;
    }

    public Book findBookById(Integer id) throws DataAccessException, ResourceNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    public Book findBookByIsbn(String isbn) throws DataAccessException, ResourceNotFoundException {
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        return optionalBook.orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    public List<Book> readAllBooks() throws DataAccessException {
        return bookRepository.findAll();
    }

    public void updateBook(Integer id, BookDTO bookData) throws DataAccessException, ResourceNotFoundException, BusinessRuleException {
        System.out.println("Edição de livro chegou na Service");
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id + ", couldn't update data"));
        Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(bookData.getIsbn());

        if (bookWithSameIsbn.isPresent() && !bookWithSameIsbn.get().getIdBook().equals(id)) {
            throw new BusinessRuleException("The ISBN: " + bookData.getIsbn() + " is already in use by another book");
        }

        existingBook.setTitle(bookData.getTitle());
        existingBook.setAuthor(bookData.getAuthor());
        existingBook.setPublishedYear(bookData.getPublishedYear());
        existingBook.setCategory(bookData.getCategory());
        existingBook.setIsbn(bookData.getIsbn());
        existingBook.setIdBook(id);

        System.out.println("Novo titulo na camada Service: " + existingBook.getTitle());

        bookRepository.update(existingBook);
        System.out.println("Service terminou a edição");
    }

    public void deleteBook(Integer id) throws DataAccessException, ResourceNotFoundException {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.delete(id);
        } else {
            throw new ResourceNotFoundException("Book not found with ID: " + id);
        }
    }

    public static int checkPublishedYear(String publishedYear) throws FormatErrorException, BusinessRuleException {
        int publishedYearInt;
        
        try {
            publishedYearInt = Integer.parseInt(publishedYear);
            if (publishedYearInt <= 0) {
                throw new BusinessRuleException("Invalid value for published year");
            }
            return publishedYearInt;
        } catch (NumberFormatException e) {
            throw new FormatErrorException("Couldn't convert: " + publishedYear + " to a valid year");
        }
    }

    private static void isValidIsbnFormat(String isbn) throws FormatErrorException {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new FormatErrorException("ISBN cannot be empty");
        }

        String cleanedIsbn = isbn.replace("-", "").replace(" ", "");

        switch (cleanedIsbn.length()) {
            case 10 -> isValidIsbn10(cleanedIsbn);
            case 13 -> isValidIsbn13(cleanedIsbn);
            default -> throw new FormatErrorException("ISBN must be 10 or 13 digits");
        }
    }

    private static void isValidIsbn10(String isbn10) throws FormatErrorException {
        if (!isbn10.substring(0, 9).matches("\\d{9}") || !isbn10.substring(9).matches("[\\dX]")) {
            throw new FormatErrorException("ISBN-10 format error: " + isbn10);
        } 

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.digit(isbn10.charAt(i), 10);
            sum += digit * (10 - i);
        }

        char expectedCheckDigit;
        int checkDigit = (11 - (sum % 11)) % 11;
        if (checkDigit == 10) {
            expectedCheckDigit = 'X';
        } else {
            expectedCheckDigit = Character.forDigit(checkDigit, 10);
        }

        char actualCheckDigit = Character.toUpperCase(isbn10.charAt(9));
        boolean isValid = actualCheckDigit == expectedCheckDigit;
        if (!isValid) {
            throw new FormatErrorException("ISBN-10 format error: " + isbn10 + " expected: " + expectedCheckDigit + " got: " + actualCheckDigit);

        }
    }

    private static void isValidIsbn13(String isbn13) throws BusinessRuleException {
        if (!isbn13.matches("\\d{13}") || (!isbn13.startsWith("978")) && !isbn13.startsWith("979")) {
            throw new FormatErrorException("ISBN-13 format error: " + isbn13);
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.digit(isbn13.charAt(i), 10);
            sum += (i % 2 == 0) ? digit * 1 : digit * 3;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        char expectedCheckDigit = Character.forDigit(checkDigit, 10);
        
        char actualCheckDigit = isbn13.charAt(12);

        boolean isValid = expectedCheckDigit == actualCheckDigit;
        if (!isValid) {
            throw new FormatErrorException("ISBN-13 format error: " + isbn13 + " expected: " + expectedCheckDigit + " got: " + actualCheckDigit);
        }
    }

}

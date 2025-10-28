package Controller;

import java.util.ArrayList;
import java.util.List;

import DTO.BookDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.FormatErrorException;
import Exceptions.ResourceNotFoundException;
import Model.Book;
import Service.BookService;
import View.MainAppFrame;

public class BookController {
    
    private final BookService service;
    private MainAppFrame mainFrame;

    public BookController(BookService service) {
        this.service = service;
    }

    public boolean createBookRequest(BookDTO bookData) {
        try {
            service.createBook(bookData);
            return true;
        } catch (DataCreationException | BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
            return false;
        }
    }

    public List<Object[]> loadBooksRequest() {
        try {
            List<Book> books = service.readAllBooks();
            List<Object[]> dataForView = new ArrayList<>();

            for (Book book : books) {
                Object[] rowData = new Object[] {
                    book.getIdBook(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublishedYear(),
                    book.getCategory(),
                    book.getIsbn()
                };
                dataForView.add(rowData);
            }
            return dataForView;

        } catch (DataAccessException e) {
            System.out.print("Teste"); // Substituir
        }
        return null;
    }

    public void requestBookDetailsView(Integer bookId) {
        System.out.println("Controller Recieved the Request");
        if (mainFrame != null) {
            mainFrame.switchToView("BOOK_DETAILS", bookId);
        }
    }

    public boolean requestBookEdit(Integer bookId, BookDTO bookData) {
        try {
            System.out.println("Edição de livro chegou no Controller");
            service.updateBook(bookId, bookData);
            return true;
        } catch (ResourceNotFoundException | BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
            return false;
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access database");
            return false;
        }

    }

    public BookDTO requestBookById(Integer bookId) {
        Book book = service.findBookById(bookId);
        BookDTO bookDTO = new BookDTO(book.getTitle(), book.getAuthor(), book.getPublishedYear(), book.getCategory(), book.getIsbn());
        return bookDTO;
    }

    public void requestDelete(Integer bookId) {
        try {
            service.deleteBook(bookId);
        } catch (ResourceNotFoundException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: " + e.getMessage());
        }
    }

    public void setMainFrame(MainAppFrame frame) {
        this.mainFrame = frame;
    }

    public Integer checkPublishedYear(String publishedYearString) {
        try {
            return BookService.checkPublishedYear(publishedYearString);
        } catch (FormatErrorException e) {
            mainFrame.showErrorMessage(e.getMessage());
        } catch (BusinessRuleException e) {
            mainFrame.showWarningMessage(e.getMessage());
        }
        return null;
    }

}

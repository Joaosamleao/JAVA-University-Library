package Controller;

import java.util.ArrayList;
import java.util.List;

import DTO.BookDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.ResourceNotFoundException;
import Model.Book;
import Service.BookService;
import View.MainAppFrame;

public class BookController {
    
    private final BookService service;
    private final MainAppFrame mainFrame;

    public BookController(BookService service, MainAppFrame mainFrame) {
        this.service = service;
        this.mainFrame = mainFrame;
    }

    public void createBookRequest(BookDTO bookData) {
        service.createBook(bookData);
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
        if (mainFrame != null) {
            mainFrame.switchToView("BOOK_DETAIL", bookId);
        }
    }

    public void requestBookEdit(Integer bookId, BookDTO bookData) {
        try {
            service.updateBook(bookId, bookData);
        } catch (ResourceNotFoundException e) {

        } catch (BusinessRuleException e) {

        } catch (DataAccessException e) {
            
        }

    }

    public BookDTO requestBookById(Integer bookId) {
        Book book = service.findBookById(bookId);
        BookDTO bookDTO = new BookDTO(book.getTitle(), book.getAuthor(), book.getPublishedYear(), book.getCategory(), book.getIsbn());
        return bookDTO;
    }

}

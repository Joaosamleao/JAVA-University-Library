package Controller;

import java.util.ArrayList;
import java.util.List;

import DTO.BookDTO;
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

        } catch (Exception e) {
            System.out.print("Teste"); // Substituir
        }
        return null;
    }

    public void requestBookDetailsView(Integer bookId) {
        if (mainFrame != null) {
            mainFrame.switchToView("BOOK_DETAIL", bookId);
        }
    }

    public void requestBookEditView(Integer bookId) {
        if (mainFrame != null) {
            mainFrame.switchToView("BOOK_EDIT", bookId);
        }
    }

    public BookDTO requestBookById(Integer bookId) {
        Book book = service.findBookById(bookId);
        BookDTO bookDTO = new BookDTO(book.getTitle(), book.getAuthor(), book.getPublishedYear(), book.getCategory(), book.getIsbn());
        return bookDTO;
    }

}

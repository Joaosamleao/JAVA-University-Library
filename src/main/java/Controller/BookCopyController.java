package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.BookCopy;
import Service.BookCopyService;
import View.MainAppFrame;

public class BookCopyController {
    
    private final BookCopyService service;
    private final MainAppFrame mainFrame;

    public BookCopyController(BookCopyService service, MainAppFrame mainFrame) {
        this.service = service;
        this.mainFrame = mainFrame;
    }

    public void createCopyRequest(Integer bookId, String barcode, String locationCode) {
        service.createCopy(bookId, barcode, locationCode);
    }

    public List<Object[]> loadCopiesRequest(Integer id) {
        try {
            List<BookCopy> copies = service.findCopiesByBook(id);
            List<Object[]> dataForView = new ArrayList<>();

            for (BookCopy copy : copies) {
                Object[] rowData = new Object[] {
                    copy.getIdCopy(),
                    copy.getBarcode(),
                    copy.getStatus(),
                    copy.getLocationCode()
                };
                dataForView.add(rowData);
            }
            return dataForView;
        } catch (Exception e) {
            // Substituir
        }
        return null;
    }

}

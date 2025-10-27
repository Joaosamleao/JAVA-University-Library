package Controller;

import java.util.ArrayList;
import java.util.List;

import DTO.CopyDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.ResourceNotFoundException;
import Model.BookCopy;
import Service.BookCopyService;
import View.MainAppFrame;

public class BookCopyController {
    
    private final BookCopyService service;
    private MainAppFrame mainFrame;

    public BookCopyController(BookCopyService service) {
        this.service = service;
    }

    public void createCopyRequest(Integer bookId, String barcode, String locationCode) {
        try {
            service.createCopy(bookId, barcode, locationCode);
        } catch (DataCreationException | BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: Couldn't save copy: " + e.getMessage());
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't save to database");
        }
    }

    public List<Object[]> loadCopiesRequest(Integer id) {
        System.out.println("Load copies recebe a request com ID: " + id);
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
                System.out.println("Copy Found: " + java.util.Arrays.toString(rowData));
                dataForView.add(rowData);
            }
            System.out.println("Load copies chegou ao fim");
            return dataForView;
        } catch (DataAccessException e) {
            System.out.print("Exception no Load copies");
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access database");
        }
        return null;
    }

    public void requestCopyEdit(Integer copyId, CopyDTO copyData) {
        try {
            service.updateCopy(copyId, copyData);
        } catch (ResourceNotFoundException | BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: Couldn't update copy info: " + e.getMessage());
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't save to database: " + e.getMessage());
        }
    }

    public CopyDTO requestCopyById(Integer copyId) {
        BookCopy copy = service.findCopyById(copyId);
        CopyDTO copyDTO = new CopyDTO(copy.getBarcode(), copy.getLocationCode());
        return copyDTO;
    }

    public CopyDTO requestCopyByBarcode(String barcode) {
        BookCopy copy = service.copyByBarcode(barcode);
        CopyDTO copyDTO = new CopyDTO();
        copyDTO.setCopyId(copy.getIdCopy());
        return copyDTO;
    }

    public void setMainFrame(MainAppFrame frame) {
        this.mainFrame = frame;
    }

}

package Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DTO.FineDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.FormatErrorException;
import Exceptions.ResourceNotFoundException;
import Model.Fine;
import Service.FineService;
import Service.LoanService;
import Service.UserService;
import View.MainAppFrame;

public class FineController {
    
    private final FineService service;
    private final UserService userService;

    private MainAppFrame mainFrame;

    public FineController(FineService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    public void createFineRequest(FineDTO fineData) {
        try {
            Fine fine = new Fine(fineData.getLoan() ,fineData.getIdUser(), fineData.getAmount());
            service.createFine(fine);
        } catch (BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
        } catch (DataCreationException e) {
            mainFrame.showErrorMessage("ERROR: Couldn't create loan");
        }
    }

    public List<Object[]> loadFinesRequest() {
        try {
            List<Fine> fines = service.readAllFines();
            List<Object[]> dataForView = new ArrayList<>();

            for (Fine fine : fines) {
                Object[] rowData = new Object[] {
                    fine.getLoan(),
                    userService.findUserById(fine.getIdUser()).getRegistration(),
                    fine.getAmount(),
                    fine.getIssueDate()
                };
                dataForView.add(rowData);
            }
            return dataForView;
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database");
        }
        return null;
    }

    public List<Object[]> loadFinesByUser(Integer id) {
        try {
            List<Fine> fines = service.findFinesByUserId(id);
            List<Object[]> dataForView = new ArrayList<>();

            for (Fine fine : fines) {
                Object[] rowData = new Object[] {
                    fine.getLoan(),
                    userService.findUserById(fine.getIdUser()).getRegistration(),
                    fine.getAmount(),
                    fine.getIssueDate()
                };
                dataForView.add(rowData);
            }
            return dataForView;
        } catch (DataAccessException e) {
            mainFrame.showWarningMessage("UNEXPECTED ERROR: Couldn't access the database");
        }
        return null;
    }

    public void completeFine(Integer id, FineDTO fineData) {
        try {
            service.updateFine(id, fineData);
        } catch (ResourceNotFoundException | BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database");
        }
    }

    public LocalDate formatDateString(String dateString) {
        try {
            return LoanService.isValidDate(dateString);
        } catch (FormatErrorException e) {
            mainFrame.showWarningMessage("ERROR: Couldn't convert " + dateString + " to a Date");
        }
        return null;
    }

}



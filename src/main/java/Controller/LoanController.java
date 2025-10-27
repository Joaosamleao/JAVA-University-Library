package Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DTO.LoanDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.FormatErrorException;
import Exceptions.ResourceNotFoundException;
import Model.Loan;
import Model.Enum.ItemStatus;
import Service.BookCopyService;
import Service.LoanService;
import Service.UserService;
import View.MainAppFrame;

public class LoanController {
    
    private final LoanService loanService;
    private final UserService userService;
    private final BookCopyService copyService;
    private MainAppFrame mainFrame;

    public LoanController(LoanService loanService, UserService userService, BookCopyService copyService) {
        this.loanService = loanService;
        this.userService = userService;
        this.copyService = copyService;
    }

    public void createLoanRequest(LoanDTO loanData) {
        try {
            Loan loan = new Loan(loanData.getUserId(), loanData.getCopyId());
            loanService.createLoan(loan);
            copyService.updateCopyStatus(loanData.getCopyId(), ItemStatus.BORROWED);
        } catch (BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
        } catch (DataCreationException e) {
            mainFrame.showErrorMessage("ERROR: Couldn't create loan");
        }
    }

    public List<Object[]> loadLoansRequest() {
        try {
            List<Loan> loans = loanService.readAllLoans();
            List<Object[]> dataForView = new ArrayList<>();

            for (Loan loan : loans) {
                Object[] rowData = new Object[] {
                    loan.getIdLoan(),
                    userService.findUserById(loan.getUserId()).getRegistration(),
                    copyService.findCopyById(loan.getCopyId()).getBarcode(),
                    loan.getLoanDate(),
                    loan.getExpectedReturnDate()
                };
                dataForView.add(rowData);
            }
            return dataForView;
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database");
        }
        return null;
    }

    public List<Object[]> loadLoansByUser(Integer id) {
        try {
            List<Loan> loans = loanService.findLoanByUserId(id);
            List<Object[]> dataForView = new ArrayList<>();

            for (Loan loan : loans) {
                Object[] rowData = new Object[] {
                    loan.getIdLoan(),
                    userService.findUserById(loan.getUserId()).getRegistration(),
                    copyService.findCopyById(loan.getCopyId()).getBarcode(),
                    loan.getLoanDate(),
                    loan.getExpectedReturnDate()
                };
                dataForView.add(rowData);
            }
            return dataForView;
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database");
        }
        return null;
    }

    public void completeLoan(Integer id, LoanDTO loanData) {
        try {
            loanService.updateLoan(id, loanData);
            copyService.updateCopyStatus(loanService.findLoanById(id).getCopyId(), ItemStatus.AVAILABLE);
        } catch (ResourceNotFoundException | BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database");
        }
    }

    public LoanDTO findLoanById(Integer id) {
        try {
            Loan loan = loanService.findLoanById(id);
            LoanDTO loanData = new LoanDTO(); loanData.setLoanDate(loan.getLoanDate());
            return loanData;
        } catch (ResourceNotFoundException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database");
        }
        return null;
    }

    public void setMainFrame(MainAppFrame frame) {
        this.mainFrame = frame;
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

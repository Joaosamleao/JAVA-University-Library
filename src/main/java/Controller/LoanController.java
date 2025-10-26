package Controller;

import java.util.ArrayList;
import java.util.List;

import DTO.LoanDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.ResourceNotFoundException;
import Model.Loan;
import Service.BookCopyService;
import Service.LoanService;
import Service.UserService;
import View.MainAppFrame;

public class LoanController {
    
    private final LoanService loanService;
    private final UserService userService;
    private final BookCopyService copyService;
    private final MainAppFrame mainFrame;

    public LoanController(LoanService loanService, UserService userService, BookCopyService copyService, MainAppFrame mainFrame) {
        this.loanService = loanService;
        this.userService = userService;
        this.copyService = copyService;
        this.mainFrame = mainFrame;
    }

    public void createLoanRequest(LoanDTO loanData) {
        try {
            Loan loan = new Loan(loanData.getUserId(), loanData.getCopyId());
            loanService.createLoan(loan);
        } catch (DataCreationException | BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: Couldn't create loan: " + e.getMessage());
        }
    }

    public List<Object[]> loadLoansRequest() {
        try {
            List<Loan> loans = loanService.readAllLoans();
            List<Object[]> dataForView = new ArrayList<>();

            for (Loan loan : loans) {
                Object[] rowData = new Object[] {
                    loan.getUserId(),
                    copyService.findCopyById(loan.getCopyId()).getBarcode(),
                    loan.getLoanDate(),
                    loan.getExpectedReturnDate()
                };
                dataForView.add(rowData);
            }
            return dataForView;
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database: " + e.getMessage());
        }
        return null;
    }

    public List<Object[]> loadLoansByUser(Integer id) {
        try {
            List<Loan> loans = loanService.findLoanByUserId(id);
            List<Object[]> dataForView = new ArrayList<>();

            for (Loan loan : loans) {
                Object[] rowData = new Object[] {
                    userService.findUserById(loan.getUserId()).getRegistration(),
                    copyService.findCopyById(loan.getCopyId()).getBarcode(),
                    loan.getLoanDate(),
                    loan.getExpectedReturnDate()
                };
                dataForView.add(rowData);
            }
            return dataForView;
        } catch (ResourceNotFoundException e) {
            mainFrame.showWarningMessage("WARNING: No users found: " + e.getMessage());
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database: " + e.getMessage());
        }
        return null;
    }

}

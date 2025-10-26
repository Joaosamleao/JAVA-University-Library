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

public class LoanController {
    
    private final LoanService loanService;
    private final UserService userService;
    private final BookCopyService copyService;

    public LoanController(LoanService loanService, UserService userService, BookCopyService copyService) {
        this.loanService = loanService;
        this.userService = userService;
        this.copyService = copyService;
    }

    public void createLoanRequest(LoanDTO loanData) {
        try {
            Loan loan = new Loan(loanData.getUserId(), loanData.getCopyId());
            loanService.createLoan(loan);
        } catch (BusinessRuleException e) {

        } catch (DataCreationException e) {

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

        } catch (DataAccessException e) {

        }
        return null;
    }

}

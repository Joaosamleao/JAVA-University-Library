package Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import DTO.LoanDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.FormatErrorException;
import Exceptions.ResourceNotFoundException;
import Model.Enum.ItemStatus;
import Model.Fine;
import Model.Loan;
import Service.BookCopyService;
import Service.FineService;
import Service.LoanService;
import Service.UserService;
import View.MainAppFrame;

public class LoanController {
    
    private final LoanService loanService;
    private final UserService userService;
    private final BookCopyService copyService;
    private final FineService fineService;
    private MainAppFrame mainFrame;

    public LoanController(LoanService loanService, UserService userService, BookCopyService copyService, FineService fineService) {
        this.loanService = loanService;
        this.userService = userService;
        this.copyService = copyService;
        this.fineService = fineService;
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

    public boolean completeLoan(Integer id, LocalDate actualDate) {
        try {
            Loan originalLoan = loanService.findLoanById(id);
            loanService.updateLoan(originalLoan, actualDate);
            copyService.updateCopyStatus(originalLoan.getCopyId(), ItemStatus.AVAILABLE);

            long daysOverdue = ChronoUnit.DAYS.between(originalLoan.getExpectedReturnDate(), originalLoan.getActualReturnDate());

            if (daysOverdue > 0) {
                BigDecimal ratePerDay = new BigDecimal("3.00");
                BigDecimal totalAmount = ratePerDay.multiply(new BigDecimal(daysOverdue));

                Fine fineData = new Fine(id, originalLoan.getUserId(), totalAmount);
                fineService.createFine(fineData);
            }

            return true;
        } catch (ResourceNotFoundException | BusinessRuleException e) {
            mainFrame.showWarningMessage("WARNING: " + e.getMessage());
            return false;
        } catch (DataAccessException e) {
            mainFrame.showErrorMessage("UNEXPECTED ERROR: Couldn't access the database");
            return false;
        }
    }

    public LoanDTO findLoanById(Integer id) {
        try {
            Loan loan = loanService.findLoanById(id);
            LoanDTO loanData = new LoanDTO(); loanData.setLoanDate(loan.getLoanDate());
            loanData.setExpectedReturnDate(loan.getExpectedReturnDate());
            loanData.setUserId(loan.getUserId());
            loanData.setCopyId(loan.getCopyId());
            System.out.println("Achado empréstimo com ID: " + loanData.getLoanId() + " e Actual Return Date: " + loanData.getActualReturnDate());
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

package Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
import Exceptions.FormatErrorException;
import Exceptions.ResourceNotFoundException;
import Model.Loan;
import Repository.Interface.LoanRepository;


// Operações remetente ao usuário de tipo Clerk
// Cadastrar empréstimo
// Marcar devolução

// TO-D0 25/10/2025
// Create Loan ✅
// Read Loan ✅
// Update Loan ✅
// Delete Loan ✅

public class LoanService {
    
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(Loan loanData) throws DataCreationException, BusinessRuleException {
        Optional<Loan> existingActiveLoan = loanRepository.findActiveLoanByCopyId(loanData.getCopyId());
        if (existingActiveLoan.isPresent()) {
            throw new BusinessRuleException("Copy is not available for loans");
        }

        Loan loan = new Loan(loanData.getUserId(), loanData.getCopyId());
        loanRepository.create(loan);

        return loan;
    }

    public Loan findLoanById(Integer id) throws DataAccessException, ResourceNotFoundException {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        return optionalLoan.orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));
    }

    public List<Loan> readAllLoans() throws DataAccessException {
        return loanRepository.findAll();
    }

    public List<Loan> findLoanByUserId(Integer id) throws DataAccessException {
        return loanRepository.findLoanByUserId(id);
    }

    public void updateLoan(Loan existingLoan, LocalDate actualDate) throws DataAccessException, ResourceNotFoundException, BusinessRuleException {
        if (actualDate.isBefore(existingLoan.getLoanDate())) {
            throw new BusinessRuleException("Invalid value for return date");
        }

        existingLoan.setActualReturnDate(actualDate);
        loanRepository.update(existingLoan);
    }

    public void deleteLoan(Integer id) throws DataAccessException {
        loanRepository.delete(id);
    }

    public static LocalDate isValidDate(String dateString) throws FormatErrorException {
        if (dateString.isEmpty()) {
            throw new FormatErrorException("Invalid value for return date");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate convertedDate;

        try {
            convertedDate = LocalDate.parse(dateString, formatter);
            return convertedDate;
        } catch (DateTimeParseException e) {
            throw new FormatErrorException("Couldn't convert: " + dateString + " to a Date");
        }
    }

}

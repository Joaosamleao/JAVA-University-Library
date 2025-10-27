package Service;

import java.util.List;
import java.util.Optional;

import DTO.LoanDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
import Exceptions.DataCreationException;
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
        return optionalLoan.orElseThrow(() -> new ResourceNotFoundException("ERROR: Loan not found with ID: " + id));
    }

    public List<Loan> readAllLoans() throws DataAccessException {
        return loanRepository.findAll();
    }

    public List<Loan> findLoanByUserId(Integer id) throws DataAccessException, ResourceNotFoundException {
        return loanRepository.findLoanByUserId(id);
    }

    public void updateLoan(Integer id, LoanDTO loanData) throws DataAccessException, ResourceNotFoundException, BusinessRuleException {
        if (loanData.getActualReturnDate().isBefore(loanData.getLoanDate())) {
            throw new BusinessRuleException("WARNING: Invalid value for return date");
        }
        Loan existingLoan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("ERROR: Loan not found with ID: " + id)));

        existingLoan.setActualReturnDate(loanData.getActualReturnDate());
        loanRepository.update(existingLoan);
    }

    public void deleteLoan(Integer id) throws DataAccessException {
        loanRepository.delete(id);
    }

}

package Service;

import java.util.List;
import java.util.Optional;

import DTO.LoanDTO;
import Exceptions.BusinessRuleException;
import Exceptions.DataAccessException;
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

    public Loan createLoan(LoanDTO loanData) throws DataAccessException, BusinessRuleException {
        Optional<Loan> loanWithSameCopy = loanRepository.findLoanByCopyId(loanData.getCopyId());
        if (loanWithSameCopy.isPresent() && !loanWithSameCopy.get().getCopyId().equals((loanData.getCopyId()))) {
            throw new BusinessRuleException("ERROR: Copy is already borrowed");
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

    public Loan findLoanByUserId(Integer id) throws DataAccessException, ResourceNotFoundException {
        Optional<Loan> optionalLoan = loanRepository.findLoanByUserId(id);
        return optionalLoan.orElseThrow(() -> new ResourceNotFoundException("ERROR: Loan not found with User ID: " + id));
    }

    public void updateLoan(Integer id, LoanDTO loanData) throws DataAccessException, ResourceNotFoundException {
        Loan existingLoan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("ERROR: Loan not found with ID: " + id)));

        existingLoan.setActualReturnDate(loanData.getActualReturnDate());
        loanRepository.update(existingLoan);
    }

    public void deleteLoan(Integer id) throws DataAccessException {
        loanRepository.delete(id);
    }

}

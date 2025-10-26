package Repository.Interface;

import java.util.Optional;

import Model.Loan;

public interface LoanRepository extends GenericRepository<Loan, Integer> {
    
    Optional<Loan> findLoanByUserId(Integer id);

    Optional<Loan> findLoanByCopyId(Integer id);

}

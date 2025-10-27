package Repository.Interface;

import java.util.List;
import java.util.Optional;

import Model.Loan;

public interface LoanRepository extends GenericRepository<Loan, Integer> {
    
    List<Loan> findLoanByUserId(Integer id);

    Optional<Loan> findLoanByCopyId(Integer id);

    Optional<Loan> findActiveLoanByCopyId(Integer id);

}

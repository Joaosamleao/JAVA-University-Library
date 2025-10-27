package Repository.Interface;

import java.util.List;
import java.util.Optional;

import Model.Fine;

public interface FineRepository extends GenericRepository<Fine, Integer> {
    
    List<Fine> findFineByUserId(Integer id);

    Optional<Fine> findActiveFineByLoanId(Integer id);

}

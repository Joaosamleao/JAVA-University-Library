package Repository.Interface;

import java.util.List;
import java.util.Optional;

import Model.BookCopy;
import Model.Enum.ItemStatus;

public interface BookCopyRepository extends GenericRepository<BookCopy, Integer> {
    
    Optional<BookCopy> findByBarCode(String barcode);

    List<BookCopy> findByStatus(ItemStatus status);

    List<BookCopy> findByBook(Integer id);

}

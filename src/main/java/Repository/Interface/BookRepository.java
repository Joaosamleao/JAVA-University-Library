package Repository.Interface;

import java.util.Optional;

import Model.Book;

public interface BookRepository extends GenericRepository<Book, Integer> {
    
    Optional<Book> findByIsbn(String isbn);

}

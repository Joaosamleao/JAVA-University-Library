package Repository.Interface;

import java.util.List;
import java.util.Optional;

// Operações comuns aos objetos do sistema
// Create
// Read
// Update
// Delete

public interface GenericRepository<T, ID> {
    
    T create(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void update(T entity);

    void delete(ID id);

}

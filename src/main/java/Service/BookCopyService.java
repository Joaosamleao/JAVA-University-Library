package Service;

// Regras de Negócio sobre a entidade BookCopy (Cópia)

import Repository.Interface.BookCopyRepository;


// Operações remetente ao usuário de tipo Librarian
// Cadastrar cópias de um livro
// Edtiar cópias de um livro
// Remover cópias de um livro

// TO-DO 21/10/2025
// Create copies
// Read copies
// Update copies
// Delete copies

public class BookCopyService {
    
    private final BookCopyRepository copyRepository;

    public BookCopyService(BookCopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

}

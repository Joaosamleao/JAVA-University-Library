package Service;

import java.util.List;

import Model.Book;
import Repository.Interface.BookRepository;

// Regras de Negócio sobre a entidade Book (Livro)

// Operações Remetentes ao usuário de tipo Librarian
// Cadastrar Livro
// Remover Livro
// Editar Livro

// Operações remetente ao usuário do tipo Student (Estudante):
// Visualizar o acervo de livros

// TO-DO 18/10/2025
// Create Book
// Read Book
// Update Book
// Delete Book

public class BookService {

    private BookRepository bookRepository;
    
    public BookService() {

    }

    public Book createBook(String title, String author, int publishedYear, String category, String isbn) {
        return null;
    }

    public Book findBookById(Long id) {
        return null;
    }

    public List<Book> readAllBooks() {
        return null;
    }

    public void updateBook() {

    }

    public void deleteBook() {

    }

}

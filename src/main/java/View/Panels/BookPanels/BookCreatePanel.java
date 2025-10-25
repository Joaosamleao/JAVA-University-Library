package View.Panels.BookPanels;

import javax.swing.JPanel;

import Controller.BookController;

public class BookCreatePanel extends JPanel {
    
    private final BookController bookController;

    public BookCreatePanel(BookController bookController) {
        this.bookController = bookController;
    }

}

package View.Panels.BookPanels;

import javax.swing.JPanel;

import Controller.BookController;

public class BookEditPanel extends JPanel {
    
    private final BookController bookController;

    public BookEditPanel(BookController bookController) {
        this.bookController = bookController;
    }

}

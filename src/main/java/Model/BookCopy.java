package Model;

import Model.Enum.ItemStatus;

public class BookCopy {
    
    private int idCopy;
    private Book book;
    private String barcode;
    private ItemStatus status;
    private String locationCode;

    public BookCopy(Book book, String barcode, ItemStatus status, String locationCode) {
        this.book = book;
        this.barcode = barcode;
        this.status = status;
        this.locationCode = locationCode;
    }

    //Getters
    public int getIdItem() { return idCopy; }
    public Book getBook() { return book; }
    public String getBarcode() { return barcode; }
    public ItemStatus getStatus() { return status; }
    public String getLocationCode() { return locationCode; }

    //Setters
    public void setIdCopy(int idCopy) { this.idCopy = idCopy; }
    public void setStatus(ItemStatus status) { this.status = status; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }

}

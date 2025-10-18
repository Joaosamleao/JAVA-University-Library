package Model;

import Model.Enum.ItemStatus;

public class BookItem {
    
    private Long idItem;
    private Book book;
    private String barcode;
    private ItemStatus status;
    private String locationCode;

    public BookItem(Book book, String barcode, ItemStatus status, String locationCode) {
        this.book = book;
        this.barcode = barcode;
        this.status = status;
        this.locationCode = locationCode;
    }

    //Getters
    public Long getIdItem() { return idItem; }
    public Book getBook() { return book; }
    public String getBarcode() { return barcode; }
    public ItemStatus getStatus() { return status; }
    public String getLocationCode() { return locationCode; }

    //Setters
    public void setStatus(ItemStatus status) { this.status = status; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }

}

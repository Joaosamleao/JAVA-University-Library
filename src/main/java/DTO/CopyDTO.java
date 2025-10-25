package DTO;

import Model.Book;
import Model.Enum.ItemStatus;

public class CopyDTO {
    
    private final Book book;
    private final String barcode;
    private final ItemStatus status;
    private final String locationCode;

    public CopyDTO(Book book, String barcode, ItemStatus status, String locationCode) {
        this.book = book;
        this.barcode = barcode;
        this.status = status;
        this.locationCode = locationCode;
    }

    public Book getBook() { return book; }
    public String getBarcode() { return barcode; }
    public ItemStatus getStatus() { return status; }
    public String getLocationCode() { return locationCode; }

}

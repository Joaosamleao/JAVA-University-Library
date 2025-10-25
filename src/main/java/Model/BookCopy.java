package Model;

import Model.Enum.ItemStatus;

public class BookCopy {
    
    private Integer idCopy;

    private Integer bookId;
    private String barcode;
    private ItemStatus status;
    private String locationCode;

    public BookCopy() {

    }

    public BookCopy(Integer bookId, String barcode, String locationCode) {
        this.bookId = bookId;
        this.barcode = barcode;
        this.status = ItemStatus.AVAILABLE;
        this.locationCode = locationCode;
    }

    //Getters
    public Integer getIdCopy() { return idCopy; }
    public Integer getBookId() { return bookId; }
    public String getBarcode() { return barcode; }
    public ItemStatus getStatus() { return status; }
    public String getLocationCode() { return locationCode; }

    //Setters
    public void setIdCopy(Integer idCopy) { this.idCopy = idCopy; }
    public void setBookId(Integer id) { this.bookId = id; }
    public void setStatus(ItemStatus status) { this.status = status; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }

}

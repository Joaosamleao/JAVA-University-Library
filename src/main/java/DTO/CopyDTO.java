package DTO;

public class CopyDTO {
    
    private Integer copyId;

    private String barcode;
    private String locationCode;

    public CopyDTO() {

    }

    public CopyDTO(String barcode, String locationCode) {
        this.barcode = barcode;
        this.locationCode = locationCode;
    }

    public Integer getCopyId() { return copyId; }
    public String getBarcode() { return barcode; }
    public String getLocationCode() { return locationCode; }

    public void setCopyId(Integer id) { this.copyId = id; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }

}

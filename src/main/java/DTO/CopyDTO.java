package DTO;

public class CopyDTO {
    
    private final String barcode;
    private final String locationCode;

    public CopyDTO(String barcode, String locationCode) {
        this.barcode = barcode;
        this.locationCode = locationCode;
    }

    public String getBarcode() { return barcode; }
    public String getLocationCode() { return locationCode; }

}

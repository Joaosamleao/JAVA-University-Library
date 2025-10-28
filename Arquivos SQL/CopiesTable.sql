USE university;

CREATE TABLE copies (
    id_copy INT NOT NULL AUTO_INCREMENT,
    id_book INT NOT NULL,
    barcode VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    location_code VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_copy),
    UNIQUE KEY (barcode),
    FOREIGN KEY (id_book) REFERENCES books(id_book)
)
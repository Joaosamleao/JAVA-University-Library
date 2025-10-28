USE university;

CREATE TABLE books (
    id_book INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    publication_year YEAR,
    category VARCHAR(100),
    isbn VARCHAR(20) UNIQUE,
    PRIMARY KEY(id_book)
) AUTO_INCREMENT = 1000;
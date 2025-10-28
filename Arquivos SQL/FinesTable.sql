USE university;

CREATE TABLE fines (
    id_fine INT NOT NULL AUTO_INCREMENT,
    id_loan INT NOT NULL,
    id_user INT NOT NULL,
    amount DECIMAL NOT NULL,
    issue_date DATE NOT NULL,
    payment_date DATE NULL,
    PRIMARY KEY (id_fine),
    UNIQUE KEY (id_loan),
    FOREIGN KEY (id_loan) REFERENCES loans(id_loan),
    FOREIGN KEY (id_user) REFERENCES users(id_user)
)
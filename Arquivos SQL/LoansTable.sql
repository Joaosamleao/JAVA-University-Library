USE university;

CREATE TABLE loans (
    id_loan INT NOT NULL AUTO_INCREMENT,
    id_user INT NOT NULL,
    id_copy INT NOT NULL,
    loan_date DATE NOT NULL,
    expected_return_date DATE NOT NULL,
    actual_return_date DATE NULL,
    PRIMARY KEY (id_loan),
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_copy) REFERENCES copies(id_copy)
)
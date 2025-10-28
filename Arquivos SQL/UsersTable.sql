USE university;

CREATE TABLE users (
    id_user INT NOT NULL AUTO_INCREMENT,
    user_type VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    registration VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(72) NOT NULL,
    PRIMARY KEY (id_user),
    UNIQUE KEY (registration),
    UNIQUE KEY (email)
)
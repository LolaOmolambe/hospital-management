CREATE TABLE IF NOT EXISTS STAFF (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    uuid VARCHAR(50) NOT NULL,
    registration_date DATE NOT NULL
    );

INSERT INTO STAFF(id, name, uuid, registration_date) VALUES ('1', 'Test', 'SF8C0DF6B2', '2020-09-21');

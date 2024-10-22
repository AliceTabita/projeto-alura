CREATE TABLE Course(
                       id bigint(20) NOT NULL AUTO_INCREMENT,
                       name varchar(50) NOT NULL,
                       code varchar(10) NOT NULL UNIQUE,
                       description varchar(255) NOT NULL,
                       status enum('ACTIVE', 'INACTIVE') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE',
                       inactivationDate datetime,
                       instructorId bigint(20) NOT NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (instructorId) REFERENCES User (id)
)
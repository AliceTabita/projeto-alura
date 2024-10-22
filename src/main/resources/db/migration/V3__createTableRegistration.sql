CREATE TABLE Registration(
                             id bigint(20) NOT NULL AUTO_INCREMENT,
                             registrationDate datetime,
                             userId bigint(20) NOT NULL,
                             courseId bigint(20) NOT NULL,
                             PRIMARY KEY (id),
                             FOREIGN KEY (userId) REFERENCES User (id),
                             FOREIGN KEY (courseId) REFERENCES Course (id)
)
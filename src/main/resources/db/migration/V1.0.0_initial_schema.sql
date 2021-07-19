CREATE TABLE LOCATION (
    Id varchar(256) PRIMARY KEY NOT NULL,
    Name varchar(256),
    Address.Country varchar(256),
    Address.City varchar(256),
    Address.County varchar(256),
    Address.StreetAddress varchar(256),
)
CREATE TABLE IF NOT EXISTS LOCATION (
    LOCATION_ID INT  AUTO_INCREMENT PRIMARY KEY NOT NULL,
    NAME VARCHAR(256),
    COUNTRY VARCHAR(256),
    CITY VARCHAR(256),
    COUNTY VARCHAR(256),
    STREET_ADDRESS VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS PRODUCT_CATEGORY(
    PRODUCT_CATEGORY_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    NAME VARCHAR(256),
    DESCRIPTION VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS SUPPLIER(
    SUPPLIER_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    NAME VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS CUSTOMER(
    CUSTOMER_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    FIRSTNAME VARCHAR(256),
    LASTNAME VARCHAR(256),
    USERNAME VARCHAR(256),
    PASSWORD VARCHAR(256),
    EMAIL_ADDRESS VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS REVENUE(
    REVENUE_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    LOCATION_ID INT REFERENCES LOCATION(LOCATION_ID),
    DATE_RECORDED DATE,
    CALCULATED_SUM DECIMAL
);

CREATE TABLE IF NOT EXISTS PRODUCT(
    PRODUCT_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    NAME VARCHAR(256),
    DESCRIPTION VARCHAR(256),
    PRICE DECIMAL,
    WEIGHT DOUBLE,
    PRODUCT_CATEGORY_ID INT  REFERENCES PRODUCT_CATEGORY(PRODUCT_CATEGORY_ID),
    SUPPLIER_ID INT  REFERENCES SUPPLIER(SUPPLIER_ID),
    IMAGE_URL VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS STOCK(
    STOCK_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    PRODUCT_ID INT  REFERENCES PRODUCT(PRODUCT_ID),
    LOCATION_ID INT  REFERENCES LOCATION(LOCATION_ID),
    QUANTITY INT,
    CONSTRAINT UNIQ_ST_COLS UNIQUE(STOCK_ID, PRODUCT_ID, LOCATION_ID)
);

CREATE TABLE IF NOT EXISTS ORDERS(
    ORDER_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    CUSTOMER_ID INT  REFERENCES CUSTOMER(CUSTOMER_ID),
    SHIPPED_FROM INT  REFERENCES LOCATION(LOCATION_ID),
    CREATED_AT TIMESTAMP,
    QUANTITY INT,
    COUNTRY VARCHAR(256),
    CITY VARCHAR(256),
    COUNTY VARCHAR(256),
    STREET_ADDRESS VARCHAR(256)

);

CREATE TABLE IF NOT EXISTS ORDER_DETAIL(
    ORDER_DETAIL_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ORDER_ID INT  REFERENCES ORDERS(ORDER_ID),
    PRODUCT_ID INT  REFERENCES PRODUCT(PRODUCT_ID),
    QUANTITY INT,
    CONSTRAINT UNIQ_OD_COLS UNIQUE(ORDER_DETAIL_ID, ORDER_ID, PRODUCT_ID)

);

CREATE TABLE item
(
    barcode text  NOT NULL primary key,
    name    text  NOT NULL,
    unit    text  NOT NULL,
    price   money NOT NULL,
    type    text   NOT NULL
);

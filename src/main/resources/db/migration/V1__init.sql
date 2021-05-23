CREATE TABLE item
(
    barcode text  NOT NULL primary key,
    name    text  NOT NULL,
    unit    text,
    price   money NOT NULL,
    type    text  NOT NULL
);

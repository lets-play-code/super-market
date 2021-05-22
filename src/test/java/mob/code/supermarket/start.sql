CREATE TABLE item
(
    barcode text  NOT NULL primary key,
    name    text  NOT NULL,
    unit    text  NULL,
    price   double NOT NULL,
    type    int   NOT NULL
);

CREATE TABLE promotion
(
    id   text NOT NULL primary key,
    name text NOT NULL,
    type int  NOT NULL
);

CREATE TABLE promotion_item
(
    promotion_id text NOT NULL,
    item_barcode text NOT NULL
);
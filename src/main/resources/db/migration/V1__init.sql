CREATE TABLE item
(
    barcode text  NOT NULL primary key,
    name    text  NOT NULL,
    unit    text  NOT NULL,
    price   money NOT NULL,
    type    int   NOT NULL
);

insert into item(barcode, name, unit, price, type) values('81102001', 'juice', '', 5, 0);
insert into item(barcode, name, unit, price, type) values('81102002', 'biscuit','', 13, 0);
insert into item(barcode, name, unit, price, type) values('81103001', 'apple', 'KG', 4, 0);


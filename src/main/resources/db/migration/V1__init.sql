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

insert into item(barcode, name, unit, price, type) values('12345678', 'pizza', '', 15, 0);
insert into item(barcode, name, unit, price, type) values('22345678', 'milk', 'L', 12.3, 1);





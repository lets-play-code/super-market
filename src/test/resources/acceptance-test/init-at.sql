CREATE TABLE item (
  barcode text NOT NULL primary key,
  name text NOT NULL,
  unit text NOT NULL,
  price money NOT NULL,
  type int NOT NULL
);

CREATE TABLE promotion (
  id text NOT NULL primary key,
  name text NOT NULL,
  type int NOT NULL
);

CREATE TABLE promotion_item (
  promotion_id text NOT NULL,
  item_barcode text NOT NULL
);


INSERT INTO item (barcode, name, unit, price, type) VALUES
('82202001', 'juice', '', 5, 0),
('82202002', 'biscuit', '', 13, 0),
('82202003', 'cola', '250ml', 4.2, 0),
('82203001', 'apple', 'KG', 10, 1),
('82203002', 'rice', 'KG', 7.09, 1),
('82203003', 'orange', 'KG', 13.07, 1),

('00000000', 'NA', '', 0, 0) ;

INSERT INTO promotion(id, name, type) VALUES
('P001', 'Two-for-one', 0),
('P002', '95%', 1);

INSERT INTO promotion_item(promotion_id, item_barcode) VALUES
('P001', '82202001'),
('P001', '82202002'),
('P002', '82202002'),
('P002', '82203002');
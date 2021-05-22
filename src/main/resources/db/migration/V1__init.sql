create table t_item
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `barcode` varchar(30) DEFAULT NULL,
    `name`    varchar(30) DEFAULT NULL,
    `unit`    varchar(10) DEFAULT NULL,
    `type`    varchar(10) DEFAULT NULL,
    `price`   float       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=415 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

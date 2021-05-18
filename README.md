超市收银机练习
---

你需要实现一个超市收银机的Restful service。
这个service需要实现一个 `/scan` 的API，从POS机扫码器获取条码，并返回收据信息。

可以预见到超市将会有促销业务，所以收银模块也应该支持。不过目前还不需要马上支持。

起始工程中以及包括一些代码和API，这些都是用来作为示例的，你可以把它们移除。
除了数据库表结构，API定义，properties 文件中已经定义的属性，其它的代码都可以进行修改。

这个练习的目的是模拟现实中需求逐步演进的情况。请逐个完成每个需求，前序需求没有完成之前不要提前阅读后续需求。

采用极限编程实践进行编码，包括TDD，重构，结对编程或MOB。
当你完成时，把代码push到小组分支（无需提交PR），并与PO确认是否完成了需求。

- [Story 1](./story1.md)
- [Story 2](./story2.md)
- [Story 3](./story3.md)
- [Story 4](./story4.md)
- [Story 5](./story5.md)
- [Story 6](./story6.md)

### 参考信息
超市中的商品保存在 `item` 表中，以下是示例字段（部分字段省略）

 barcode  | name | unit | price | type 
  ---     |  --- | ---  |  ---  | ---
 12345678 | pizza|      |  15   |  1
 22345678 | milk |  L   |  12.3 |  0 

促销信息保存在 `promotion` 表中，以下是示例字段（部分字段省略）

 id   |     name    | type
 ---  |     ---     | ---  
 P001 | just buy it | 0
 P002 | hot sale    | 0

`item` 与 `promotion` 之间是多对多关系。两者的关联信息保存在 `promotion_item` 表中。

promotion_id | item_barcode    
---  |   ---     
P001 | 12345678
P002 | 22345678


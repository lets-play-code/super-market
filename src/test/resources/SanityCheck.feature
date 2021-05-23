Feature: sanity check

  Scenario: ping
    When I 'GET' the api '/ping'
    Then the server response will match '{ data: "pong" }'

#  Scenario: show sample items
#    When I 'GET' the api '/item'
#    And the server response will match
#      """
#      { data: [
#        { barcode: "81102001", name: "juice", unit: "", price: 5 },
#        { barcode: "81102002", name: "biscuit", unit: "", price: 13 },
#        { barcode: "81103001", name: "apple", unit: "KG", price: 4 }
#      ] }
#      """

  Scenario: show sample error
    When I 'GET' the api '/dontcall'
    And the server response will match
      """
      { error: "It is a sample error" }
      """

#  Scenario: show scan result
#    When I 'POST' the api '/scan' with
#    """
#    [
#      "12345678",
#      "22345678-3"
#    ]
#    """
#    And the server response will match
#      """
#      {
#      "data": [
#        "****** SuperMarket receipt ******",
#        "pizza: 1 x 15.00 --- 15.00",
#        "milk: 3(L) x 12.30 --- 36.90",
#        "---------------------------------",
#        "total: 51.90(CNY)",
#        "*********************************"
#        ]
#      }
#      """

  Scenario: 根据条码结果输出小票
    Given 条码扫描数据有
      | 条目         |
      | 12345678   |
      | 22345678-3 |
    And 有商品
      | 条码       | 名称    | 价格    | 单位 | 类型 |
      | 12345678 | pizza | 15.00 |    | 0  |
      | 22345678 | milk  | 12.30 | L  | 0  |
    Then 扫描条码结果为
    """
     {
      "data": [
        "****** SuperMarket receipt ******",
        "pizza: 1 x 15.00 --- 15.00",
        "milk: 3(L) x 12.30 --- 36.90",
        "---------------------------------",
        "total: 51.90(CNY)",
        "*********************************"
      ]
    }
    """
  Scenario: 找不到条码时提示对应条码不存在
    Given 条码扫描数据有
      | 条目       |
      | 88888888 |
    And 有商品
      | 条码       | 名称    | 价格    | 单位 | 类型 |
      | 12345678 | pizza | 15.00 |    | 0  |
      | 22345678 | milk  | 12.30 | L  | 0  |
    Then 扫描条码结果为
    """
     {
      "data": null,
      "error": "item doesn't exist: 88888888"
    }
    """

#  Scenario: 多个同样条码的汇总
#    Given 条码扫描数据有
#      | 条目       |
#      | 12345678 |
#      | 12345678 |
#      | 12345678 |
#    And 有商品
#      | 条码       | 名称    | 价格    | 单位 | 类型 |
#      | 12345678 | pizza | 15.00 |    | 0  |
#      | 22345678 | milk  | 12.30 | L  | 0  |
#    Then 扫描条码结果为
#    """
#     {
#      "data": [
#        "****** SuperMarket receipt ******",
#        "pizza: 3 x 15.00 --- 45.00",
#        "---------------------------------",
#        "total: 45.00(CNY)",
#        "*********************************"
#      ]
#      }
#    """
#
#  Scenario: barcodes dont exist in the product repository
#    When I 'POST' the api '/scan'
#    """
#
#    """
#    And the server response will match
#      """
#      {
#      "data": null,
#      "error": "item doesn't exist: 88888888"
#      }
#      """
Feature: Story 1, simple order
  Background following items exist in database
  (82202001, 'juice', '', 5, 0),
  (82202002, 'biscuit', '', 13, 0),
  (82203001, 'apple', 'KG', 10, 1),

  Scenario: order by barcodes detailed
    When I 'POST' the api '/scan' with
      """
      ["82202001", "82203001-3", "82202002"]
      """
    Then the server response will match
      """
      { data: [
        "****** SuperMarket receipt ******",
        "juice: 1 x 5.00 --- 5.00",
        "apple: 3(KG) x 10.00 --- 30.00",
        "biscuit: 1 x 13.00 --- 13.00",
        "---------------------------------",
        "total: 48.00(CNY)",
        "*********************************"
        ] }
      """

  Scenario: no exist barcode
    When I 'POST' the api '/scan' with
      """
      ["12345678"]
      """
    Then the server response will match
      """
      { error: "item doesn't exist: 12345678" }
      """


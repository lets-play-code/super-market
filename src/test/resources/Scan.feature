Feature: Scan products

  Scenario: Scan single product
    When I 'POST' the api '/scan' with
      """
      [
        "123456"
      ]
      """
    Then the server response will match
      """
      {
        "data": [
          "****** SuperMarket receipt ******",
          "pizza: 1 x 15.00 --- 15.00",
          "---------------------------------",
          "total: 15.00(CNY)",
          "*********************************"
        ]
      }
      """

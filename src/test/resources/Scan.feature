Feature: scan code

  Scenario: scan api demo
    When I 'POST' the api '/scan' with
      """
      [
        "12345678",
        "22345678-3"
      ]
      """
    Then the server response will match
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
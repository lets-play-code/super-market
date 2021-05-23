Feature: Scan products

  Scenario: Scan single product
    When I 'POST' the api '/scan' with
      """
      [
        "12345678"
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

  Scenario: Scan don't exist product
    When I 'POST' the api '/scan' with
     """
      [
        "88888888"
      ]
      """
    Then the server response will match
      """
      {
          "data": null,
          "error": "item doesn't exist: 88888888"
      }
      """

  Scenario: Scan two product
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

  Scenario: Scan product need validate duplicate
    When I 'POST' the api '/scan' with
      """
      [
        "12345678",
        "12345678",
        "12345678"
      ]
      """
    Then the server response will match
      """
      {
        "data": [
          "****** SuperMarket receipt ******",
          "pizza: 3 x 15.00 --- 45.00",
          "---------------------------------",
          "total: 45.00(CNY)",
          "*********************************"
        ]
      }
      """

  Scenario: Scan weight product need count
    When I 'POST' the api '/scan' with
      """
      [
        "22345678"
      ]
      """
    Then the server response will match
      """
      {
        "data": null,
        "error": "wrong quantity of 22345678"
      }
      """

  Scenario: Scan product has float
    When I 'POST' the api '/scan' with
      """
      [
        "22345678-1.5"
      ]
      """
    Then the server response will match
      """
      {
        "data": [
          "****** SuperMarket receipt ******",
          "milk: 1.5(L) x 12.30 --- 18.45",
          "---------------------------------",
          "total: 18.45(CNY)",
          "*********************************"
        ]
      }
      """

  Scenario: Scan product has float
    When I 'POST' the api '/scan' with
      """
      [
        "22345678-1.5"
      ]
      """
    Then the server response will match
      """
      {
        "data": [
          "****** SuperMarket receipt ******",
          "milk: 1.5(L) x 12.30 --- 18.45",
          "---------------------------------",
          "total: 18.45(CNY)",
          "*********************************"
        ]
      }
      """

  Scenario: Scan product has wrong float
    When I 'POST' the api '/scan' with
      """
      [
        "22345678-1.55"
      ]
      """
    Then the server response will match
      """
      {
        "data": null,
        "error": "wrong quantity of 22345678"
      }
      """

  Scenario: Scan product has wrong count
    When I 'POST' the api '/scan' with
      """
      [
        "22345678-0"
      ]
      """
    Then the server response will match
      """
      {
        "data": null,
        "error": "wrong quantity of 22345678"
      }
      """

  Scenario: Scan product has float problem
    When I 'POST' the api '/scan' with
      """
      [
        "33345678-1.5"
      ]
      """
    Then the server response will match
      """
      {
        "data": [
          "****** SuperMarket receipt ******",
          "coffee: 1.5(L) x 18.77 --- 28.15",
          "---------------------------------",
          "total: 28.15(CNY)",
          "*********************************"
        ]
      }
      """

#  Scenario: Scan invalid QRCode
#    When I 'POST' the api '/scan' with
#      """
#      [
#        "    _  _     _  _  _  _ ",
#        "|   _| _||_||_  _   ||_|",
#        ""
#      ]
#      """
#    Then the server response will match
#      """
#      {
#        "data": null,
#        "error": "can not recognize barcode:\n    _  _     _  _  _  _ \n|   _| _||_||_ |_   ||_|"
#      }
#      """
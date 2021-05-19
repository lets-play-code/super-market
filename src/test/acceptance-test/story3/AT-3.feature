Feature: Story 3, complex barcode
  Background:
  """
   _     _  _     _  _  _  _  _
  | ||   _| _||_||_ |_   ||_||_|
  |_||  |_  _|  | _||_|  ||_| _|

  """

  Scenario: order by complex barcodes
    When I 'POST' the api '/scan' with
      """
      [
        " _  _  _  _  _  _  _    ",
        "|_| _| _|| | _|| || ||  ",
        "|_||_ |_ |_||_ |_||_||  ",
        "",
        " _  _  _  _  _  _  _    ",
        "|_| _| _|| | _|| || ||  ",
        "|_||_ |_ |_| _||_||_||  ",
        "3.3",
        " _  _  _  _  _  _  _  _ ",
        "|_| _| _|| | _|| || | _|",
        "|_||_ |_ |_||_ |_||_||_ ",
        "4",
        " _  _  _  _  _  _  _    ",
        "|_| _| _|| | _|| || ||  ",
        "|_||_ |_ |_||_ |_||_||  ",
        ""
      ]
      """
    Then the server response will match
      """
      { data: [
        "****** SuperMarket receipt ******",
        "juice: 2 x 5.00 --- 10.00",
        "apple: 3.3(KG) x 10.00 --- 33.00",
        "biscuit: 4 x 13.00 --- 52.00",
        "---------------------------------",
        "total: 95.00(CNY)",
        "*********************************"
        ] }
      """

  Scenario: missing quantity for weighted type
    When I 'POST' the api '/scan' with
      """
      [
        " _  _  _  _  _  _  _    ",
        "|_| _| _|| | _|| || ||  ",
        "|_||_ |_ |_| _||_||_||  ",
        ""
      ]
      """
    Then the server response will match
      """
      { error: "wrong quantity of 82203001" }
      """

  Scenario: cannot recognize barcode
    When I 'POST' the api '/scan' with
      """
      [
        "    _  _  _  _  _  _    ",
        "|_| _| _|| | _|| || ||  ",
        "|_||_ |_ |_| _||_||_||  ",
        ""
      ]
      """
    Then the server response will match
      """
      { error: "can not recognize barcode:\
          _  _  _  _  _  _    \
      |_| _| _|| | _|| || ||  \
      |_||_ |_ |_| _||_||_||  \
      " }
      """


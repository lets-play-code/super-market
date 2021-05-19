Feature: Story 5, barcode check sum
  Background:
  82202001 -> 822020017
  82202002 -> 822020025
  82203001 -> 822030012
  82203002 -> 822030020
  following items exist in database
  ('82202001', 'juice', '', 5, 0),
  ('82202002', 'biscuit', '', 13, 0),
  ('82203001', 'apple', 'KG', 10, 1),
  ('82203002', 'rice', 'KG', 7.09, 1),
  ('82203003', 'orange', 'KG', 13.07, 1)

  and following promotions
  ('P001', 'Two-for-one', '82202001,82202002')
  ('P002', '95%', '82202002, 82203002')

  Scenario: order with promotions
    When I 'POST' the api '/scan' with
      """
      [
        " _  _  _  _  _  _  _     _ ",
        "|_| _| _|| | _|| || ||    |",
        "|_||_ |_ |_||_ |_||_||    |",
        "",
        " _  _  _  _  _  _  _  _  _ ",
        "|_| _| _|| | _|| || | _||_ ",
        "|_||_ |_ |_||_ |_||_||_  _|",
        "7",
        " _  _  _  _  _  _  _  _  _ ",
        "|_| _| _|| | _|| || | _|| |",
        "|_||_ |_ |_| _||_||_||_ |_|",
        "4.7",
        " _  _  _  _  _  _  _     _ ",
        "|_| _| _|| | _|| || ||    |",
        "|_||_ |_ |_||_ |_||_||    |",
        ""
      ]
      """
    Then the server response will match
      """
      { data: [
        "****** SuperMarket receipt ******",
        "juice: 2 x 5.00 --- 10.00",
        "biscuit: 7 x 13.00 --- 65.00",
        "rice: 4.7(KG) x 7.09 --- 31.65 (-1.66)",
        "---------------------------------",
        "Two-for-one:",
        "biscuit: 2",
        "---------------------------------",
        "total: 106.65(CNY)",
        "       -27.66(CNY)",
        "*********************************"
        ] }
      """

  Scenario: cannot recognize barcode, check sum error
    When I 'POST' the api '/scan' with
      """
      [
        " _  _  _  _  _  _  _       ",
        "|_| _| _|| | _|| || ||  |  ",
        "|_||_ |_ |_| _||_||_||  |  ",
        ""
      ]
      """
    Then the server response will match
      """
      { error: "can not recognize barcode:\
       _  _  _  _  _  _  _       \
      |_| _| _|| | _|| || ||  |  \
      |_||_ |_ |_| _||_||_||  |  \
      " }
      """

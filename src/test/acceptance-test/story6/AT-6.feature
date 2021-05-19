Feature: Story 6, barcode auto correct
  Scenario: auto correct error number
    When I 'POST' the api '/scan' with
      """
      [
        " _  _  _  _  _  _  _     _ ",
        "|_| _| _|| | _|| || ||    |",
        "|_||_ |_ |_ |_ |_||_||    |",
        ""
      ]
      """
    Then the server response will match
      """
      { data: [
        "****** SuperMarket receipt ******",
        "juice: 1 x 5.00 --- 10.00",
        "---------------------------------",
        "total: 10.00(CNY)",
        "*********************************"
        ] }
      """

  Scenario: cannot correct, more than 1 change
    When I 'POST' the api '/scan' with
      """
      [
        " _  _  _  _  _  _  _     _ ",
        "|_| _| _|| | _|| || ||    |",
        "|_||  |_ | ||_ |_||_||    |",
        ""
      ]
      """
    Then the server response will match
      """
      { error: "can not recognize barcode:\
       _  _  _  _  _  _  _     _ \
      |_| _| _|| | _|| || ||    |\
      |_||  |_ | ||_ |_||_||    |\
      " }
      """

  Scenario: valid number but not checksum, auto correct, possible code 822030810
    When I 'POST' the api '/scan' with
      """
      [
        " _  _  _  _  _  _  _     _ ",
        "|_| _| _|| | _|| || ||  | |",
        "|_||_ |_ |_| _||_||_||  |_|",
        ""
      ]
      """
    Then the server response will match
      """
      { error: "item doesn't exist: 82203081" }
      """

  Scenario: cannot recognize barcode, more than 1 possible correct: 622030019, 822830019
    When I 'POST' the api '/scan' with
      """
      [
        " _  _  _  _  _  _  _     _ ",
        "|_| _| _|| | _|| || ||  |_|",
        "|_||_ |_ |_| _||_||_||   _|",
        ""
      ]
      """
    Then the server response will match
      """
      { error: "can not recognize barcode:\
       _  _  _  _  _  _  _     _ \
      |_| _| _|| | _|| || ||  |_|\
      |_||_ |_ |_| _||_||_||   _|\
      " }
      """

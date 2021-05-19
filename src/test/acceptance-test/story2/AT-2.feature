Feature: Story 2, enhance simple order

  Background following items exist in database
  ('82202001', 'juice', '', 5, 0),
  ('82202002', 'biscuit', '', 13, 0),
  ('82202003', 'cola', '250ml', 4.2, 0),
  ('82203001', 'apple', 'KG', 10, 1),
  ('82203002', 'rice', 'KG', 7.09, 1),
  ('82203003', 'orange', 'KG', 13.07, 1),

  Scenario: order float weight and repeat items
    When I 'POST' the api '/scan' with
      """
      ["82203001-1", "82202001-2", "82203001-0.5", "82202001", "82202003-3"]
      """
    Then the server response will match
      """
      { data: [
        "****** SuperMarket receipt ******",
        "apple: 1.5(KG) x 10.00 --- 15.00",
        "juice: 3 x 5.00 --- 15.00",
        "cola: 3(250ml) x 4.20 --- 12.60",
        "---------------------------------",
        "total: 42.60(CNY)",
        "*********************************"
        ] }
      """

  Scenario: missing quantity for weighted type
    When I 'POST' the api '/scan' with
      """
      ["82203001"]
      """
    Then the server response will match
      """
      { error: "wrong quantity of 82203001" }
      """

  Scenario: wrong quantity for weighted type
    When I 'POST' the api '/scan' with
      """
      ["82203001-3.14159"]
      """
    Then the server response will match
      """
      { error: "wrong quantity of 82203001" }
      """

  Scenario: wrong quantity for weighted type - zero
    When I 'POST' the api '/scan' with
      """
      ["82203001-0"]
      """
    Then the server response will match
      """
      { error: "wrong quantity of 82203001" }
      """

  Scenario: wrong number for individual type
    When I 'POST' the api '/scan' with
      """
      ["82202001-1.2"]
      """
    Then the server response will match
      """
      { error: "wrong quantity of 82202001" }
      """

  Scenario: wrong number for individual type - zero
    When I 'POST' the api '/scan' with
      """
      ["82202001-0"]
      """
    Then the server response will match
      """
      { error: "wrong quantity of 82202001" }
      """

  Scenario: order float weight conner case
    When I 'POST' the api '/scan' with
      """
      ["82203002-1.5", "82203003-0.5"]
      """
    Then the server response will match
      """
      { data: [
        "****** SuperMarket receipt ******",
        "rice: 1.5(KG) x 7.09 --- 10.63",
        "orange: 0.5(KG) x 13.07 --- 6.53",
        "---------------------------------",
        "total: 17.16(CNY)",
        "*********************************"
        ] }
      """


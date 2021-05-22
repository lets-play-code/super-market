Feature: sanity check

  Scenario: ping
    When I 'GET' the api '/ping'
    Then the server response will match '{ data: "pong" }'

  Scenario: show sample items
    When I 'GET' the api '/item'
    And the server response will match
      """
      { data: [
        { barcode: "81102001", name: "juice", unit: "", price: 5 },
        { barcode: "81102002", name: "biscuit", unit: "", price: 13 },
        { barcode: "81103001", name: "apple", unit: "KG", price: 4 }
      ] }
      """

  Scenario: show sample error
    When I 'GET' the api '/dontcall'
    And the server response will match
      """
      { error: "It is a sample error" }
      """

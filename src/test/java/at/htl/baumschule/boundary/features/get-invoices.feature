Feature: InvoiceEndpoint: Get results of queries

  Scenario: Get best selling product
    Given url 'http://localhost:8081/api/plants/get-most-sold-plant'
    When method GET
    Then status 200
    Then match response.plant.name == 'Tanne'
    * print 'The most sold plant was ' + response.plant.name + '. It was sold ' + response.quantity + ' times.'

  Scenario: Get total revenue
    Given url 'http://localhost:8081/api/invoices/get-total-revenue'
    When method GET
    Then status 200
    Then match response == '2160.5'
    * print 'The total revenue was ' + response

  Scenario: Get plant with highest revenue
    Given url 'http://localhost:8081/api/plants/get-plant-with-highest-revenue'
    When method GET
    Then status 200
    Then match response == ["Tanne", 1125.0 ]
    * print 'The plant with the highest revenue was ' + response[0] + ' with a total revenue of ' + response[1]

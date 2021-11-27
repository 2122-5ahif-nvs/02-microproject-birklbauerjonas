Feature: CustomerEndpoint

  Background:
    * def customersJson = read('data/customers.json')

  Scenario: Add Customer
    Given url 'http://localhost:8081/api/customers/add-customers'
    And request customersJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all customers'
    * print response

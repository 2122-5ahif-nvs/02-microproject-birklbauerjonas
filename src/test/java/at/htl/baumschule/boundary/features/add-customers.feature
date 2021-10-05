Feature: CustomerEndpoint

  Background:
    * url apiBaseUrl
    * def customersJson = read('data/customers.json')

  Scenario: Add Customer
    Given path 'customers/add-customers'
    And request customersJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all customers'
    * print response
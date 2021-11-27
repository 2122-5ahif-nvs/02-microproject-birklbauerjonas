Feature: InvoiceEndpoint

  Background:
    * def invoicesJson = read('data/invoices.json')

  Scenario: Add invoices
    Given url 'http://localhost:8081/api/invoices/add-invoices'
    And request invoicesJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all invoices'
    * print response

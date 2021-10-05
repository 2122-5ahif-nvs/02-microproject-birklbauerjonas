Feature: InvoiceEndpoint

  Background:
    * url apiBaseUrl
    * def invoicesJson = read('data/invoices.json')

  Scenario: Add invoices
    Given path 'invoices/add-invoices'
    And request invoicesJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all invoices'
    * print response
Feature: Clean repositories

  Scenario: Clean customer repository
    Given url 'http://localhost:8081/api/customers/clear'

  Scenario: Clean location repository
    Given url 'http://localhost:8081/api/locations/clear'

  Scenario: Clean plant repository
    Given url 'http://localhost:8081/api/plants/clear'

  Scenario: Clean invoice repository
    Given url 'http://localhost:8081/api/invoices/clear'

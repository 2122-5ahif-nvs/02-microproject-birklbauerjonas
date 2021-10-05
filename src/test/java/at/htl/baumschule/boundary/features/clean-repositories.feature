Feature: Clean repositories

  Background:
    * url apiBaseUrl

  Scenario: Clean customer repository
    Given path 'customers/clear'

  Scenario: Clean location repository
    Given path 'locations/clear'

  Scenario: Clean plant repository
    Given path 'plants/clear'

  Scenario: Clean invoice repository
    Given path 'invoices/clear'
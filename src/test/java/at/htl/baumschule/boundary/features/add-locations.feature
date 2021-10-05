Feature: LocationEndpoint

  Background:
    * url apiBaseUrl
    * def locationJson = read('data/locations.json')

  Scenario: Add locations
    Given path 'locations/add-locations'
    And request locationJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all locations'
    * print response
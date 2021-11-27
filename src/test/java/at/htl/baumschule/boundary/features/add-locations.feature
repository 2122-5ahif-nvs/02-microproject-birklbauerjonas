Feature: LocationEndpoint

  Background:
    * def locationJson = read('data/locations.json')

  Scenario: Add locations
    Given url 'http://localhost:8081/api/locations/add-locations'
    And request locationJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all locations'
    * print response

Feature: PlantEndpoint

  Background:
    * url apiBaseUrl
    * def plantsJson = read('data/plants.json')

  Scenario: Add plants
    Given path 'plants/add-plants'
    And request plantsJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all plants'
    * print response
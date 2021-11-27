Feature: PlantEndpoint

  Background:
    * def plantsJson = read('data/plants.json')

  Scenario: Add plants
    Given url 'http://localhost:8081/api/plants/add-plants'
    And request plantsJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all plants'
    * print response

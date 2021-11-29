Feature: Clean repositories

  Scenario: Get bearer token
    Given url 'http://localhost:8081/auth/realms/quarkus/protocol/openid-connect/token'
    * header Authorization = 'Basic backend-service 8d2019d6-f9e2-46c0-a221-54b34fbf1789'
    * form field grant_type = password
    * form field username = admin
    * form field password = admin
    When method post
    Then status 200
    * def access_token = 'Bearer ' + response.access_token

  Scenario: Clean customer repository
    Given url 'http://localhost:8081/api/customers/clear'
    * header Authorization = access_token

  Scenario: Clean location repository
    Given url 'http://localhost:8081/api/locations/clear'
    * header Authorization = access_token

  Scenario: Clean plant repository
    Given url 'http://localhost:8081/api/plants/clear'
    * header Authorization = access_token

  Scenario: Clean invoice repository
    Given url 'http://localhost:8081/api/invoices/clear'
    * header Authorization = access_token

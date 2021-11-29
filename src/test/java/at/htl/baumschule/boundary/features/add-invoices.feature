Feature: InvoiceEndpoint

  Background:
    * def invoicesJson = read('data/invoices.json')

  Scenario: Get bearer token
    Given url 'http://localhost:8081/auth/realms/quarkus/protocol/openid-connect/token'
    * header Authorization = 'Basic backend-service 8d2019d6-f9e2-46c0-a221-54b34fbf1789'
    * form field grant_type = password
    * form field username = admin
    * form field password = admin
    When method post
    Then status 200
    * def access_token = 'Bearer ' + response.access_token

  Scenario: Add invoices
    Given url 'http://localhost:8081/api/invoices/add-invoices'
    * header Authorization = access_token
    And request invoicesJson
    When method POST
    Then status 200
    Then match response == 'Successfully added all invoices'
    * print response

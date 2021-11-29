Feature: InvoiceEndpoint: Get results of queries

  Scenario: Get bearer token
    Given url 'http://localhost:8081/auth/realms/quarkus/protocol/openid-connect/token'
    * header Authorization = 'Basic backend-service 8d2019d6-f9e2-46c0-a221-54b34fbf1789'
    * form field grant_type = password
    * form field username = admin
    * form field password = admin
    When method post
    Then status 200
    * def access_token = 'Bearer ' + response.access_token

  Scenario: Get best selling product
    Given url 'http://localhost:8081/api/plants/get-most-sold-plant'
    * header Authorization = access_token
    When method GET
    Then status 200
    Then match response.plant.name == 'Tanne'
    * print 'The most sold plant was ' + response.plant.name + '. It was sold ' + response.quantity + ' times.'

  Scenario: Get total revenue
    Given url 'http://localhost:8081/api/invoices/get-total-revenue'
    * header Authorization = access_token
    When method GET
    Then status 200
    Then match response == '2160.5'
    * print 'The total revenue was ' + response

  Scenario: Get plant with highest revenue
    Given url 'http://localhost:8081/api/plants/get-plant-with-highest-revenue'
    * header Authorization = access_token
    When method GET
    Then status 200
    Then match response == ["Tanne", 1125.0 ]
    * print 'The plant with the highest revenue was ' + response[0] + ' with a total revenue of ' + response[1]

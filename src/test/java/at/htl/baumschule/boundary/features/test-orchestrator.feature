Feature: Test orchestration feature

  Scenario: Run all of the tests in order
    * call read('clean-repositories.feature')
    * call read('add-customers.feature')
    * call read('add-locations.feature')
    * call read('add-plants.feature')
    * call read('add-invoices.feature')
    * call read('get-invoices.feature')
Feature: User Login

  Scenario: Login with previously registered user
    And user is registered via API
    When user logs in via UI
    Then login is successful
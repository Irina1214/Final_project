Feature: User Registration

  Scenario: Successful registration with unique email
    Given user is on main page
    When user registers with unique email via UI
    Then user is successfully registered

  Scenario: Attempt to register with existing email
    Given user is on main page
    And user is registered via API
    When user tries to register with existing email via UI
    Then registration error is displayed
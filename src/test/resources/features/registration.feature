Feature: User Registration

  Scenario: Successful registration with unique email
    When user registers with unique email via UI
    Then registration is successful

  Scenario: Attempt to register with existing email
    When user tries to register with existing email via UI
    Then registration error is displayed
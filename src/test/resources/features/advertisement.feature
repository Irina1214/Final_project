Feature: Advertisement Management

  Scenario: Successful creation of advertisement
    Given user is registered and logged in
    When he creates new advertisement in category "Auto"
    Then advertisement is created successfully

  Scenario: Successful editing of own advertisement
    Given user is registered and logged in
    And user has created advertisement
    When he edits his advertisement
    Then editing is successful

  Scenario: Successful deletion of own advertisement
    Given user is registered and logged in
    And user has created advertisement
    When he deletes his advertisement
    Then deletion is successful
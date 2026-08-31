Feature: Data-Driven Authentication Testing

  @Regression
  Scenario Outline: Verify error messages across multiple invalid credentials
    Given The user navigates to the login page
    When The user enters an username "<username>" and password "<password>"
    And Clicks on the submit button
    Then An error message confirming invalid credentials should be displayed

    Examples:

      | username     | password            |
      | testUser123  | incorrectPass       |
      | admin        | wrongPassword       |
      | guestUser    | randomAccessString  |

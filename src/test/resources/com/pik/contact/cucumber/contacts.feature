Feature: Contacts Page Search

  Scenario: Search by name

    Given User is on search page
    When User enters the name on search input
    Then filtered results appear to the page


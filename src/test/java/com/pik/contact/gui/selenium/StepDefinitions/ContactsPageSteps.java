package com.pik.contact.gui.selenium.StepDefinitions;

import com.pik.contact.gui.selenium.pageobjects.ContactsPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.pik.contact.gui.selenium.setup.SeleniumDriver.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactsPageSteps {
    @After
    public static void tearDown() {
        getDriver().close();
    }
    public ContactsPage contactsPage;
    @Given("User is on search page")
    public void userIsOnSearchPage() {
        contactsPage = new ContactsPage(8080).open();
    }

    @When("User enters the name on search input")
    public void userEntersTheNameOnSearchInput() throws InterruptedException {
        contactsPage.find("John");
    }

    @Then("filtered results appear to the page")
    public void filteredResultsAppearToThePage() throws InterruptedException {
        assertThat(contactsPage.firstContactTitle()).isEqualTo("John");
    }
}

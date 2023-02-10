package com.pik.contact.gui.selenium.StepDefinitions;

import com.pik.contact.Application;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static com.pik.contact.gui.selenium.setup.SeleniumDriver.getDriver;

@RunWith(Cucumber.class)
@CucumberOptions(features ="src/test/resources/com/pik/contact/cucumber", glue = {"StepDefinitions"})
public class TestRunner {

}

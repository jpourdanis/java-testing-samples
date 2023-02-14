package com.pik.contact.gui.selenium.test;

import com.pik.contact.Application;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactsPageTest {

    @Value("${local.server.port}")
    int port;

    @Test
    public void should_display_contact() throws Exception {
        // initialize the driver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:" + port);

        // find the WebElements and interact with them.
        WebElement filterTextBox = driver.findElement(By.id("filter"));
        filterTextBox.sendKeys("John");
        Thread.sleep(1000);
        WebElement firstContactTile = driver.findElement(By.cssSelector("table > tbody > tr:nth-child(1) > th > strong > input"));

        // do the assertion of the test
        assertThat(firstContactTile.getAttribute("value")).isEqualTo("John");

        // close the driver.
        driver.quit();
    }
}

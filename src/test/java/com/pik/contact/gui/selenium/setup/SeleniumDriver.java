package com.pik.contact.gui.selenium.setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.concurrent.TimeUnit;

public class SeleniumDriver {

    static private WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();    //can be replaced with HtmlUnitDriver for better performance
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        }
        return driver;
    }

}

package com.pik.contact.gui.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.pik.contact.gui.selenium.setup.SeleniumDriver.getDriver;

public class ContactsPage extends BasePage<ContactsPage> {
    private By filter = By.id("filter");

    private By firstContactTitle = By.cssSelector("table > tbody > tr:nth-child(1) > th > strong > input");

    private By firstContactJob = By.xpath("/html/body/div[2]/div[1]/form/table/tbody/tr[4]/td/input");

    public ContactsPage(int port) {
        super(port);
    }

    @Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.titleContains("Contact");
	}

	@Override
	public String getPageUrl() {
		return "/#/view";
	}

	public ContactsPage open() {
		return openPage();
	}

    public void find(String query) throws InterruptedException {
        Thread.sleep(1000);
        getDriver().findElement(filter).sendKeys(query);
    }

    public String firstContactTitle() throws InterruptedException {
        Thread.sleep(1000);
        return getDriver().findElement(firstContactTitle).getAttribute("value");
    }

    public String firstContactJob() throws InterruptedException {
        Thread.sleep(1000);
        return getDriver().findElement(firstContactJob).getAttribute("value");
    }
}

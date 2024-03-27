package org.company.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends Common {

	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(css = ".action__submit")
	protected WebElement submit;
	@FindBy(css = "[placeholder='Select Country']")
	protected WebElement country;
	@FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
	protected WebElement selectCountryValue;
	protected By results = By.cssSelector(".ta-results");

	public void selectCountry(String countryName) {
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();
		waitForElementVisibility(results);
		selectCountryValue.click();
	}
	
	public void submitOrder()
	{
		submit.click();
	}
}

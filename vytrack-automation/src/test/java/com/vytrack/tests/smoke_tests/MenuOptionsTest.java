package com.vytrack.tests.smoke_tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MenuOptionsTest {
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void login(String username, String password) {
		driver.get("http://qa2.vytrack.com/user/login");
		driver.findElement(By.id("prependedInput")).sendKeys(username);
		driver.findElement(By.id("prependedInput2")).sendKeys(password);
		driver.findElement(By.id("_submit")).click();
	}

	@Test(dataProvider = "getDriverLoginData", enabled = false) // enabled = false,
	public void MenuOptionsDriver(String username, String password) {
		login(username, password);
		waitSeconds(3);

		// Fleets/Cars
		driver.findElement(By.cssSelector("#main-menu > ul > li:nth-child(1) > a > span")).click();
		driver.findElement(By.cssSelector("a[href='entity/Extend_Entity_Carreservation']")).click();
		waitSeconds(5);
		String actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		String expected = "Cars";

		Assert.assertEquals(actual, expected);

		// Customers/Accounts
		driver.findElement(By.cssSelector("#main-menu > ul > li:nth-child(2) > a > span")).click();
		driver.findElement(By.cssSelector("[data-route='oro_account_index']")).click();
		waitSeconds(2);
		actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expected = "Accounts";

		Assert.assertEquals(actual, expected);

		// Customers/Contacts
		driver.findElement(By.cssSelector("#main-menu > ul > li:nth-child(2) > a > span")).click();
		driver.findElement(By.cssSelector("[data-route='oro_contact_index']")).click();
		waitSeconds(2);
		actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expected = "Contacts";

		Assert.assertEquals(actual, expected);

		// Activities/Calendar
		driver.findElement(By.linkText("Activities")).click();
		// driver.findElement(By.cssSelector("#main-menu > ul > li:nth-child(3) > a >
		// span")).click();
		driver.findElement(By.cssSelector("[data-route='oro_calendar_event_index']")).click();
		waitSeconds(2);
		actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expected = "Calendar Events";

		Assert.assertEquals(actual, expected);

	}

	@Test(dataProvider = "getStoreManagerLoginData") // enabled = false,
	public void MenuOptionsStoreManager(String username, String password) {
		login(username, password);
		waitSeconds(5);

		// Fleets/Cars
		driver.findElement(By.linkText("Fleet")).click();
		driver.findElement(By.cssSelector("a[href='entity/Extend_Entity_Carreservation']")).click();
		waitSeconds(5);
		String actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		String expected = "All Cars";
		// BUG All Cars vs Cars

		Assert.assertEquals(actual, expected);

		// Customers/Accounts
		driver.findElement(By.linkText("Customers")).click();
		driver.findElement(By.cssSelector("[data-route='oro_account_index']")).click();
		waitSeconds(2);
		actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expected = "Accounts";

		Assert.assertEquals(actual, expected);
		waitSeconds(2);
		// Customers/Contacts
		driver.findElement(By.linkText("Customers")).click();
		driver.findElement(By.cssSelector("[data-route='oro_contact_index']")).click();
		waitSeconds(2);
		actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expected = "All Contacts";

		Assert.assertEquals(actual, expected);
		// BUG All Cars vs Cars

		// Sales/Opportunities
		driver.findElement(By.linkText("Sales")).click();
		driver.findElement(By.cssSelector("[data-route='oro_sales_opportunity_index']")).click();
		waitSeconds(2);
		actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expected = "Open Opportunities";

		Assert.assertEquals(actual, expected);

		// Sales/Calls
		driver.findElement(By.linkText("Activities")).click();
		driver.findElement(By.cssSelector("[data-route='oro_call_index']")).click();
		waitSeconds(2);
		actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expected = "All Calls";

		Assert.assertEquals(actual, expected);

		// Activities/Calendar
		driver.findElement(By.linkText("Activities")).click();
		driver.findElement(By.cssSelector("[data-route='oro_calendar_event_index']")).click();
		waitSeconds(2);
		actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expected = "All Calendar Events";
		
		Assert.assertEquals(actual, expected);
		//BUG All Calendar Events vs Calendar Events
	}

	public void waitSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void scrollIntoView(WebElement element, WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@DataProvider
	public String[][] getLoginData() {
		String[][] data = new String[3][2];

		data[0][0] = "user197";
		data[0][1] = "UserUser123";

		data[1][0] = "storemanager237";
		data[1][1] = "UserUser123";

		data[2][0] = "salesmanager300";
		data[2][1] = "UserUser123";

		return data;
	}

	@DataProvider
	public String[][] getDriverLoginData() {
		String[][] data = new String[1][2];

		data[0][0] = "user197";
		data[0][1] = "UserUser123";

		return data;
	}
	
	@DataProvider
	public String[][] getSalesManagerLoginData() {
		String[][] data = new String[1][2];

		data[0][0] = "salesmanager300";
		data[0][1] = "UserUser123";

		return data;
	}
	
	@DataProvider
	public String[][] getStoreManagerLoginData() {
		String[][] data = new String[1][2];

		data[0][0] = "storemanager237";
		data[0][1] = "UserUser123";

		return data;
	}
}

package com.vytrack.tests.components.login_navigation;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PageAccess {
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

	@Test
	public void VehicleContractsTestStoreManager() {
		String[][] credentials;
		String username;
		String password;

		// StoreMananger
		credentials = getStoreManagerLoginData();
		username = credentials[0][0];
		password = credentials[0][1];

		login(username, password);
		waitSeconds(5);

		// Fleets/Cars
		driver.findElement(By.linkText("Fleet")).click();
		driver.findElement(By.cssSelector("a[href='entity/Extend_Entity_VehicleContract']")).click();
		waitSeconds(5);
		String actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		String expected = "All Vehicle Contract";
		Assert.assertEquals(actual, expected);	
	}
	
	@Test
	public void VehicleContractsTestSalesManager() {
		String[][] credentials;
		String username;
		String password;

		// StoreMananger
		credentials = getSalesManagerLoginData();
		username = credentials[0][0];
		password = credentials[0][1];

		login(username, password);
		waitSeconds(5);

		// Fleets/Cars
		driver.findElement(By.linkText("Fleet")).click();
		driver.findElement(By.cssSelector("a[href='/entity/Extend_Entity_VehicleContract']")).click();
		waitSeconds(5);
		String actual = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		String expected = "All Vehicle Contract";
		Assert.assertEquals(actual, expected);	
	}
	
	@Test
	public void VehicleContractsTestDriver() {
		String[][] credentials;
		String username;
		String password;

		// StoreMananger
		credentials = getSalesManagerLoginData();
		username = credentials[0][0];
		password = credentials[0][1];

		login(username, password);
		waitSeconds(5);

		// Fleets/Cars
		driver.findElement(By.linkText("Fleet")).click();
		driver.findElement(By.cssSelector("a[href='entity/Extend_Entity_VehicleContract']")).click();
		waitSeconds(5);
		
		String actualError = driver.findElement(By.cssSelector("[class ='message']")).getText();
		String expectedError = "You do not have permission to perform this action.";
		Assert.assertEquals(actualError, expectedError);	
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
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

	public void waitSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

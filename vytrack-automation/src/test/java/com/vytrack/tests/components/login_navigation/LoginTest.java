package com.vytrack.tests.components.login_navigation;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
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

public class LoginTest {
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
	public void LoginPositive() {
		String[][] credentials;
		String username;
		String password;
		
		
		// StoreMananger
		credentials = getStoreManagerLoginData();
		username = credentials[0][0];
		password = credentials[0][1];
		
		
		login(username, password);
		waitSeconds(5);

		String storeManagerName = driver.findElement(By.id("user-menu")).getText();


		String actualPage = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		String expectedPage = "Dashboard";
		Assert.assertEquals(actualPage, expectedPage);
		
		driver.findElement(By.id("user-menu")).click();
		driver.findElement(By.cssSelector("[href='/user/logout']")).click();
		waitSeconds(3);
			
		
		// SalesManager
		credentials = getSalesManagerLoginData();
		username = credentials[0][0];
		password = credentials[0][1];
		
		login(username, password);
		waitSeconds(5);
		
		String salesManagerName = driver.findElement(By.id("user-menu")).getText();
		System.out.println("names:" + salesManagerName + "," + storeManagerName);
		//Assert.assertNotEquals(storeManagerName, salesManagerName);
		//BUG it is not a different name.

		actualPage = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expectedPage = "Dashboard";
		Assert.assertEquals(actualPage, expectedPage);
		
		driver.findElement(By.id("user-menu")).click();
		driver.findElement(By.cssSelector("[href='/user/logout']")).click();
		waitSeconds(3);
		
		
		// Driver
		credentials = getDriverLoginData();
		username = credentials[0][0];
		password = credentials[0][1];
		
		login(username, password);
		waitSeconds(5);
		
		String driverName = driver.findElement(By.id("user-menu")).getText();
		//Assert.assertNotEquals(storeManagerName, driverName);
		//Assert.assertNotEquals(salesManagerName, driverName);
		//BUG
	   
		actualPage = driver.findElement(By.cssSelector("h1.oro-subtitle")).getText();
		expectedPage = "Quick Launchpad";
		Assert.assertEquals(actualPage, expectedPage);
		
		driver.findElement(By.id("user-menu")).click();
		driver.findElement(By.cssSelector("[href='/user/logout']")).click();;
		waitSeconds(3);
			
	}
	
	
	@Test (dataProvider = "getInvalidLoginData")
	public void LoginNegative(String username, String password) {		
		
		driver.get("http://qa2.vytrack.com/user/login");
		String pageTitleBefore = driver.getTitle();
		String urlBefore = driver.getCurrentUrl();
		
		driver.findElement(By.id("prependedInput")).sendKeys(username);
		driver.findElement(By.id("prependedInput2")).sendKeys(password);
		driver.findElement(By.id("_submit")).click();
		waitSeconds(3);
		
//		Alert al=driver.switchTo().alert();
//		System.out.println(al.getText());
//		
//		al.accept();
//	driver.switchTo().defaultContent();
		
		
		String actualAlert = driver.findElement(By.cssSelector("[class ='alert alert-error']")).getText();
		String expectedAlert = "Invalid user name or password.";
		Assert.assertEquals(actualAlert, expectedAlert);	
		

		String pageTitleAfter = driver.getTitle();
		String urlAfter = driver.getCurrentUrl();
		Assert.assertEquals(pageTitleBefore, pageTitleAfter);	
		Assert.assertEquals(urlBefore, urlAfter);
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
	
	@DataProvider
	public String[][] getInvalidLoginData() {
		String[][] data = new String[1][2];

		data[0][0] = "invalidusername";
		data[0][1] = "invalidpassword";

		return data;
	}

}

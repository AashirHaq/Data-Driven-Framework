package com.home.base;

import org.testng.annotations.BeforeSuite;

import com.home.utilities.ExcelReader;
import com.home.utilities.ExtentManager;
import com.home.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;

public class TestBase {

	/*
	 * WebDriver - done
	 * Properties - done
	 * Logs - log4j jar, .log, log4j.properties, Logger - done
	 * ExtentReports, ReportNG
	 * DB
	 * Excel
	 * Mail
	 * Jenkins
	 * 
	 */
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static String pathOfCurrentDir = System.getProperty("user.dir");	
	public static ExcelReader excel = new ExcelReader(pathOfCurrentDir + "\\src\\test\\resources\\excel\\testData.xlsx");
	public static WebDriverWait wait;
	public ExtentReports report = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	
  @BeforeSuite
  public void setUp() throws IOException {
	  
	  if(driver == null) {
		  
		  fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		  config.load(fis);
		  log.debug("Config file loaded");
		  fis = new FileInputStream(pathOfCurrentDir + "\\src\\test\\resources\\properties\\OR.properties");
		  OR.load(fis);
		  log.debug("OR file loaded");
		  
		  if(System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			  browser = System.getenv("browser");
		  }else {
			  browser = config.getProperty("browser");
		  }

		  config.setProperty("browser", browser);
		  
		  if(config.getProperty("browser").equals("firefox")) {
			  driver = new FirefoxDriver();
		  }else if(config.getProperty("browser").equals("chrome")) {
			  System.setProperty("webdriver.chrome.driver", pathOfCurrentDir + "\\src\\test\\resources\\executables\\chromedriver.exe");
			  driver = new ChromeDriver();
			  log.debug("Chrome launched");

		  }else if(config.getProperty("browser").equals("ie")) {
			  System.setProperty("webdriver.ie.driver", pathOfCurrentDir + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
			  driver = new InternetExplorerDriver();
		  }
		  
		  driver.get(config.getProperty("testsiteurl"));
		  log.debug("Navigated to " + config.getProperty("testsiteurl"));
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		  wait = new WebDriverWait(driver, 5L);
	  }
	  
  }
  
  
  public boolean isElementPresent(By by) {
	
	  try {
		  
		  driver.findElement(by);
		  return true;
		  
	} catch (NoSuchElementException e) {
		  return false;
	}
	  
  }
  
  public static void verifyEquals(String expected, String actual) throws IOException {
	  try {
		Assert.assertEquals(actual, expected);
	} catch (Throwable t) {

		TestUtil.captureScreenshot();
		// ReportNG
		Reporter.log("<br>Verification failure: " + t.getMessage());
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.screenshotName + "\"><img src=\"" + TestUtil.screenshotName + "\" width=200 height=200 ></a>");
		Reporter.log("<br>");
		
		//ExtentReports
		test.log(LogStatus.FAIL, "Verification failed with exception: " + t.getMessage());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
	
	}
  }
  
  
  
  public void click(String locator) {
	  
	  if(locator.endsWith("_CSS")) {
		  driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
	  }else if(locator.endsWith("_XPATH")) {
		  driver.findElement(By.xpath(OR.getProperty(locator))).click();
	  }else if(locator.endsWith("_ID")) {
		  driver.findElement(By.id(OR.getProperty(locator))).click();
	  }
	
	  test.log(LogStatus.INFO, "Clicking on: " + locator);
  }
  
  public void type(String locator, String value) {
	  
	  if(locator.endsWith("_CSS")) {
		  driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
	  }else if(locator.endsWith("_XPATH")) {
		  driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
	  }else if(locator.endsWith("_ID")) {
		  driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
	  }

	  test.log(LogStatus.INFO, "Typing in: " + locator + " entered value as " + value);
  }
  
  static WebElement dropdown;
  
  public void select(String locator, String value) {
	  
	  if(locator.endsWith("_CSS")) {
		  dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
	  }else if(locator.endsWith("_XPATH")) {
		  dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
	  }else if(locator.endsWith("_ID")) {
		  dropdown = driver.findElement(By.id(OR.getProperty(locator)));
	  }
	  
	  Select select = new Select(dropdown);
	  select.selectByVisibleText(value);
	  
	  test.log(LogStatus.INFO, "Selecting from dropdown: " + locator + " value as " + value);

  }
  

  @AfterSuite
  public void tearDown() {
	  
	  if(driver != null) {
		  driver.quit();
	  }
	  
	  log.debug("Test execution completed");
	  
  }

}

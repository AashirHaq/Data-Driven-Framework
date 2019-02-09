package com.home.base;

import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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

	
  @BeforeSuite
  public void setUp() throws IOException {
	  
	  if(driver == null) {
		  
		  fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		  config.load(fis);
		  log.debug("Config file loaded");
		  fis = new FileInputStream(pathOfCurrentDir + "\\src\\test\\resources\\properties\\OR.properties");
		  OR.load(fis);
		  log.debug("OR file loaded");

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

  @AfterSuite
  public void tearDown() {
	  
	  if(driver != null) {
		  driver.quit();
	  }
	  
	  log.debug("Test execution completed");
	  
  }

}

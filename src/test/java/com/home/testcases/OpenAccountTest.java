package com.home.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.home.base.TestBase;
import com.home.utilities.TestUtil;

public class OpenAccountTest extends TestBase {
	
  @Test(dataProviderClass = TestUtil.class, dataProvider="dp")
  public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {
	  
	  //runmode - Y
	  if (!TestUtil.isTestRunnable("OpenAccountTest", excel)) {
		  throw new SkipException("Skipping the test" + "openAccountTest" + "as the Runmode is NO");
	  }
	  
	  
	  String customer = data.get("customer");
	  String currency = data.get("currency");
	 
	  click("openAccount_CSS");
	  select("customer_CSS", customer);
	  select("currency_CSS", currency);  
	  click("process_CSS");
	  Thread.sleep(2000);
	  Alert alert = wait.until(ExpectedConditions.alertIsPresent());	  
	  alert.accept();

  
  }
  
 
}

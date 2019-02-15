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

public class AddCustomerTest extends TestBase {
	
  @Test(dataProviderClass = TestUtil.class, dataProvider="dp")
  public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
	  
	  String firstName = data.get("firstName");
	  String lastName = data.get("lastName");
	  String postCode = data.get("postCode");
	  String alertText = data.get("alertText");
	  String runMode = data.get("runMode");
	  
//	  //runmode - Y
	  if (!runMode.equalsIgnoreCase("Y")) {
		  throw new SkipException("Skipping the test case as the Runmode for given dataset is NO");
	  }
  
	  click("addCustBtn_CSS");
	  type("firstName_CSS", firstName);
	  type("lastName_XPATH", lastName);
	  type("postCode_CSS", postCode);
	  click("addBtn_CSS");
	  Thread.sleep(2000);
	  
	  Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	  Assert.assertTrue(alert.getText().contains(alertText));
	  
	  alert.accept();
	  

  
  }
 
}

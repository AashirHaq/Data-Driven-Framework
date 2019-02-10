package com.home.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.home.base.TestBase;

public class AddCustomerTest extends TestBase {
	
  @Test(dataProvider="getData")
  public void addCustomer(Hashtable<String, String> data) {
	  
	  String firstName = data.get("firstName");
	  String lastName = data.get("lastName");
	  String postCode = data.get("postCode");
	  String alertText = data.get("alertText"); 
  
	  driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
	  driver.findElement(By.cssSelector(OR.getProperty("firstName"))).sendKeys(firstName);
	  driver.findElement(By.cssSelector(OR.getProperty("lastName"))).sendKeys(lastName);
	  driver.findElement(By.cssSelector(OR.getProperty("postCode"))).sendKeys(postCode);
	  driver.findElement(By.cssSelector(OR.getProperty("addBtn"))).click();
	  
	  Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	  Assert.assertTrue(alert.getText().contains(alertText));
	  
	  alert.accept();
	  

  
  }
  
  
  @DataProvider
  public Object[][] getData(){
	  

	  String sheetName = "AddCustomerTest";
	  int rows = excel.getRowCount(sheetName);
	  int cols = excel.getColumnCount(sheetName);
	  	  
	  
	  Object[][] data = new Object[rows-1][1];
	  
	  Hashtable<String, String> table = null;
	  
	  for(int rowNum = 2; rowNum <= rows; rowNum++) {
		  
		  table = new Hashtable<String, String>();
		  
		  for(int colNum = 0; colNum < cols; colNum++) {
			  
			  // data[rowNum-2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			  
			  table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
			  data[rowNum -2][0] = table;
			  
		  }
		  
	  }
	  
	  return data;
  }
}

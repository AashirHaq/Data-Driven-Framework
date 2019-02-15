package com.home.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.home.base.TestBase;
import com.home.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Test;

public class CustomListeners extends TestBase implements  ITestListener{

	public void onTestStart(ITestResult result) {

		test = report.startTest(result.getName().toUpperCase());

	}

	public void onTestSuccess(ITestResult result) {

		test.log(LogStatus.PASS, result.getName().toUpperCase() + " PASS");
		report.endTest(test);
		report.flush();
	}

	public void onTestFailure(ITestResult result) {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.log(LogStatus.FAIL, result.getName().toUpperCase() + " Failed with exception: " + result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.screenshotName + "\">screenshot</a>");	
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.screenshotName + "\"><img src=\"" + TestUtil.screenshotName + "\" width=200 height=200 ></a>");
		
		report.endTest(test);
		report.flush();
	}

	public void onTestSkipped(ITestResult result) {

		test.log(LogStatus.SKIP, result.getName()  + " Skip the test as Run mode is NO");
		report.endTest(test);
		report.flush();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}

package com.Listeners;



//import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

//import com.SetUp.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import ExtentReport.ExtentReporterNG;

public class Listneners  implements ITestListener {
	ExtentTest test;
	ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();
	ExtentReports extent=ExtentReporterNG.getReportObject();
	public void onTestStart(ITestResult result) {
		test= extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed Execute by Nikhil Kharkhodia");
		
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().log(Status.FAIL, "Test Failed");
		
	}
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().log(Status.SKIP, "Test Skiped");
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("Rest Assured API Automation testCase execution start By"+ " ***** Nikhil Kharkhodia");
		
	}

	public void onFinish(ITestContext context) {
		extent.flush();
		
	}

}

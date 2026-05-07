package com.bankbot.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.bankbot.base.DriverFactory;
import com.bankbot.utils.ExtentManager;
import com.bankbot.utils.ExtentTestManager;
import com.bankbot.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getExtentReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().pass("Test passed");
        ExtentTestManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtil.capture(DriverFactory.getDriver(), result.getMethod().getMethodName());
        ExtentTestManager.getTest().fail(result.getThrowable());
        ExtentTestManager.getTest().fail("Failure screenshot",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        ExtentTestManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().skip(result.getThrowable());
        ExtentTestManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getExtentReports().flush();
    }
}

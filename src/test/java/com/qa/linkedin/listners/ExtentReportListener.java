package com.qa.linkedin.listners;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.linkedin.util.TestUtil;

public class ExtentReportListener implements ITestListener {

	private static ExtentReports extentReport = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public void onTestStart(ITestResult result) {

		ExtentTest test = extentReport
				.createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());
		extentTest.set(test);
		extentTest.get().getModel().setStartTime(getTime(result.getStartMillis()));

	}

	public void onTestSuccess(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName + " passed!");
		String logText = "<b>Test Method  " + methodName.toUpperCase() + " Successful</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, m);
		extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public void onTestFailure(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName + " failed!");

		// System.setProperty("org.uncommons.reportng.escape-output", "false");
		String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get().fail("<details><summary><b><font color=red>" + "Exception Occured:Click to see"
				+ "</font></b ></summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>\n");

		try {
			String path = TestUtil.captureScreenshot(result.getMethod().getMethodName());
			extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>",
					MediaEntityBuilder.createScreenCaptureFromPath(path).build());

		} catch (IOException e) {
			extentTest.get().fail("Test Failed, cannot attach screenshot");
			e.printStackTrace();
		}
		String logText = "<b>Test Method" + methodName + " Failed</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, m);

		extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public void onTestSkipped(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName + " skipped!");
		String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get().fail("<details><summary><b><font color=yellow>" + "Exception Occured:Click to see"
				+ "</font></b ></summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>\n");
		String logText = "<b>Test Case " + methodName + " Skipped </b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		extentTest.get().log(Status.SKIP, m);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));

	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {

		if (extentReport != null) {

			extentReport.flush();

		}

	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

}
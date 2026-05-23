package com.orangehrm.listeners;

import com.orangehrm.base.TestBase;
import com.orangehrm.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;

import java.io.IOException;

public class CustomerListener extends TestBase implements ITestListener {

    public void onFinish(ITestContext context){

    }

    public void onStart(ITestContext context){

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result){

    }
//    @Override
    public void onTestFailure(ITestResult result){
        System.setProperty("org.uncommons.reportng.escape-output","false");
        try {
            TestUtil.captureScreenshot();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        test.log(LogStatus.FAIL, result.getName().toUpperCase()+" Failed with exception : "+result.getThrowable());
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));


        Reporter.log("Capturing Screenshot");
        Reporter.log("<a target='_blank' href="+TestUtil.screenshotName+">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target='_blank' href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestSkipped(ITestResult result){
        test.log(LogStatus.SKIP, result.getMethod()+" Skipped the test");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestStart(ITestResult result){
        test = rep.startTest(result.getName().toUpperCase());

//        if(!TestUtil.isTestRunnable(result.getMethod().getMethodName(), excel)){
//            throw new SkipException("Skipping the test "+result.getName().toUpperCase()+" as the Run mode is NO");
//        }

    }

    public void onTestSuccess(ITestResult result){

        test.log(LogStatus.PASS, result.getName().toUpperCase()+" PASS");
        rep.endTest(test);
        rep.flush();


    }

}

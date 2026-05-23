package com.orangehrm.testcases;

import com.orangehrm.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

public class BankManagerLoginTest extends TestBase {
    @Test
    public void bankManagerLoginTest() throws IOException {

        log.debug("Inside Login Test!!!");
        appLogs.debug("Inside Login Test!!!");
        click("mgrLoginBtn_CSS");

        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Login not successful");
//        verifyEquals("abc", "xyz");
//
//        Assert.fail("Element is not found");
        log.debug("Login Successfully Executed !!!");
        appLogs.debug("Login Successfully Executed !!!");


    }
}

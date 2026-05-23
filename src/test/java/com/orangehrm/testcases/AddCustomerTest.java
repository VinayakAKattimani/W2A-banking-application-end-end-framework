package com.orangehrm.testcases;

import com.orangehrm.base.TestBase;
import com.orangehrm.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AddCustomerTest extends TestBase {


    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void addCustomerTest(String firstName, String lastName, String postCode, String alerttext) {


        click("addCustBtn_CSS");
        type("firstName_CSS", firstName);
        type("lastName_CSS", lastName);
        type("postCode_CSS", postCode);
        click("submitAddCust_CSS");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(alerttext));
        alert.accept();

    }
//
//    @Test
//    public void failingTest() throws IOException {
//        verifyEquals("abc", "xyz");
//
//        Assert.fail("Element is not found");
//    }


}



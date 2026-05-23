package com.orangehrm.testcases;

import com.orangehrm.base.TestBase;
import com.orangehrm.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class OpenAccountTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void openAccountTest(String customer, String currency, String alertText) {

        click("openaccount_CSS");
        select("selectCust_CSS", customer);
        select("selectCurrency_CSS", currency);
        click("addAcc_CSS");

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(alertText));
        alert.accept();

    }

}

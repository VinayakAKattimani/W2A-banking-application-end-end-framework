package com.orangehrm.base;

import com.orangehrm.testcases.BankManagerLoginTest;
import com.orangehrm.utilities.ExcelReader;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.w3c.dom.html.HTMLSelectElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {


    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static Properties reportng = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger(TestBase.class);
    public static Logger appLogs =
            Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel;
    public static WebDriverWait wait;
    public static Select select;
    public static ExtentReports rep = ExtentManager.getInstance();
    public static ExtentTest test;

    static {
        try {
            excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\Excel\\testdata.xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeSuite
    public void setUp() {
        if (driver == null) {

            try {
                fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                config.load(fis);
                log.debug("Config file Loaded !!!");
                appLogs.debug("Config file Loaded !!!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                OR.load(fis);
                log.debug("OR file Loaded !!!");
                appLogs.debug("OR file Loaded !!!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (config.getProperty("browser").equals("chrome")){
                driver = new ChromeDriver();
                log.debug("Chrome Launched!!!");
                appLogs.debug("Chrome Launched!!!");
            } else if (config.getProperty("browser").equals("firefox")) {
                driver = new FirefoxDriver();
            } else if (config.getProperty("browser").equals("edge")) {
                driver = new EdgeDriver();
            }

            driver.get(config.getProperty("testsiteurl"));
            log.debug("Navigated to "+config.getProperty("testsiteurl")+ " !!!");
            appLogs.debug("Navigated to Orange HRM web !!!");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//            navigateToManagerPage();
        }

    }

    public boolean isElementPresent(By by) {
        try{
            driver.findElement(by);
            return true;
        }catch (NoSuchElementException s){
            return false;
        }
    }

    public void click(String locator){

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }
        test.log(LogStatus.INFO, "Clicking on : "+locator);
    }

    public void type(String locator, String value) {
        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }
        test.log(LogStatus.INFO, "Typing in  : "+locator+" entered value as " + value);
    }

    static WebElement dropdown;

    public void select(String locator, String value) {
        if (locator.endsWith("_CSS")) {
            dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        } else if (locator.endsWith("_XPATH")) {
            dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
        } else if (locator.endsWith("_ID")) {
            dropdown = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
        test.log(LogStatus.INFO, "Selecting from dropdown : "+locator+" value as " + value);
    }



    public static void verifyEquals(String expected, String actual) throws IOException {
        try{
            Assert.assertEquals(actual, expected);
        }catch (Throwable t) {
            TestUtil.captureScreenshot();

          //ReportNG
            Reporter.log("<br>"+"Verification failure : "+t.getMessage()+"<br>");
            Reporter.log("<a target='_blank' href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
            Reporter.log("<br>");
            Reporter.log("<br>");
            //Extent Report
            test.log(LogStatus.FAIL," Verification failed with exception: "+t.getMessage());
            test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

        }
    }

    @AfterSuite
    public void tearDown() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
        log.debug("Test Execution Completed !!!");
        appLogs.debug("Test Execution Completed !!!");

    }
}

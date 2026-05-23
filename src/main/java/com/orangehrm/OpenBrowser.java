package com.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenBrowser {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.manage().window().maximize();


        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement username = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("username")));

        username.sendKeys("Admin");

        driver.findElement(By.name("password"))
                .sendKeys("admin123");

        driver.findElement(By.xpath("//button[@type='submit']"))
                .click();


    }
}

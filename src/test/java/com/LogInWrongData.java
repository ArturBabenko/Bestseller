package com;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.ForgotPassword;
import pageObjects.LandingPage;
import pageObjects.LoginPage;
import resources.Skeleton;

import java.io.IOException;


public class LogInWrongData extends Skeleton {
    public WebDriver driver;
    //public static Logger log = LogManager.getLogger(Skeleton.class.getName());

    @Test(dataProvider = "getData")
    public void LogInWrongData(String email, String password) throws IOException {

        driver = seleniumDriver();
        driver.manage().window().maximize();
        driver.get(properties.getProperty("url"));
        driver.findElement(By.xpath("//button[@class='cookie-overlay__close js-cookie-overlay__close']")).click();
        LandingPage lp = new LandingPage(driver);
        LoginPage loginPage = lp.login();
        loginPage.emailInput().sendKeys(email);
        loginPage.passwordInput().sendKeys(password);
        loginPage.rememberBox().click();
        loginPage.loginButton().click();
       // Log.info("Button clicked");
        Assert.assertEquals(loginPage.loginError(), "Niestety, to nie pasuje do " +
                "naszych rekordów. Sprawdź pisownię i spróbuj ponownie.");
        //Log.info("Error message should displays");

       ForgotPassword fp = loginPage.forgotPassword();
       fp.emailInput().sendKeys("abc@cda.com");
       fp.sendButton().click();

    }

    @DataProvider

    public Object[][] getData() {
    Object[][] data = new Object[2][2];
    data[0][0] = "bestsellerselenium@mailinator.com";
    data[0][1] = "123456-";
    data[1][0] = "adminbestsellerselenium@mailinator.com";
    data[1][1] = "123456-";

    return data;

    }

    @AfterTest
    public void closeWindow() {
        driver.close();
    }
}

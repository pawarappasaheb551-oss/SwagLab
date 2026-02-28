package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import page.LoginPage;

public class LoginTest extends BaseTest {

    // ✅ Positive Login Test
    @Test(priority = 1)
    public void validLoginTest() {

        LoginPage lp = new LoginPage(driver);
        lp.login("standard_user", "secret_sauce");

        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("inventory"));
    }

    // ❌ Invalid Password
    @Test(priority = 2)
    public void invalidPasswordTest() {

        LoginPage lp = new LoginPage(driver);
        lp.login("standard_user", "wrong123");

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Username and password do not match"));
    }

    // ❌ Invalid Username
    @Test(priority = 3)
    public void invalidUsernameTest() {

        LoginPage lp = new LoginPage(driver);
        lp.login("wrong_user", "secret_sauce");

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Username and password do not match"));
    }

    // ❌ Blank Username
    @Test(priority = 4)
    public void blankUsernameTest() {

        LoginPage lp = new LoginPage(driver);
        lp.login("", "secret_sauce");

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Username is required"));
    }

    // ❌ Blank Password
    @Test(priority = 5)
    public void blankPasswordTest() {

        LoginPage lp = new LoginPage(driver);
        lp.login("standard_user", "");

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Password is required"));
    }
}
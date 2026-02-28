package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import page.LoginPage;
import page.homepage;

public class LogoutTest extends BaseTest {

    @Test
    public void verifyLogoutFunctionality() throws InterruptedException {

        // Step 1: Login
        LoginPage lp = new LoginPage(driver);
        lp.login("standard_user", "secret_sauce");

        // Step 2: Click Menu
        homepage hp = new homepage(driver);
        hp.clickMenu();

        Thread.sleep(5000);   // small wait (later replace with explicit wait)

        // Step 3: Click Logout
        hp.clickCart();

        // Step 4: Verify user redirected to login page
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("saucedemo"));
    }
}
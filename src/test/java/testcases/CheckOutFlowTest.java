package testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import page.CheckoutPage;
import page.LoginPage;
import page.ProductPage;
import utilities.DriverFactory;

public class CheckOutFlowTest extends BaseTest {

    // Common method: Login + Add to cart + Open checkout
    public void goToCheckoutPage() {
        LoginPage login = new LoginPage(DriverFactory.driver);
        login.login("standard_user", "secret_sauce");

        ProductPage product = new ProductPage(DriverFactory.driver);
        product.addFirstProduct();
        product.openCart();

        DriverFactory.driver.findElement(By.id("checkout")).click();
    }

    // =============================
    // ✅ POSITIVE CHECKOUT
    // =============================
    @Test(priority = 1)
    public void checkoutPositiveTest() {
        goToCheckoutPage();

        CheckoutPage checkout = new CheckoutPage(DriverFactory.driver);
        checkout.fillDetails("Appasaheb", "Pawar", "411033");
        checkout.finishOrder();

        String msg = checkout.getSuccessMessage();
        Assert.assertEquals(msg, "Thank you for your order!");
        System.out.println("✅ Positive Checkout Passed");
    }

    // =============================
    // ❌ NEGATIVE - First Name Blank
    // =============================
    @Test(priority = 2)
    public void checkoutNegative_FirstNameBlank() 
    {
        goToCheckoutPage();

        CheckOutFlowTest checkout = new CheckOutFlowTest();
        checkout.fillDetails("", "Pawar", "411033");

        String error = DriverFactory.driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assert.assertTrue(error.contains("First Name is required"));
        System.out.println("❌ First Name Validation Passed");
    }

    // =============================
    // ❌ NEGATIVE - Last Name Blank
    // =============================
    @Test(priority = 3)
    public void checkoutNegative_LastNameBlank() {
        goToCheckoutPage();

        CheckoutPage checkout = new CheckoutPage(DriverFactory.driver);
        checkout.fillDetails("Appasaheb", "", "411033");

        String error = DriverFactory.driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assert.assertTrue(error.contains("Last Name is required"));
        System.out.println("❌ Last Name Validation Passed");
    }

    // =============================
    // ❌ NEGATIVE - Postal Code Blank
    // =============================
    @Test(priority = 4)
    public void checkoutNegative_PostalCodeBlank() {
        goToCheckoutPage();

        CheckoutPage checkout = new CheckoutPage(DriverFactory.driver);
        checkout.fillDetails("Appasaheb", "Pawar", "");

        String error = DriverFactory.driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assert.assertTrue(error.contains("Postal Code is required"));
        System.out.println("❌ Postal Code Validation Passed");
    }

    // =============================
    // 🔒 LOGOUT FLOW
    // =============================
    @Test(priority = 5)
    public void logoutAfterCheckoutTest() {
        // Login + add product
        LoginPage login = new LoginPage(DriverFactory.driver);
        login.login("standard_user", "secret_sauce");

        ProductPage product = new ProductPage(DriverFactory.driver);
        product.addFirstProduct();

        // Logout
        product.logout();

        // Verify redirected to login page
        Assert.assertTrue(DriverFactory.driver.getCurrentUrl().contains("saucedemo"));
        System.out.println("🔒 Logout Flow Passed");
    }
}
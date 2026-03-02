package testcases;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import page.CheckoutPage;

import java.time.Duration;

public class Manullyfillfieldcheckout extends CheckoutPage {

    WebDriver driver;

    public Manullyfillfieldcheckout() {
        super(null);
    }

    @BeforeMethod
    public void setup() {

        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");

        super.driver = driver;

        // ===== LOGIN =====
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // ===== ADD PRODUCT =====
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // ===== GO TO CART =====
        driver.findElement(By.className("shopping_cart_link")).click();

        // ===== CLICK CHECKOUT =====
        driver.findElement(By.id("checkout")).click();
    }

    // =========================
    // ✅ POSITIVE TEST
    // =========================

    @Test
    public void validCheckout() throws InterruptedException {

        fillDetails("John", "Doe", "411001");
        Thread.sleep(1500);

        finishOrder();
        Thread.sleep(1500);

        Assert.assertTrue(getSuccessMessage().contains("THANK YOU"));

        Thread.sleep(2000);
    }

    // =========================
    // ❌ NEGATIVE TEST - Empty First Name
    // =========================

    @Test
    public void emptyFirstName() throws InterruptedException {

        fillDetails("", "Doe", "411001");
        Thread.sleep(1500);

        validateError();
        Thread.sleep(2000);
    }

    // =========================
    // ❌ NEGATIVE TEST - Invalid Postal
    // =========================

    @Test
    public void invalidPostalCode() throws InterruptedException {

        fillDetails("John", "Doe", "ABCDE");
        Thread.sleep(1500);

        validateError();
        Thread.sleep(2000);
    }

    // =========================
    // COMMON ERROR VALIDATION
    // =========================

    public void validateError() {
        WebElement error =
                driver.findElement(By.cssSelector("h3[data-test='error']"));
        Assert.assertTrue(error.isDisplayed(), "Error message not displayed!");
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);  // So automation visibly disel
        driver.quit();
    }
}
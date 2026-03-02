package testcases;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import page.CheckoutPage;

import java.time.Duration;

public class CheckoutPageTest extends CheckoutPage {

    WebDriver driver;

    public CheckoutPageTest() {
        super(null);
    }

    @BeforeClass
    public void setup() {

        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/checkout-step-one.html");

        super.driver = driver;
    }

    // =========================
    // ✅ POSITIVE TESTS
    // =========================

    @Test(priority = 1)
    public void validCheckout1() {
        fillDetails("John", "Doe", "12345");
        finishOrder();
        Assert.assertTrue(getSuccessMessage().contains("THANK YOU"));
        driver.navigate().refresh();
    }

    @Test(priority = 2)
    public void validCheckoutWithSpaces() {
        fillDetails(" John ", " Doe ", " 411001 ");
        finishOrder();
        Assert.assertTrue(getSuccessMessage().contains("THANK YOU"));
        driver.navigate().refresh();
    }

    @Test(priority = 3)
    public void validSingleCharacter() {
        fillDetails("A", "B", "1");
        finishOrder();
        Assert.assertTrue(getSuccessMessage().contains("THANK YOU"));
        driver.navigate().refresh();
    }

    @Test(priority = 4)
    public void validLongInput() {

        String longStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";
        fillDetails(longStr, longStr, "1234567890");

        try {
            finishOrder();
            Assert.assertTrue(getSuccessMessage().contains("THANK YOU"));
        } catch (Exception e) {
            validateError();
        }

        driver.navigate().refresh();
    }

    // =========================
    // ❌ REQUIRED FIELD TESTS
    // =========================

    @Test(priority = 5)
    public void emptyFirstName() {
        fillDetails("", "Doe", "12345");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 6)
    public void emptyLastName() {
        fillDetails("John", "", "12345");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 7)
    public void emptyPostalCode() {
        fillDetails("John", "Doe", "");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 8)
    public void allFieldsEmpty() {
        fillDetails("", "", "");
        validateError();
        driver.navigate().refresh();
    }

    // =========================
    // ❌ INVALID INPUT TESTS
    // =========================

    @Test(priority = 9)
    public void numericInName() {
        fillDetails("1234", "Doe", "12345");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 10)
    public void specialCharactersInName() {
        fillDetails("!@#$%", "^&*()", "12345");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 11)
    public void scriptInjectionTest() {
        fillDetails("<script>", "Doe", "12345");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 12)
    public void sqlInjectionTest() {
        fillDetails("' OR 1=1 --", "Doe", "12345");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 13)
    public void alphabetsInPostal() {
        fillDetails("John", "Doe", "ABCDE");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 14)
    public void specialCharsInPostal() {
        fillDetails("John", "Doe", "12@#45");
        validateError();
        driver.navigate().refresh();
    }

    // =========================
    // ❌ EDGE & BEHAVIOR TESTS
    // =========================

    @Test(priority = 15)
    public void onlySpaces() {
        fillDetails("   ", "   ", "   ");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 16)
    public void emojiInput() {
        fillDetails("😀", "😀", "12345");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 17)
    public void unicodeInput() {
        fillDetails("नाम", "पाटील", "411001");
        validateError();
        driver.navigate().refresh();
    }

    @Test(priority = 18)
    public void doubleClickContinue() {
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("continue")).click();

        try {
            finishOrder();
            Assert.assertTrue(getSuccessMessage().contains("THANK YOU"));
        } catch (Exception e) {
            validateError();
        }

        driver.navigate().refresh();
    }

    @Test(priority = 19)
    public void refreshAfterEnteringData() {

        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.navigate().refresh();

        String value =
                driver.findElement(By.id("first-name")).getAttribute("value");

        Assert.assertTrue(value.isEmpty());
    }

    // =========================
    // COMMON ERROR VALIDATION
    // =========================

    public void validateError() {
        try {
            WebElement error =
                    driver.findElement(By.cssSelector("h3[data-test='error']"));
            Assert.assertTrue(error.isDisplayed());
        } catch (Exception e) {
            Assert.fail("Expected error but none found!");
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
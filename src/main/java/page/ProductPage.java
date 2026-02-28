package page;

import org.openqa.selenium.*;
import java.util.List;

public class ProductPage {

    WebDriver driver;

    By products = By.className("inventory_item");
    By firstAddToCart = By.xpath("(//button[text()='Add to cart'])[1]");
    By cartIcon = By.className("shopping_cart_link");
    By menuBtn = By.id("react-burger-menu-btn");
    By logoutBtn = By.id("logout_sidebar_link");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getProductCount() {
        List<WebElement> list = driver.findElements(products);
        return list.size();
    }

    public void addFirstProduct() {
        driver.findElement(firstAddToCart).click();
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }

    public void logout() {
        driver.findElement(menuBtn).click();
        driver.findElement(logoutBtn).click();
    }
}

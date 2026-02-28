package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class homepage{

    WebDriver driver;

    By addToCart = By.id("add-to-cart-sauce-labs-backpack");
    By cartIcon = By.className("shopping_cart_link");

    public homepage(WebDriver driver) 
    {
        this.driver = driver;
    }

    public void addProductToCart() {
        driver.findElement(addToCart).click();
    }

    public void clickCart() {
        driver.findElement(cartIcon).click();
    }

	public void clickMenu() {
		// TODO Auto-generated method stub
		
	}
}

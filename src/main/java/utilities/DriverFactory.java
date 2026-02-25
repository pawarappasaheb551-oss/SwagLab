package utilities;
import org.openqa.selenium.WebDriver;
public class DriverFactory 
{
	
	
	ThreadLocal<WebDriver> driver = new ThreadLocal<>();
}

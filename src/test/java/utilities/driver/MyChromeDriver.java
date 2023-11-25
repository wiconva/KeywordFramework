package utilities.driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utilities.tools.Keys;

public class MyChromeDriver {
    WebDriver webDriver;
    public MyChromeDriver(boolean headless){
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless == true)chromeOptions.addArguments("--headless");
        System.setProperty(Keys.CHROME_DRIVER_PROPERTIE, Keys.USER_DIR+Keys.LOCATION_CHROME_DRIVER);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
    }

    public WebDriver getWebDriver (){
        return this.webDriver;
    }
}

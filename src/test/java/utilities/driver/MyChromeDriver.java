package utilities.driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utilities.keys.AppKeys;

public class MyChromeDriver {
    WebDriver webDriver;
    public MyChromeDriver(boolean headless){
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless == true)chromeOptions.addArguments("--headless");
        System.setProperty(AppKeys.CHROME_DRIVER_PROPERTIE, AppKeys.USER_DIR+ AppKeys.LOCATION_CHROME_DRIVER);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
    }

    public WebDriver getWebDriver (){
        return this.webDriver;
    }
}

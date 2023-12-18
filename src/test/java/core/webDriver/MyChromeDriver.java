package core.webDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import core.keys.AppKeys;


public class MyChromeDriver {
    WebDriver webDriver;
    public final static String CHROME_DRIVER_PROPERTIE = "webdriver.chrome.driver";
    public MyChromeDriver(boolean headless){
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless == true)chromeOptions.addArguments("--headless");
        System.setProperty(this.CHROME_DRIVER_PROPERTIE, AppKeys.USER_DIR+ AppKeys.LOCATION_CHROME_DRIVER);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
    }

    public WebDriver getWebDriver (){
        return this.webDriver;
    }
}

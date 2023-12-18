package core.webDriver;

import core.keys.AppKeys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class MyFireFoxDriver {
    WebDriver webDriver;
    public final static String FIREFOX_DRIVER_PROPERTIE = "webdriver.Firefox.driver";
    public MyFireFoxDriver(boolean headless){
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (headless == true)firefoxOptions.addArguments("--headless");
        System.setProperty(this.FIREFOX_DRIVER_PROPERTIE, AppKeys.USER_DIR+ AppKeys.LOCATION_FIREFOX_DRIVER);
        webDriver = new FirefoxDriver(firefoxOptions);
        webDriver.manage().window().maximize();
    }

    public WebDriver getWebDriver (){
        return this.webDriver;
    }
}

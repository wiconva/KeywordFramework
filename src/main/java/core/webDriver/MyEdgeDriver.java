package core.webDriver;

import core.keys.AppKeys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


public class MyEdgeDriver {
    WebDriver webDriver;
    public final static String EDGE_DRIVER_PROPERTIE = "webdriver.Edge.driver";
    public final static String LOCATION_EDGE_DRIVER = "/src/test/resources/webdriver/msedgedriver.exe";
    public MyEdgeDriver(boolean headless){
        EdgeOptions edgeOptions = new EdgeOptions();
        if (headless == true)edgeOptions.addArguments("--headless");
        System.setProperty(this.EDGE_DRIVER_PROPERTIE, AppKeys.USER_DIR+ this.LOCATION_EDGE_DRIVER);
        webDriver = new EdgeDriver(edgeOptions);
        webDriver.manage().window().maximize();
    }

    public WebDriver getWebDriver (){
        return this.webDriver;
    }
}

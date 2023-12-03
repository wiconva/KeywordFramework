package utilities.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.driver.MyChromeDriver;
import utilities.tools.LoggerT;

public class WebActions {
    private final String MSG_OPENING_BROWSER = "Opening the brower";
    private final String MSG_BROWSER_OPEN = "The browser is Open in: ";
    private final String MSG_INPUT_TEXT = "The text was insert in the input";
    private final String MSG_INPUT_TEXT_BEGIN = "Begin insert Text in the Web Element gived";
    private final String MSG_FIND_ELEMENT ="Searching the Web Element in the Web page";
    private final String MSG_OPEN_WD = "";
    private final String MSG_CLOSE_WD ="";
    public final String CHROME_DRIVER="chrome";
    private WebDriver webDriver;

    public WebActions (String driverName, boolean headless){
        switch (driverName){
            case CHROME_DRIVER:
                this.webDriver = new MyChromeDriver(headless).getWebDriver();
                break;
        }
    }

    public void openBrowser (String objectWebStep[], String []inputStep, String [] outputSteps ){
        LoggerT.WriteInConsole(this.MSG_OPENING_BROWSER, LoggerT.NORMAL_LEVEL);
        String url = inputStep[0];
        this.webDriver.get(url);
        LoggerT.WriteInConsole(this.MSG_BROWSER_OPEN+ url, LoggerT.NORMAL_LEVEL);
    }
    public void inputText (String objectWebStep[], String []inputStep, String [] outputSteps ){
        LoggerT.WriteInConsole(this.MSG_INPUT_TEXT_BEGIN, LoggerT.NORMAL_LEVEL);
        String text = inputStep[0];
        WebElement inputText = this.findWebElement(objectWebStep);
        inputText.sendKeys(text);
        LoggerT.WriteInConsole(this.MSG_INPUT_TEXT, LoggerT.NORMAL_LEVEL);
    }
    private WebElement findWebElement (String [] objectWebStep){
        LoggerT.WriteInConsole(this.MSG_FIND_ELEMENT, LoggerT.NORMAL_LEVEL);
        String locatorMethod = objectWebStep[0].toLowerCase();
        String locator = objectWebStep[1];
        switch (locatorMethod){
            case "id":
                return webDriver.findElement(By.id(locator));
            case "xpath":
                return webDriver.findElement(By.xpath(locator));
            default:
                return null;
        }
    }

    public void closeBrowser(){
        LoggerT.WriteInConsole(this.MSG_OPEN_WD, LoggerT.NORMAL_LEVEL);
        this.webDriver.quit();
        LoggerT.WriteInConsole(this.MSG_CLOSE_WD, LoggerT.NORMAL_LEVEL);
    }
}

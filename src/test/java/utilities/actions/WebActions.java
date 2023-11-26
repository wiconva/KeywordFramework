package utilities.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.driver.MyChromeDriver;
import utilities.tools.Logger;

public class WebActions {
    private final String MSG_OPENING_BROWSER = "Opening the brower";
    private final String MSG_BROWSER_OPEN = "The browser is Open in: ";
    private final String MSG_INPUT_TEXT = "The text was input";
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
        Logger.WriteInConsole(this.MSG_OPENING_BROWSER, Logger.NORMAL_LEVEL);
        String url = inputStep[0];
        this.webDriver.get(url);
        Logger.WriteInConsole(this.MSG_BROWSER_OPEN+ url, Logger.NORMAL_LEVEL);
    }
    public void inputText (String objectWebStep[], String []inputStep, String [] outputSteps ){
        String text = inputStep[0];
        WebElement inputText = this.findWebElement(objectWebStep);
        inputText.sendKeys(text);
        Logger.WriteInConsole(this.MSG_INPUT_TEXT, Logger.NORMAL_LEVEL);
    }
    private WebElement findWebElement (String [] objectWebStep){
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
        this.webDriver.quit();
    }
}

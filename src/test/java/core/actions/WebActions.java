package core.actions;

import core.controller.TestController;
import core.webDriver.MyEdgeDriver;
import core.webDriver.MyFireFoxDriver;
import org.openqa.selenium.*;
import core.webDriver.MyChromeDriver;
import core.tools.TLogger;

import java.util.Hashtable;

public class WebActions {
    public final String CHROME_DRIVER="chrome";
    public final String FIREFOX_DRIVER="firefox";
    public final String EDGE_DRIVER="edge";
    private WebDriver webDriver;

    public WebActions (String driverName, boolean headless){
        driverName = driverName.trim().toLowerCase();
        TLogger.WriteInConsole("Starting web driver ["+driverName+"]",TLogger.NORMAL_LEVEL);
        switch (driverName){
            case CHROME_DRIVER:
                this.webDriver = new MyChromeDriver(headless).getWebDriver();
                break;
            case FIREFOX_DRIVER:
                this.webDriver = new MyFireFoxDriver(headless).getWebDriver();
                break;
            case EDGE_DRIVER:
                this.webDriver = new MyEdgeDriver(headless).getWebDriver();
                break;
            default:
                TLogger.WriteInConsole("The web driver:["+driverName+"] is not supported",TLogger.ERROR_LEVEL );
                TestController.validateTest(TLogger.ERROR_LEVEL);
                break;
        }
        TLogger.WriteInConsole("Driver Started",TLogger.NORMAL_LEVEL);
    }

    public void browserGet(String objectWebStep[], String []inputStep, String [] outputSteps ){
        try{
            TLogger.WriteInConsole("Opening the brower", TLogger.NORMAL_LEVEL);
            String url = inputStep[0];
            this.webDriver.get(url);
            TLogger.WriteInConsole("The browser is Open in: "+ url, TLogger.NORMAL_LEVEL);
        }catch(Exception e){
            TLogger.WriteInConsole("The URL resource is not disponible, check the URL",TLogger.ERROR_LEVEL);
            TLogger.WriteInConsole(e.getMessage(), TLogger.ERROR_LEVEL);
            TestController.validateTest(TLogger.ERROR_LEVEL);
        }
    }

    public void inputText (String objectWebStep[], String []inputStep, String [] outputSteps ){
        try{
            TLogger.WriteInConsole("Begin insert Text in the Web Element gived", TLogger.NORMAL_LEVEL);
            String text = inputStep[0];
            WebElement inputText = this.findWebElement(objectWebStep);
            inputText.sendKeys(text);
            TLogger.WriteInConsole("The text was insert in the input", TLogger.NORMAL_LEVEL);
        }catch (Exception e){
            TLogger.WriteInConsole("Not was possible insert text in web element, check that Web Element is enable for action",TLogger.ERROR_LEVEL);
            TLogger.WriteInConsole(e.getMessage(), TLogger.ERROR_LEVEL);
            TestController.validateTest(TLogger.ERROR_LEVEL);
        }

    }

    public void getText(String[] currentWebObjectStep, String[] currentInputStep, String[] currentOutputStep, Hashtable<String, String> outputsList) {
        try{
            TLogger.WriteInConsole("Getting the text from web element",TLogger.NORMAL_LEVEL);
            String textValue ="";
            WebElement we = findWebElement(currentWebObjectStep);
            textValue = we.getText().trim();
            outputsList.put(currentOutputStep[0],textValue);
            TLogger.WriteInConsole("The text value finded is: ["+textValue+"]",TLogger.NORMAL_LEVEL);
        }catch (Exception e){
            TLogger.WriteInConsole("Not was possible get the text, check the attribute of Webelement", TLogger.ERROR_LEVEL);
            TLogger.WriteInConsole(e.getMessage(), TLogger.ERROR_LEVEL);
            TestController.validateTest(TLogger.ERROR_LEVEL);
        }
    }

    private WebElement findWebElement (String [] objectWebStep) {
        try {
            TLogger.WriteInConsole("Searching the Web Element in the Web page", TLogger.NORMAL_LEVEL);
            String locatorMethod = objectWebStep[0].toLowerCase();
            String locator = objectWebStep[1];
            switch (locatorMethod) {
                case "id":
                    return webDriver.findElement(By.id(locator));
                case "xpath":
                    return webDriver.findElement(By.xpath(locator));
                default:
                    return null;
            }
        } catch (Exception e) {
            TLogger.WriteInConsole("The web element is not in the DOM, check tne xpath is ok, or if loaded the web element ", TLogger.ERROR_LEVEL);
            TLogger.WriteInConsole(e.getMessage(), TLogger.ERROR_LEVEL);
            TestController.validateTest(TLogger.ERROR_LEVEL);
            return null;
        }
    }


    public void closeBrowser(){
        this.webDriver.quit();
    }
}

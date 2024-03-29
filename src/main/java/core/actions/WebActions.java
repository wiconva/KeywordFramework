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
        TLogger.trackTest("Starting web driver ["+driverName+"]",TLogger.NORMAL_LEVEL);
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
                TLogger.trackTest("The web driver:["+driverName+"] is not supported",TLogger.ERROR_LEVEL );
                TestController.validateTest(TLogger.ERROR_LEVEL);
                break;
        }
        TLogger.trackTest("Driver Started",TLogger.NORMAL_LEVEL);
    }

    public boolean browserGet(String objectWebStep[], String []inputStep, String [] outputSteps ){
        try{
            TLogger.trackTest("Opening the brower", TLogger.NORMAL_LEVEL);
            String url = inputStep[0];
            this.webDriver.get(url);
            TLogger.trackTest("The browser is Open in: "+ url, TLogger.NORMAL_LEVEL);
            return true;
        }catch(Exception e){
            TLogger.trackTest("The URL resource is not disponible, check the URL",TLogger.ERROR_LEVEL);
            TLogger.trackTest(e.getMessage(), TLogger.ERROR_LEVEL);
            return false;
        }
    }

    public boolean inputText (String objectWebStep[], String []inputStep, String [] outputSteps ){
        try{
            TLogger.trackTest("Begin insert Text in the Web Element gived", TLogger.NORMAL_LEVEL);
            String text = inputStep[0];
            WebElement inputText = this.findWebElement(objectWebStep);
            inputText.sendKeys(text);
            TLogger.trackTest("The text was insert in the input", TLogger.NORMAL_LEVEL);
            return true;
        }catch (Exception e){
            TLogger.trackTest("Not was possible insert text in web element, check that Web Element is enable for action",TLogger.ERROR_LEVEL);
            TLogger.trackTest(e.getMessage(), TLogger.ERROR_LEVEL);
            return false;
        }

    }

    public boolean getText(String[] currentWebObjectStep, String[] currentInputStep, String[] currentOutputStep, Hashtable<String, String> outputsList) {
        try{
            TLogger.trackTest("Getting the text from web element",TLogger.NORMAL_LEVEL);
            String textValue ="";
            WebElement we = findWebElement(currentWebObjectStep);
            textValue = we.getText().trim();
            outputsList.put(currentOutputStep[0],textValue);
            TLogger.trackTest("The text value finded is: ["+textValue+"]",TLogger.NORMAL_LEVEL);
            return true;
        }catch (Exception e){
            TLogger.trackTest("Not was possible get the text, check the attribute of Webelement", TLogger.ERROR_LEVEL);
            TLogger.trackTest(e.getMessage(), TLogger.ERROR_LEVEL);
            return false;
        }

    }

    public boolean ClickOn (String[] currentWebObjectStep, String[] currentInputStep, String[] currentOutputStep, Hashtable<String, String> outputsList) {
        try{
            TLogger.trackTest("Clicking on the web element",TLogger.NORMAL_LEVEL);
            WebElement we = findWebElement(currentWebObjectStep);
            we.click();
            TLogger.trackTest("Click done on the element",TLogger.NORMAL_LEVEL);
            return true;
        }catch (Exception e){
            TLogger.trackTest("Was not possible Click on the Web element", TLogger.ERROR_LEVEL);
            TLogger.trackTest(e.getMessage(), TLogger.ERROR_LEVEL);
            return false;
        }

    }

    private WebElement findWebElement (String [] objectWebStep) {
        try {
            TLogger.trackTest("Searching the Web Element in the Web page", TLogger.NORMAL_LEVEL);
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
            TLogger.trackTest("The web element is not in the DOM, check tne xpath is ok, or if loaded the web element ", TLogger.ERROR_LEVEL);
            TLogger.trackTest(e.getMessage(), TLogger.ERROR_LEVEL);
            return null;
        }
    }



    public void closeBrowser(){
        this.webDriver.quit();
    }
}

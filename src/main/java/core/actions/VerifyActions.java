package core.actions;

import core.controller.TestController;
import core.tools.TLogger;

public class VerifyActions {

    public  static void verify(String [] currentWebObjectStep, String []currentInputStep, String []currentOutputStep){
       switch (currentInputStep[0].toLowerCase()){
           case "textequal":
               textEqual(currentInputStep[1],currentInputStep[2]);
               break;
           default:
               TLogger.trackTest("The specific "+currentInputStep[0]+" verify action does not exist",TLogger.ERROR_LEVEL);
               TestController.validateTest(TLogger.ERROR_LEVEL);
               break;
       }
    }

    private static void textEqual (String value, String expectedValue){
        TLogger.trackTest("Verify text equal Action",TLogger.NORMAL_LEVEL);
        if(value.equals(expectedValue)){
            TLogger.trackTest("The words are equals [value:"+value+" - Expected Value:"+expectedValue+"]",TLogger.NORMAL_LEVEL);
        }else{
            TLogger.trackTest("The words are not equals [value:"+value+" - Expected Value:"+expectedValue+"]",TLogger.ERROR_LEVEL);
            TestController.validateTest(TLogger.ERROR_LEVEL);
        }
    }
}

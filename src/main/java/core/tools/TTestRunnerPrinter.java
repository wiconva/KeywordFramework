package core.tools;

import core.controller.TestController;
import core.testFile.TestFile;
import core.keys.AppKeys;

public class TTestRunnerPrinter {

    public static void printFooterTest(){
        TLogger.trackTest("Finish all Steps",TLogger.STEP_LEVEL);
        TLogger.trackTest("============================================== Test finished sucessfull ==================================================", TLogger.HEADER_TEXT_LEVEL);
    }

    public static void printStep (int step, TestFile currentTestFile, String testName){
        int excelRow = step+1;
        String keywordStep = currentTestFile.getKeyword(step);
        String [] webObjectStep = currentTestFile.getWebObjectInput(step);
        String msgRow1 = "Executing step ["+step+"] and Excel row ["+excelRow+"]";
        String msgRow2 = "** Action: "+keywordStep;
        String msgRow3 = "** WebObject: "+currentTestFile.getSteps()[step][AppKeys.OBJECT_ROW_NUMBER]+ " Method= "+ ((webObjectStep !=null)?webObjectStep[AppKeys.METHOD_LOCATOR_ARRAY_NUMBER]:null)+
                "\tLocator= "+((webObjectStep !=null)?webObjectStep[AppKeys.OBJET_LOCATOR_ARRAY_NUMBER]:null);
        String msgRow4 = "** Input: " + currentTestFile.getSteps()[step][AppKeys.INPUT_ROW_NUMBER];
        String msgRow5 = "** Output: " + currentTestFile.getSteps()[step][AppKeys.OUTPUT_ROW_NUMBER];
        String msgRow6 = "** Executing step: ";

        TLogger.trackTest(msgRow1, TLogger.STEP_LEVEL);
        TestController.validateTest(msgRow1, TLogger.STEP_LEVEL);
        TLogger.trackTest(msgRow2, TLogger.HEADER_TEXT_LEVEL);
        TestController.validateTest(msgRow2, TLogger.WARNING_LEVEL);
        TLogger.trackTest( msgRow3, TLogger.NORMAL_LEVEL);
        TestController.validateTest( msgRow3, TLogger.NORMAL_LEVEL);
        TLogger.trackTest(msgRow4, TLogger.NORMAL_LEVEL);
        TestController.validateTest( msgRow4, TLogger.NORMAL_LEVEL);
        TLogger.trackTest(msgRow5, TLogger.NORMAL_LEVEL);
        TestController.validateTest( msgRow5, TLogger.NORMAL_LEVEL);
        TLogger.trackTest(msgRow6, TLogger.WARNING_LEVEL);
        TestController.validateTest( msgRow6, TLogger.NORMAL_LEVEL);
    }

    public static void printHeaderLogTest (TestFile currentTestFile){
        TLogger.trackTest("==================================================== TEST STARTED =============================================================", TLogger.HEADER_TEXT_LEVEL);
        TLogger.trackTest("                 AUTOMATION TEST STUDIO", TLogger.WARNING_LEVEL);
        TLogger.trackTest(" Test location: ["+ currentTestFile.getUrl()+"]\n", TLogger.WARNING_LEVEL);
    }
}

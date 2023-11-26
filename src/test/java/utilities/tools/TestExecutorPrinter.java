package utilities.tools;

import utilities.framework.TestExecutor;
import utilities.framework.TestFile;
import utilities.keys.AppKeys;

public class TestExecutorPrinter {

    public static void printStep (int step, TestFile currentTestFile, String testName){
        int excelRow = step+1;
        String keywordStep = currentTestFile.getKeyword(step);
        String [] webObjectStep = currentTestFile.getWebObjectInput(step);
        String msgRow1 = "Executing "+testName+" row ["+excelRow+"]";
        String msgRow2 = "** Action: "+keywordStep;
        String msgRow3 = "** WebObject: "+currentTestFile.getSteps()[step][AppKeys.OBJECT_ROW_NUMBER]+ " Method= "+ ((webObjectStep !=null)?webObjectStep[AppKeys.METHOD_LOCATOR_ARRAY_NUMBER]:null)+
                "\tLocator= "+((webObjectStep !=null)?webObjectStep[AppKeys.OBJET_LOCATOR_ARRAY_NUMBER]:null);
        String msgRow4 = "** Input: " + currentTestFile.getSteps()[step][AppKeys.INPUT_ROW_NUMBER];
        String msgRow5 = "** Output: " + currentTestFile.getSteps()[step][AppKeys.OUTPUT_ROW_NUMBER];
        String msgRow6 = "** Execution step: ";

        Logger.WriteInConsole(msgRow1, Logger.STEP_LEVEL);
        TestExecutor.validateTest(msgRow1, Logger.STEP_LEVEL);
        Logger.WriteInConsole(msgRow2, Logger.WARNING_LEVEL);
        TestExecutor.validateTest(msgRow2, Logger.WARNING_LEVEL);
        Logger.WriteInConsole( msgRow3, Logger.NORMAL_LEVEL);
        TestExecutor.validateTest( msgRow3, Logger.NORMAL_LEVEL);
        Logger.WriteInConsole(msgRow4, Logger.NORMAL_LEVEL);
        TestExecutor.validateTest( msgRow4, Logger.NORMAL_LEVEL);
        Logger.WriteInConsole(msgRow5, Logger.NORMAL_LEVEL);
        TestExecutor.validateTest( msgRow5, Logger.NORMAL_LEVEL);
        Logger.WriteInConsole(msgRow6, Logger.NORMAL_LEVEL);
        TestExecutor.validateTest( msgRow6, Logger.NORMAL_LEVEL);
    }

    public static void printHeaderLogTest (TestFile currentTestFile){
        Logger.WriteInConsole("==========================================================================================================================", Logger.HEADER_TEXT_LEVEL);
        Logger.WriteInConsole("                 AUTOMATION TEST STUDIO", Logger.WARNING_LEVEL);
        Logger.WriteInConsole(" Test location: ["+ currentTestFile.getUrl()+"]", Logger.WARNING_LEVEL);
        Logger.WriteInConsole("==========================================================================================================================", Logger.HEADER_TEXT_LEVEL);
    }
}

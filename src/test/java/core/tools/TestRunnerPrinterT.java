package core.tools;

import core.framework.TestRunner;
import core.framework.TestFile;
import core.keys.AppKeys;

public class TestRunnerPrinterT {

    public static void printStep (int step, TestFile currentTestFile, String testName){
        int excelRow = step+1;
        String keywordStep = currentTestFile.getKeyword(step);
        String [] webObjectStep = currentTestFile.getWebObjectInput(step);
        String msgRow1 = "Executing "+testName+" Excel row ["+excelRow+"]";
        String msgRow2 = "** Action: "+keywordStep;
        String msgRow3 = "** WebObject: "+currentTestFile.getSteps()[step][AppKeys.OBJECT_ROW_NUMBER]+ " Method= "+ ((webObjectStep !=null)?webObjectStep[AppKeys.METHOD_LOCATOR_ARRAY_NUMBER]:null)+
                "\tLocator= "+((webObjectStep !=null)?webObjectStep[AppKeys.OBJET_LOCATOR_ARRAY_NUMBER]:null);
        String msgRow4 = "** Input: " + currentTestFile.getSteps()[step][AppKeys.INPUT_ROW_NUMBER];
        String msgRow5 = "** Output: " + currentTestFile.getSteps()[step][AppKeys.OUTPUT_ROW_NUMBER];
        String msgRow6 = "** Execution step: ";

        LoggerT.WriteInConsole(msgRow1, LoggerT.STEP_LEVEL);
        TestRunner.validateTest(msgRow1, LoggerT.STEP_LEVEL);
        LoggerT.WriteInConsole(msgRow2, LoggerT.WARNING_LEVEL);
        TestRunner.validateTest(msgRow2, LoggerT.WARNING_LEVEL);
        LoggerT.WriteInConsole( msgRow3, LoggerT.NORMAL_LEVEL);
        TestRunner.validateTest( msgRow3, LoggerT.NORMAL_LEVEL);
        LoggerT.WriteInConsole(msgRow4, LoggerT.NORMAL_LEVEL);
        TestRunner.validateTest( msgRow4, LoggerT.NORMAL_LEVEL);
        LoggerT.WriteInConsole(msgRow5, LoggerT.NORMAL_LEVEL);
        TestRunner.validateTest( msgRow5, LoggerT.NORMAL_LEVEL);
        LoggerT.WriteInConsole(msgRow6, LoggerT.NORMAL_LEVEL);
        TestRunner.validateTest( msgRow6, LoggerT.NORMAL_LEVEL);
    }

    public static void printHeaderLogTest (TestFile currentTestFile){
        LoggerT.WriteInConsole("==========================================================================================================================", LoggerT.HEADER_TEXT_LEVEL);
        LoggerT.WriteInConsole("                 AUTOMATION TEST STUDIO", LoggerT.WARNING_LEVEL);
        LoggerT.WriteInConsole(" Test location: ["+ currentTestFile.getUrl()+"]", LoggerT.WARNING_LEVEL);
        LoggerT.WriteInConsole("==========================================================================================================================", LoggerT.HEADER_TEXT_LEVEL);
    }
}

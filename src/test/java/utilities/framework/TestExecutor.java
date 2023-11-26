package utilities.framework;

import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.actions.UtilActions;
import utilities.actions.WebActions;
import utilities.keys.AppKeys;
import utilities.tools.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class TestExecutor {
    private List <TestFile> testFileList = new ArrayList<>();

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser","runheadless","profile"})
    public synchronized void beforeMehtods(ITestResult r, String browser, boolean runeadless, String profile){
        /*If this methods fail, all more test case will be Ignored*/
        TestFile currentTestFile;
        String testFileName = r.getMethod().getMethodName()+ AppKeys.TEST_FILE_EXTENSION;
        currentTestFile = this.loadTest( testFileName, profile.trim(), true);
        currentTestFile.setWebActions(new WebActions(browser,runeadless));
        this.testFileList.add(currentTestFile);
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void afterMethods (ITestResult resultTest){
        String testNameResultWithExtension;
        testNameResultWithExtension = resultTest.getMethod().getMethodName()+ AppKeys.TEST_FILE_EXTENSION;
        TestFile deleteTest = null;
        for(TestFile currentTest: testFileList){
            if(currentTest.getName().equals(testNameResultWithExtension)){
                deleteTest = currentTest;
                currentTest.getWebActions().closeBrowser();
            }
        }
        if(deleteTest!=null)testFileList.remove(deleteTest);
    }

    public void runTest (String testName){
        String currentKeywordStep;
        String [] currentWebObjectStep;
        String [] currentInputStep;
        String [] currentOutputStep;
        TestFile currentTestFile=this.getCurrentTest(testName);

        try {
            if (currentTestFile.isFather())this.printHeaderLogTest(currentTestFile);
            for (int currentStep = 1; currentStep < currentTestFile.getSteps().length; currentStep++) {
                currentTestFile.tranformInput(currentStep);
                this.printStep(currentStep,currentTestFile, testName);
                currentKeywordStep =   currentTestFile.getKeyword(currentStep);
                currentWebObjectStep = currentTestFile.getWebObjectInput(currentStep);
                currentInputStep =     currentTestFile.getInput(currentStep);
                currentOutputStep =    currentTestFile.getOuputs(currentStep);

                switch (currentKeywordStep) {
                    case "callto":
                        this.executeCallTo(currentTestFile, currentStep);
                        break;
                    case "sleep":
                        UtilActions.sleep(currentInputStep);
                        break;
                    case "openbrowser":
                        currentTestFile.getWebActions().openBrowser(currentWebObjectStep, currentInputStep, currentOutputStep);
                        break;
                    case "inputtext":
                        currentTestFile.getWebActions().inputText(currentWebObjectStep, currentInputStep, currentOutputStep);
                        break;
                    default:
                        TestValidator.assertAndWriteInConsole("The specific Keyword \""+currentKeywordStep+"\" does not exists, check the test file and verify the action cell", TestValidator.ERROR_LEVEL);
                }
            }
        }catch (Exception e){
            TestValidator.assertAndWriteInConsole("Sometings go wrong reading the test file. Check function name or runTes(ParamName) in Class test.",TestValidator.WARNING_LEVEL);
            TestValidator.assertAndWriteInConsole(e.toString(), TestValidator.ERROR_LEVEL);
        }

    }
    
    private void executeCallTo (TestFile fatherTestFile, int fatherTestStepNum){
        String [] currentFatherInputStep;
        currentFatherInputStep = fatherTestFile.getInput(fatherTestStepNum);
        String callToFileName = currentFatherInputStep[0];
        TestValidator.assertAndWriteInConsole("*******************************************  Running CallTo "+"\""+callToFileName+"\"    *******************************************",TestValidator.WARNING_LEVEL);
        TestFile callToTestFile = this.loadTest (callToFileName+AppKeys.TEST_FILE_EXTENSION,fatherTestFile.getTestProfileName(),false);
        callToTestFile.setWebActions(fatherTestFile.getWebActions());
        this.testFileList.add(callToTestFile);
        this.runTest(callToFileName);
        this.testFileList.remove(callToTestFile);
        TestValidator.assertAndWriteInConsole("******************************************* Ending CallTo "+"\""+callToFileName+"\"    *******************************************",TestValidator.WARNING_LEVEL);
    }

    private TestFile getCurrentTest (String testName){
        TestFile currentTest=null;
        for(TestFile t:testFileList){
            if(t.getName().equals(testName+ AppKeys.TEST_FILE_EXTENSION))currentTest = t;
        }
        return currentTest;
    }

    private TestFile loadTest (String testName, String profile, boolean isFather){
        TestFile currentTestFile = new TestFile(testName, profile, isFather);
        currentTestFile.setUrl(new LocationPathFinder(AppKeys.TEST_REPOSITORY_PATH,currentTestFile.getName()).getPath());
        ExcelReader.readFileTest(currentTestFile);
        return currentTestFile;
    }

    private void printStep (int step, TestFile currentTestFile, String testName){
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

        TestValidator.assertAndWriteInConsole(msgRow1, TestValidator.STEP_LEVEL);
        TestValidator.assertAndWriteInConsole(msgRow2, TestValidator.WARNING_LEVEL);
        TestValidator.assertAndWriteInConsole( msgRow3,TestValidator.NORMAL_LEVEL);
        TestValidator.assertAndWriteInConsole(msgRow4, TestValidator.NORMAL_LEVEL);
        TestValidator.assertAndWriteInConsole(msgRow5, TestValidator.NORMAL_LEVEL);
        TestValidator.assertAndWriteInConsole(msgRow6, TestValidator.NORMAL_LEVEL);
    }

    private void printHeaderLogTest (TestFile currentTestFile){
        TestValidator.assertAndWriteInConsole("====================================================================",TestValidator.HEADER_TEXT_LEVEL);
        TestValidator.assertAndWriteInConsole("                 AUTOMATION TEST STUDIO", TestValidator.WARNING_LEVEL);
        TestValidator.assertAndWriteInConsole(" Test location: ["+ currentTestFile.getUrl()+"]", TestValidator.WARNING_LEVEL);
        TestValidator.assertAndWriteInConsole("====================================================================",TestValidator.HEADER_TEXT_LEVEL);
    }
}

package utilities.framework;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.actions.UtilActions;
import utilities.actions.WebActions;
import utilities.keys.AppKeys;
import utilities.tools.*;

import java.util.ArrayList;
import java.util.List;


public class TestRunner {
    private List <TestFile> testFileList = new ArrayList<>();

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser","runheadless","profile"})
    public synchronized void beforeMehtods(ITestResult r, String browser, boolean runeadless, String profile) throws NoSuchFieldException {
        /*If this methods fail, all more test case will be Ignored*/
        TestFile currentTestFile;
        String testFileName = r.getMethod().getMethodName()+ AppKeys.TEST_FILE_EXTENSION;
        currentTestFile = new TestFile ( testFileName, profile.trim(), true, r.getTestClass().getName());
        currentTestFile.setWebActions(new WebActions(browser,runeadless));
        this.testFileList.add(currentTestFile);
    }

    public void runTest (String testName){
        String currentKeywordStep;
        String [] currentWebObjectStep;
        String [] currentInputStep;
        String [] currentOutputStep;
        TestFile currentTestFile=this.getTestFromList(testName);
        try {
            if (currentTestFile.isFather()) TestRunnerPrinterT.printHeaderLogTest(currentTestFile);
            for (int currentStep = 1; currentStep < currentTestFile.getSteps().length; currentStep++) {
                currentTestFile.tranformInput(currentStep);
                TestRunnerPrinterT.printStep(currentStep,currentTestFile, testName);
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
                        LoggerT.WriteInConsole("The specific Keyword \""+currentKeywordStep+"\" does not exists, check the test file and verify the action cell", LoggerT.ERROR_LEVEL);
                        TestRunner.validateTest("", LoggerT.ERROR_LEVEL);
                }
            }
            if(currentTestFile.isFather()){
                LoggerT.WriteInConsole("======================================= Finishing the test ============================================",LoggerT.HEADER_TEXT_LEVEL);
            }

        }catch (Exception e){
            LoggerT.WriteInConsole("Sometings go wrong reading the test file. Check function name or runTes(ParamName) in Class test.", LoggerT.WARNING_LEVEL);
            LoggerT.WriteInConsole(e.toString(), LoggerT.ERROR_LEVEL);
            TestRunner.validateTest("", LoggerT.ERROR_LEVEL);
        }

    }
    
    private void executeCallTo (TestFile fatherTestFile, int fatherTestStepNum){
        String [] currentFatherInputStep;
        currentFatherInputStep = fatherTestFile.getInput(fatherTestStepNum);
        String callToFileName = currentFatherInputStep[0];
        LoggerT.WriteInConsole("*******************************************  Running CallTo "+"\""+callToFileName+"\"    *******************************************", LoggerT.WARNING_LEVEL);
        TestFile callToTestFile = new TestFile (callToFileName+AppKeys.TEST_FILE_EXTENSION,fatherTestFile.getTestProfileName(),false,fatherTestFile.getClassName());
        callToTestFile.setWebActions(fatherTestFile.getWebActions());
        this.testFileList.add(callToTestFile);
        this.runTest(callToFileName);
        this.testFileList.remove(callToTestFile);
        LoggerT.WriteInConsole("******************************************* Ending CallTo "+"\""+callToFileName+"\"    *******************************************", LoggerT.WARNING_LEVEL);
    }

    public static void validateTest (String msg, int logLevel){
        if(msg.contains(LoggerT.MSG_STEP_ERROR) || logLevel == LoggerT.ERROR_LEVEL) Assert.fail(msg);
    }

    private TestFile getTestFromList(String testName){
        TestFile currentTest=null;
        for(TestFile t:testFileList){
            if(t.getName().equals(testName+ AppKeys.TEST_FILE_EXTENSION))currentTest = t;
        }
        return currentTest;
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
}

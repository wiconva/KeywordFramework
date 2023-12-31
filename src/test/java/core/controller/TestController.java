package core.controller;
import core.actions.VerifyActions;
import core.testFile.TestFile;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import core.actions.UtilActions;
import core.actions.WebActions;
import core.keys.AppKeys;
import core.tools.*;
import java.util.ArrayList;
import java.util.List;


public class TestController {

    //List for all test in execution.
    private List <TestFile> testFileList = new ArrayList<>();
    private String browser;
    private boolean runheadless;
    private String profileDir;


    public void runTest (String testName){
        String currentKeywordStep;
        String [] currentWebObjectStep;
        String [] currentInputStep;
        String [] currentOutputStep;
        TestFile currentTestFile=this.getTestFromList(testName);
        try {
            if (currentTestFile.isFather()) TTestRunnerPrinter.printHeaderLogTest(currentTestFile);
            for (int currentStep = 1; currentStep < currentTestFile.getSteps().length; currentStep++) {
                currentTestFile.tranformInput(currentStep);
                TTestRunnerPrinter.printStep(currentStep,currentTestFile, testName);
                currentKeywordStep =   currentTestFile.getKeyword(currentStep);
                currentWebObjectStep = currentTestFile.getWebObjectInput(currentStep);
                currentInputStep =     currentTestFile.getInput(currentStep);
                currentOutputStep =    currentTestFile.getOuputs(currentStep);

                TLogger.WriteInConsole("Test Loaded",TLogger.NORMAL_LEVEL);
                TLogger.WriteInConsole("Executing the keyword action",TLogger.NORMAL_LEVEL);
                switch (currentKeywordStep) {
                    case "loadwebdriver":
                        currentTestFile.setWebActions(new WebActions(this.browser,this.runheadless));
                        break;
                    case "callto":
                        this.executeCallTo(currentTestFile, currentStep);
                        break;
                    case "browserget":
                        currentTestFile.getWebActions().browserGet(currentWebObjectStep, currentInputStep, currentOutputStep);
                        break;
                    case "sleep":
                        UtilActions.sleep(currentInputStep);
                        break;
                    case "gettext":
                        currentTestFile.getWebActions().getText(currentWebObjectStep, currentInputStep, currentOutputStep, currentTestFile.getOutputsList());
                        break;
                    case "inputtext":
                        currentTestFile.getWebActions().inputText(currentWebObjectStep, currentInputStep, currentOutputStep);
                        break;
                    case "verify":
                        VerifyActions.verify(currentWebObjectStep, currentInputStep, currentOutputStep);
                        break;
                    default:
                        TLogger.WriteInConsole(TLogger.MSG_STEP_ERROR+"The specific Keyword \""+currentKeywordStep+"\" does not exists, check the test file and verify the action cell", TLogger.ERROR_LEVEL);
                        TestController.validateTest(TLogger.ERROR_LEVEL);
                }
                TLogger.WriteInConsole("The keyword action was executed",TLogger.NORMAL_LEVEL);
            }
            if(currentTestFile.isFather()){TTestRunnerPrinter.printFooterTest();}

        }catch (Exception e){
            TLogger.WriteInConsole(TLogger.MSG_STEP_ERROR+ "Sometings go wrong reading the test file. Check function name in test class.", TLogger.WARNING_LEVEL);
            TLogger.WriteInConsole(e.toString(), TLogger.ERROR_LEVEL);
            TestController.validateTest(e.getMessage(), TLogger.ERROR_LEVEL);
        }

    }



    public void executeTest (){
        StackTraceElement [] ste = Thread.currentThread().getStackTrace();
        runTest(ste[2].getMethodName());
    }
    
    private void executeCallTo (TestFile fatherTestFile, int fatherTestStepNum){
        String [] currentFatherInputStep;
        currentFatherInputStep = fatherTestFile.getInput(fatherTestStepNum);
        String callToFileName = currentFatherInputStep[0];
        TLogger.WriteInConsole("*******************************************  Running CallTo "+"\""+callToFileName+"\"    *******************************************", TLogger.WARNING_LEVEL);
        TestFile callToTestFile =   new TestFile (callToFileName+AppKeys.TEST_FILE_EXTENSION,fatherTestFile.getTestProfileName(),this.profileDir,
                            false,fatherTestFile.getClassName(),(fatherTestFile.isFather())?fatherTestFile.getName()
                                    :fatherTestFile.getPrincipalTestName());
        callToTestFile.setWebActions(fatherTestFile.getWebActions());
        callToTestFile.setOutputsList(fatherTestFile.getOutputsList());
        this.testFileList.add(callToTestFile);
        this.runTest(callToFileName);
        this.testFileList.remove(callToTestFile);
        TLogger.WriteInConsole("******************************************* Ending CallTo "+"\""+callToFileName+"\"    *******************************************", TLogger.WARNING_LEVEL);
    }
    private TestFile getTestFromList(String testName){
        TestFile currentTest=null;
        for(TestFile t:testFileList){
            if(t.getName().equals(testName+ AppKeys.TEST_FILE_EXTENSION))currentTest = t;
        }
        return currentTest;
    }

    public static void validateTest (String msg, int logLevel){
        if(msg.contains(TLogger.MSG_STEP_ERROR) || logLevel == TLogger.ERROR_LEVEL) Assert.fail(msg);
    }
    public static void validateTest (int logLevel){
        validateTest("",logLevel);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser","runheadless","profile","profileDir"})
    public synchronized void beforeMehtods(ITestResult r, String browser, boolean runheadless, String profile, String profileDir) throws NoSuchFieldException {
        /*If this methods fail, all more test case will be Ignored*/
        this.browser = browser;
        this.runheadless = runheadless;
        this.profileDir = profileDir;
        TestFile currentTestFile;
        String testFileName = r.getMethod().getMethodName()+ AppKeys.TEST_FILE_EXTENSION;
        currentTestFile = new TestFile ( testFileName, profile.trim(), profileDir, true, r.getTestClass().getName());
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
                if(currentTest.getWebActions()!= null)currentTest.getWebActions().closeBrowser();
            }
        }
        if(deleteTest!=null)testFileList.remove(deleteTest);
    }
}

package utilities.framework;

import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.actions.UtilActions;
import utilities.actions.WebActions;
import utilities.tools.*;
import java.util.ArrayList;
import java.util.List;


public class TestExecutor {
    private List <TestFile> testFileList = new ArrayList<>();

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser","runheadless","profile"})
    public void beforeMehtods(ITestResult r, String browser, boolean runeadless, String profile){
        /*If this methods fail, all more test case will be Ignored*/
        TestFile currentTestFile;
        String testFileName = r.getMethod().getMethodName()+Keys.TEST_FILE_EXTENSION;
        currentTestFile = this.loadTest( testFileName, profile.trim());
        currentTestFile.setWebActions(new WebActions(browser,runeadless));
        this.testFileList.add(currentTestFile);
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void afterMethods (ITestResult resultTest){
        String testNameResultWithExtension;
        testNameResultWithExtension = resultTest.getMethod().getMethodName()+ Keys.TEST_FILE_EXTENSION;
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
        String keywordStep;
        String [] webObjectStep;
        String [] inputStep;
        String [] outputStep;
        TestFile currentTestFile=this.getCurrentTest(testName);
        try {
            this.printHeaderLogTest(currentTestFile);
            for (int i = 1; i < currentTestFile.getSteps().length; i++) {
                currentTestFile.tranformInput(i);
                this.printStep(i,currentTestFile);
                keywordStep =   this.getKeyword(i, currentTestFile);
                webObjectStep = this.getWebObjectInput(i,currentTestFile);
                inputStep =     this.getInput(i,currentTestFile);
                outputStep =    this.getOuputs(i,currentTestFile);

                switch (keywordStep) {
                    case "sleep":
                        UtilActions.sleep(inputStep);
                        break;
                    case "openbrowser":
                        currentTestFile.getWebActions().openBrowser(webObjectStep, inputStep, outputStep);
                        break;
                    case "inputtext":
                        currentTestFile.getWebActions().inputText(webObjectStep, inputStep, outputStep);
                        break;
                    default:
                        TestValidator.assertAndWriteInConsole("The specific Keyword \""+keywordStep+"\" does not exists, check the test file and verify the action cell",false, TestValidator.ERROR_LEVEL);
                }
            }
        }catch (Exception e){
            TestValidator.assertAndWriteInConsole("Sometings go wrong reading the test file. Check function name or runTes(ParamName) in Class test.",false,TestValidator.WARNING_LEVEL);
            TestValidator.assertAndWriteInConsole(e.toString(),true, TestValidator.ERROR_LEVEL);
        }

    }

    private TestFile getCurrentTest (String testName){
        TestFile currentTest=null;
        for(TestFile t:testFileList){
            if(t.getName().equals(testName+Keys.TEST_FILE_EXTENSION))currentTest = t;
        }
        return currentTest;
    }

    private TestFile loadTest (String testName, String profile){
        TestFile currentTestFile = new TestFile(testName, profile);
        currentTestFile.setUrl(new LocationPathFinder(Keys.TEST_REPOSITORY_PATH,currentTestFile.getName()).getPath());
        ExcelReader.readFileTest(currentTestFile);
        return currentTestFile;
    }

    private String [] getOuputs(int currentStep, TestFile currentTestFile) {
        String currentStepOutputs = currentTestFile.getSteps()[currentStep][Keys.OUTPUT_ROW_NUMBER];
               return  (!(currentStepOutputs==null)) ? currentStepOutputs.split(Keys.INPUT_DELIMITER):null;
    }

    private String [] getInput (int currentStep, TestFile currentTestFile){
        String currenStepInputs = currentTestFile.getSteps()[currentStep][Keys.INPUT_ROW_NUMBER];
              return  (!(currenStepInputs==null)) ? currenStepInputs.split(Keys.INPUT_DELIMITER):null;
    }

    private String getKeyword (int currentStep, TestFile currentTestFile){
        String currenStepKeyword = currentTestFile.getSteps()[currentStep][Keys.KEYWORD_ROW_NUMBER].toLowerCase();
        return  currenStepKeyword;
    }

    private String [] getWebObjectInput(int currentStep, TestFile currentTestFile){
        String currentStepWebObject = currentTestFile.getSteps()[currentStep][Keys.OBJECT_ROW_NUMBER];
        String[] transformWebObjectInput;
        if (currentStepWebObject != null){
            transformWebObjectInput= ExcelReader.readObjectRespositoryFile(currentStepWebObject.split(Keys.INPUT_DELIMITER)[0],
                                      currentStepWebObject.split(Keys.INPUT_DELIMITER)[Keys.OBJECT_ROW_NUMBER]);
            if(transformWebObjectInput[0] == null) transformWebObjectInput[Keys.METHOD_LOCATOR_ARRAY_NUMBER]= TestValidator.MSG_STEP_ERROR+ " The method locator is not define, check the Object repository file";
            if(transformWebObjectInput[1] == null) transformWebObjectInput[Keys.OBJET_LOCATOR_ARRAY_NUMBER]= TestValidator.MSG_STEP_ERROR+ " The Object locator is not define, check the Object repository file";
            return  transformWebObjectInput;
        }
        return null;
    }

    private void printStep (int step, TestFile currentTestFile){
        int excelRow = step+1;
        String keywordStep = this.getKeyword(step, currentTestFile);
        String [] webObjectStep = this.getWebObjectInput(step,currentTestFile);
        TestValidator.assertAndWriteInConsole("Executing the excel row ["+excelRow+"]",true, TestValidator.STEP_LEVEL);
        TestValidator.assertAndWriteInConsole("** Action: "+keywordStep,false, TestValidator.WARNING_LEVEL);
        TestValidator.assertAndWriteInConsole("** WebObject: "+currentTestFile.getSteps()[step][Keys.OBJECT_ROW_NUMBER]+
                " Method= " + ((webObjectStep !=null)?webObjectStep[Keys.METHOD_LOCATOR_ARRAY_NUMBER]:null)+
                "\tLocator= "+((webObjectStep !=null)?webObjectStep[Keys.OBJET_LOCATOR_ARRAY_NUMBER]:null) ,false, TestValidator.NORMAL_LEVEL);
        TestValidator.assertAndWriteInConsole("** Input: " + currentTestFile.getSteps()[step][Keys.INPUT_ROW_NUMBER],false, TestValidator.NORMAL_LEVEL);
        TestValidator.assertAndWriteInConsole("** Output: " + currentTestFile.getSteps()[step][Keys.OUTPUT_ROW_NUMBER],false, TestValidator.NORMAL_LEVEL);
        TestValidator.assertAndWriteInConsole("** Execution step: ",false, TestValidator.NORMAL_LEVEL);
    }

    private void printHeaderLogTest (TestFile currentTestFile){
        TestValidator.assertAndWriteInConsole("====================================================================",true, TestValidator.HEADER_TEXT_LEVEL);
        TestValidator.assertAndWriteInConsole("                 AUTOMATION TEST STUDIO",false, TestValidator.WARNING_LEVEL);
        TestValidator.assertAndWriteInConsole(" Test location: ["+ currentTestFile.getUrl()+"]",false, TestValidator.WARNING_LEVEL);
        TestValidator.assertAndWriteInConsole("====================================================================",true, TestValidator.HEADER_TEXT_LEVEL);
    }
}

package core.testFile;

import core.actions.WebActions;
import core.controller.TestController;
import core.keys.AppKeys;
import core.tools.TExcelReader;
import core.tools.TLocationPathFinder;
import core.tools.TLogger;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestFile {
    private boolean isFather;
    private String name;
    private String url;
    private String [][] steps;
    private final int CELLS = 4;
    private String testProfileName;
    private WebActions webActions;
    public Hashtable <String,String> outputsList = new Hashtable<>();
    private Hashtable<String, String> data = new Hashtable<>();
    private String className;
    private String dataFileName;
    private String principalTestName ="";
    private String profileDir;


   //Constructors principal test file.
    public TestFile(String name, String profile, String profileDir, boolean isFather, String className){
       this.name = name;
       this. testProfileName = profile;
       this. isFather = isFather;
       this.className = className;
       this.profileDir = profileDir;
       this.setUrl(new TLocationPathFinder(AppKeys.TEST_REPOSITORY_PATH,this.getName()).getPath());
       this.dataFileName = this.getDataFileName(className);
       this.loadData();
       TExcelReader.readFileTest(this);
    }

    //Constructor callto test file.
    public TestFile(String name, String profile, String profileDir, boolean isFather, String className, String principalTestName){
        this.name = name;
        this. testProfileName = profile;
        this. isFather = isFather;
        this.className = className;
        this.profileDir = profileDir;
        this.setUrl(new TLocationPathFinder(AppKeys.TEST_REPOSITORY_PATH,this.getName()).getPath());
        this.dataFileName = this.getDataFileName(className);
        this.principalTestName = principalTestName;
        this.loadData();
        TExcelReader.readFileTest(this);
    }

    private void loadData() {
        if(dataFileName!="" && dataFileName!=null){
          TExcelReader.readDataTest(this);
        }
    }

    public String getKeyword(int currentStep){
        String currenStepKeyword = this.getSteps()[currentStep][AppKeys.KEYWORD_ROW_NUMBER].toLowerCase();
        return  currenStepKeyword;
    }

    public String [] getWebObjectInput(int currentStep){
        String currentStepWebObject = this.getSteps()[currentStep][AppKeys.OBJECT_ROW_NUMBER];
        String[] transformWebObjectInput;
        if (currentStepWebObject != null){
            transformWebObjectInput= TExcelReader.readObjectRespositoryFile(currentStepWebObject.split(AppKeys.INPUT_DELIMITER)[0],
                    currentStepWebObject.split(AppKeys.INPUT_DELIMITER)[AppKeys.OBJECT_ROW_NUMBER]);
            if(transformWebObjectInput[0] == null) transformWebObjectInput[AppKeys.METHOD_LOCATOR_ARRAY_NUMBER]= TLogger.MSG_STEP_ERROR+ " The method locator is not define, check the Object repository file";
            if(transformWebObjectInput[1] == null) transformWebObjectInput[AppKeys.OBJET_LOCATOR_ARRAY_NUMBER]= TLogger.MSG_STEP_ERROR+ " The Object locator is not define, check the Object repository file";
            return  transformWebObjectInput;
        }
        return null;
    }

    public String [] getOuputs(int currentStep) {
        String currentStepOutputs = this.getSteps()[currentStep][AppKeys.OUTPUT_ROW_NUMBER];
        return  (!(currentStepOutputs==null)) ? currentStepOutputs.split(AppKeys.INPUT_DELIMITER):null;
    }

    public String [] getInput (int currentStep){
        String currenStepInputs = this.getSteps()[currentStep][AppKeys.INPUT_ROW_NUMBER];
        return  (!(currenStepInputs==null)) ? currenStepInputs.split(AppKeys.INPUT_DELIMITER):null;
    }

    public void tranformInput (int stepNum){
        String inputStep = this.steps[stepNum][2];
        if (!(inputStep== null)){
            String regex = AppKeys.VARIABLE_INPUT_FORMAT_REGEX;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(inputStep);
            String [] result;
            String [] varTranformated;
            String varInput = inputStep;

            if(matcher.find()){
                result = inputStep.split(regex);
                for(String s: result){
                    if(s!="")varInput = varInput.replace(s,"-");
                }
                varTranformated = varInput.split("-");
                for (String s:varTranformated){
                    String varReaded="";
                    if(!s.equals("")){
                        if(outputsList.get(s)!= null && outputsList.get(s)!=""){
                           varReaded = outputsList.get(s);
                           this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,varReaded);
                        }else if (getProfileKey(s) != null && getProfileKey(s)!= "" ){
                            varReaded = getProfileKey(s);
                            this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,varReaded);
                        }else if (data.get(s)!= null && data.get(s)!= ""){
                            varReaded = data.get(s);
                            this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,varReaded);
                        } else {
                            this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,"${"+ TLogger.MSG_STEP_ERROR+" The value for: "+s+" input is not define. Check profile.properties, data file, suite xml profile parameter or output in test excel file. }");
                        }
                    }
                }
            }
        }

    }

    private String getDataFileName (String testClassName){
        String dataFileName="";
        try {
            Class <?> fatherClassReference = Class.forName(testClassName);
            Constructor constructor = fatherClassReference.getDeclaredConstructor();
            Object classInstance = constructor.newInstance();
            Field field = fatherClassReference.getDeclaredField(AppKeys.DATA_FILE_CLASS_FIELD_NAME);
            dataFileName = (String)field.get(classInstance);
        } catch (Exception e) {
            TLogger.WriteInConsole("NOT data file name in the test class, check the class test" + testClassName, TLogger.WARNING_LEVEL);
        }
        return dataFileName;
    }

    private String getProfileKey(String key){
        TLocationPathFinder locationPathFinder = new TLocationPathFinder(AppKeys.PROFILE_TARGET_DIR_PATH+this.profileDir,AppKeys.PROFILE_FILE_NAME);
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(locationPathFinder.getPath()));
            return properties.getProperty(this.testProfileName+"."+ key);
        } catch (IOException e) {
            TLogger.WriteInConsole(e.toString(), TLogger.ERROR_LEVEL);
            TestController.validateTest("", TLogger.ERROR_LEVEL);
        }
        return null;
    }
    public String getPrincipalTestName(){return this.principalTestName;}
    public void setPrincipalTestName(String principalTestName){this.principalTestName = principalTestName;}
    public void setData(Hashtable<String, String>data){this.data=data;}
    public String getDataFileName(){return  this.dataFileName;}
    public String getClassName(){return this.className;};
    public void setClassName(String className){this.className = className;}
    public boolean isFather(){return this.isFather;}
    public String getTestProfileName(){return this.testProfileName;};
    public void setWebActions(WebActions webActions) {this.webActions = webActions;}
    public WebActions getWebActions(){return this.webActions;}
    public String [][] getSteps(){return this.steps;}
    public void setSteps (String [][] steps){this.steps = steps;}
    public String getUrl() {return url;}
    public void setUrl(String url){this.url =url;}
    public int getCELLS(){return CELLS;}
    public void setOutputsList (Hashtable <String, String> outputsList){this.outputsList = outputsList;}
    public Hashtable<String, String> getOutputsList() {return outputsList;}
    public String getName() {return this.name;}
}

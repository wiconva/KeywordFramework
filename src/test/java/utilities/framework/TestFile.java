package utilities.framework;

import utilities.actions.WebActions;
import utilities.keys.AppKeys;
import utilities.tools.ExcelReaderT;
import utilities.tools.LocationPathFinderT;
import utilities.tools.LoggerT;
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
    public Hashtable <String,String> outputs = new Hashtable<>();
    private WebActions webActions;
    private Hashtable<String, String> data = new Hashtable<>();
    private String className;
    private String dataFileName;

    public TestFile(String name, String profile, boolean isFather, String className){
       this.name = name;
       this. testProfileName = profile;
       this. isFather = isFather;
       this.className = className;
       this.setUrl(new LocationPathFinderT(AppKeys.TEST_REPOSITORY_PATH,this.getName()).getPath());
       this.dataFileName = this.getDataFileName(className);
       this.data = this.loadData(this.dataFileName);
       ExcelReaderT.readFileTest(this);
    }

    private Hashtable<String, String> loadData(String dataFileName) {
        Hashtable<String, String > testData = new Hashtable<>();
        if(dataFileName!="" && dataFileName!=null){
          testData.put("var1","value1");
        }
        return testData;
    }


    public String getKeyword(int currentStep){
        String currenStepKeyword = this.getSteps()[currentStep][AppKeys.KEYWORD_ROW_NUMBER].toLowerCase();
        return  currenStepKeyword;
    }

    public String [] getWebObjectInput(int currentStep){
        String currentStepWebObject = this.getSteps()[currentStep][AppKeys.OBJECT_ROW_NUMBER];
        String[] transformWebObjectInput;
        if (currentStepWebObject != null){
            transformWebObjectInput= ExcelReaderT.readObjectRespositoryFile(currentStepWebObject.split(AppKeys.INPUT_DELIMITER)[0],
                    currentStepWebObject.split(AppKeys.INPUT_DELIMITER)[AppKeys.OBJECT_ROW_NUMBER]);
            if(transformWebObjectInput[0] == null) transformWebObjectInput[AppKeys.METHOD_LOCATOR_ARRAY_NUMBER]= LoggerT.MSG_STEP_ERROR+ " The method locator is not define, check the Object repository file";
            if(transformWebObjectInput[1] == null) transformWebObjectInput[AppKeys.OBJET_LOCATOR_ARRAY_NUMBER]= LoggerT.MSG_STEP_ERROR+ " The Object locator is not define, check the Object repository file";
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
                    varInput = varInput.replace(s,"-");
                }
                varTranformated = varInput.split("-");
                for (String s:varTranformated){
                    String varNotFormatInput;
                    varNotFormatInput = s.replace("${","").replace("}","");
                    if(!varNotFormatInput.equals("")){
                        String propertieRaded;

                        propertieRaded = ((getProfileKey(varNotFormatInput) != null))? getProfileKey(varNotFormatInput):outputs.get(varNotFormatInput);
                        if((propertieRaded != null)){
                            this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,propertieRaded);
                        }else{
                            this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,"${"+ LoggerT.MSG_STEP_ERROR+" The value for: "+s+" input is not define. Check profile.properties, suite xml profile parameter or output in test excel file. }");
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
            Field field = fatherClassReference.getDeclaredField(AppKeys.DATA_FILE_FIELD);
            dataFileName = (String)field.get(classInstance);
        } catch (Exception e) {
            LoggerT.WriteInConsole("NOT data file name in the test class, check the class test" + testClassName,LoggerT.WARNING_LEVEL);
        }
        return dataFileName;
    }

    private String getProfileKey(String key){
        LocationPathFinderT locationPathFinder = new LocationPathFinderT(AppKeys.PROFILE_TARGET_DIR_PATH,AppKeys.PROFILE_FILE_NAME);
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(locationPathFinder.getPath()));
            return properties.getProperty(this.testProfileName+"."+ key);
        } catch (IOException e) {
            LoggerT.WriteInConsole(e.toString(), LoggerT.ERROR_LEVEL);
            TestRunner.validateTest("", LoggerT.ERROR_LEVEL);
        }
        return null;
    }
    public String getClassName(){return this.className;};
    public void setClassName(String className){this.className = className;}
    public boolean isFather(){return this.isFather;}
    public String getTestProfileName(){return this.testProfileName;};
    public void setWebActions(WebActions webActions) {this.webActions = webActions;}
    public WebActions getWebActions(){return this.webActions;}
    public String [][] getSteps(){return this.steps;}
    public void setSteps (String [][] steps){
        this.steps = steps;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url){this.url =url;}
    public int getCELLS(){return CELLS;}
    public void setOutput (String key, String value){
        this.outputs.put(key,value);
    }
    public String getName() {return this.name;}
}

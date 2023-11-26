package utilities.framework;

import utilities.actions.WebActions;
import utilities.keys.AppKeys;
import utilities.tools.ExcelReader;
import utilities.tools.LocationPathFinder;
import utilities.tools.Logger;
import java.io.FileReader;
import java.io.IOException;
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

    public TestFile(String name, String profile, boolean isFather){
       this.name = name;
       this. testProfileName = profile;
       this. isFather = isFather;
       this.setUrl(new LocationPathFinder(AppKeys.TEST_REPOSITORY_PATH,this.getName()).getPath());
       ExcelReader.readFileTest(this);
    }


    public String getKeyword(int currentStep){
        String currenStepKeyword = this.getSteps()[currentStep][AppKeys.KEYWORD_ROW_NUMBER].toLowerCase();
        return  currenStepKeyword;
    }

    public String [] getWebObjectInput(int currentStep){
        String currentStepWebObject = this.getSteps()[currentStep][AppKeys.OBJECT_ROW_NUMBER];
        String[] transformWebObjectInput;
        if (currentStepWebObject != null){
            transformWebObjectInput= ExcelReader.readObjectRespositoryFile(currentStepWebObject.split(AppKeys.INPUT_DELIMITER)[0],
                    currentStepWebObject.split(AppKeys.INPUT_DELIMITER)[AppKeys.OBJECT_ROW_NUMBER]);
            if(transformWebObjectInput[0] == null) transformWebObjectInput[AppKeys.METHOD_LOCATOR_ARRAY_NUMBER]= Logger.MSG_STEP_ERROR+ " The method locator is not define, check the Object repository file";
            if(transformWebObjectInput[1] == null) transformWebObjectInput[AppKeys.OBJET_LOCATOR_ARRAY_NUMBER]= Logger.MSG_STEP_ERROR+ " The Object locator is not define, check the Object repository file";
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
                            this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,"${"+ Logger.MSG_STEP_ERROR+" The value for: "+s+" input is not define. Check profile.properties, suite xml profile parameter or output in test excel file. }");
                        }
                    }
                }
            }
        }

    }

    private String getProfileKey(String key){
        LocationPathFinder locationPathFinder = new LocationPathFinder(AppKeys.PROFILE_TARGET_DIR_PATH,AppKeys.PROFILE_FILE_NAME);
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(locationPathFinder.getPath()));
            return properties.getProperty(this.testProfileName+"."+ key);
        } catch (IOException e) {
            Logger.WriteInConsole(e.toString(), Logger.ERROR_LEVEL);
            TestExecutor.validateTest("",Logger.ERROR_LEVEL);
        }
        return null;
    }

    public boolean isFather(){return this.isFather;};
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

package utilities.framework;

import utilities.actions.WebActions;
import utilities.tools.Keys;
import utilities.tools.LocationPathFinder;
import utilities.tools.TestValidator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestFile {
    private String name;
    private String url;
    private String [][] steps;
    private final int CELLS = 4;
    private final String PROPERTIES_FILE_NAME = Keys.PROFILE_FILE_NAME;
    private String testProfileName;
    public Hashtable <String,String> outputs = new Hashtable<>();
    private WebActions webActions;

    public TestFile(String name, String profile){
       this.name = name;
       this. testProfileName = profile;
    }

    public void tranformInput (int stepNum){

        String inputStep = this.steps[stepNum][2];
        if (!(inputStep== null)){
            String regex = Keys.VARIABLE_INPUT_FORMAT_REGEX;
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
                        propertieRaded = ((loadPropertiesFile(varNotFormatInput) == null))?outputs.get(varNotFormatInput):loadPropertiesFile(varNotFormatInput);
                        if(!(propertieRaded == null)){
                            this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,propertieRaded);
                        }else{
                            this.steps[stepNum][2]= this.steps[stepNum][2].replace(s,"${"+ TestValidator.MSG_STEP_ERROR+" The value for: "+s+" input is not define. Check profile.properties, suite xml profile parameter or output in test excel file. }");
                        }
                    }
                }
            }
        }

    }

    private String loadPropertiesFile (String key){
        LocationPathFinder locationPathFinder = new LocationPathFinder(Keys.PROFILE_TARGET_DIR_PATH,this.PROPERTIES_FILE_NAME);
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(locationPathFinder.getPath()));
            return properties.getProperty(this.testProfileName+"."+ key);
        } catch (IOException e) {
            TestValidator.assertAndWriteInConsole(e.toString(),true, TestValidator.ERROR_LEVEL);
        }
        return null;
    }

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

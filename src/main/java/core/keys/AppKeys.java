package core.keys;

public final class AppKeys {
    //Configuration for install.
    public final static String TEST_REPOSITORY_PATH = "C:/TestRespository/";
    public final static String LOG_RELATIVE_PATH = "ExecutionLogs/";
    public final static String TEST_FILE_EXTENSION = ".xlsx";

    //Configuration framework use.
    public final static int TIME_TO_NEXT_EXECUTION_TRY_OF_KEYWORD_FAIL = 5;
    public final static int EXECUTION_TRY_OF_KEYWORD_FAIL = 3;
    public final static String USER_DIR = System.getProperty("user.dir");
    public final static String INPUT_DELIMITER = "\\|\\|";
    public final static String VARIABLE_INPUT_FORMAT_REGEX = "\\$\\{(\\w|_)*\\}";
    public final static String PROFILE_FILE_NAME ="testProfile.properties";
    public final static String PROFILE_TARGET_DIR_PATH = USER_DIR+"/src/test/resources/testProfiles/";
    public final static String DATA_FILE_CLASS_FIELD_NAME = "DATA_FILE_NAME";                                   //this field is for reflexion.
    public final static int TEST_SHEET_NUMBER = 0;
    public final static int METHOD_LOCATOR_ARRAY_NUMBER = 0;
    public final static int OBJET_LOCATOR_ARRAY_NUMBER = 1;
    public final static int KEYWORD_ROW_NUMBER = 0;
    public final static int OBJECT_ROW_NUMBER = 1;
    public final static int INPUT_ROW_NUMBER = 2;
    public final static int OUTPUT_ROW_NUMBER = 3;

}

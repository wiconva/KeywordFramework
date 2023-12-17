package core.keys;

public final class AppKeys {
    public final static String CHROME_DRIVER_PROPERTIE = "webdriver.chrome.driver";
    public final static String LOCATION_CHROME_DRIVER = "/src/test/resources/webdriver/chromedriver.exe";
    public final static String USER_DIR = System.getProperty("user.dir");
    public final static String TEST_FILE_EXTENSION = ".xlsx";
    public final static String INPUT_DELIMITER = "\\|\\|";
    public final static String VARIABLE_INPUT_FORMAT_REGEX = "\\$\\{\\w*\\}";
    public final static String TEST_REPOSITORY_PATH = "C:/TestRespository/";
    public final static String PROFILE_FILE_NAME ="testProfile.properties";
    public final static String DATA_FILE_FIELD_NAME = "DATA_FILE_NAME";
    public final static String PROFILE_TARGET_DIR_PATH = AppKeys.USER_DIR+"/src/test/resources/testProfiles/";
    public final static int TEST_SHEET_NUMBER = 0;
    public final static int METHOD_LOCATOR_ARRAY_NUMBER = 0;
    public final static int OBJET_LOCATOR_ARRAY_NUMBER = 1;
    public final static int KEYWORD_ROW_NUMBER = 0;
    public final static int OBJECT_ROW_NUMBER = 1;
    public final static int INPUT_ROW_NUMBER = 2;
    public final static int OUTPUT_ROW_NUMBER = 3;
}

package utilities.actions;

import utilities.tools.TestValidator;

public class UtilActions {
    private static final String MSG_TIME_COMPLETE = "The time for sleep is completed";

    public static void sleep (String [] input) {
        int time = (int)Double.parseDouble(input[0])*1000;
        try {
            Thread.sleep(time);
            TestValidator.assertAndWriteInConsole(MSG_TIME_COMPLETE,false, TestValidator.NORMAL_LEVEL);
        } catch (InterruptedException e) {
            TestValidator.assertAndWriteInConsole(e.toString(),false, TestValidator.WARNING_LEVEL);
        }
    }
}

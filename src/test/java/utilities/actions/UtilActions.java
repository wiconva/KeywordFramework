package utilities.actions;

import utilities.framework.TestExecutor;
import utilities.tools.Logger;

public class UtilActions {
    private static final String MSG_TIME_COMPLETE = "The time for sleep is completed";
    private static final String MSG_BEGIN_TIME_TO_SLEEP = "Begin Time to sleep";

    public static void sleep (String [] input) {
        int time = (int)Double.parseDouble(input[0])*1000;
        try {
            Logger.WriteInConsole(MSG_BEGIN_TIME_TO_SLEEP,Logger.NORMAL_LEVEL);
            Thread.sleep(time);
            Logger.WriteInConsole(MSG_TIME_COMPLETE, Logger.NORMAL_LEVEL);
        } catch (InterruptedException e) {
            Logger.WriteInConsole(e.toString(), Logger.ERROR_LEVEL);
            TestExecutor.validateTest("Impossible sleep the curent Thread", Logger.ERROR_LEVEL);
        }
    }
}

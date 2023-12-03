package utilities.actions;

import utilities.framework.TestRunner;
import utilities.tools.LoggerT;

public class UtilActions {
    private static final String MSG_TIME_COMPLETE = "The time for sleep is completed";
    private static final String MSG_BEGIN_TIME_TO_SLEEP = "Begin Time to sleep";

    public static void sleep (String [] input) {
        int time = (int)Double.parseDouble(input[0])*1000;
        try {
            LoggerT.WriteInConsole(MSG_BEGIN_TIME_TO_SLEEP, LoggerT.NORMAL_LEVEL);
            Thread.sleep(time);
            LoggerT.WriteInConsole(MSG_TIME_COMPLETE, LoggerT.NORMAL_LEVEL);
        } catch (InterruptedException e) {
            LoggerT.WriteInConsole(e.toString(), LoggerT.ERROR_LEVEL);
            TestRunner.validateTest("Impossible sleep the curent Thread", LoggerT.ERROR_LEVEL);
        }
    }
}

package core.actions;

import core.controller.TestController;
import core.tools.TLogger;

public class UtilActions {
    private static final String MSG_TIME_COMPLETE = "The time for sleep is completed";
    private static final String MSG_BEGIN_TIME_TO_SLEEP = "Begin Time to sleep";

    public static void sleep (String [] input) {
        int time = (int)Double.parseDouble(input[0])*1000;
        try {
            TLogger.WriteInConsole(MSG_BEGIN_TIME_TO_SLEEP, TLogger.NORMAL_LEVEL);
            Thread.sleep(time);
            TLogger.WriteInConsole(MSG_TIME_COMPLETE, TLogger.NORMAL_LEVEL);
        } catch (InterruptedException e) {
            TLogger.WriteInConsole(e.toString(), TLogger.ERROR_LEVEL);
            TestController.validateTest("Impossible sleep the curent Thread", TLogger.ERROR_LEVEL);
        }
    }
}

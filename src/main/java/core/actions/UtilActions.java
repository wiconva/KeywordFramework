package core.actions;

import core.controller.TestController;
import core.tools.TLogger;

public class UtilActions {

    public static void sleep (String [] input) {
        int time = (int)Double.parseDouble(input[0])*1000;
        try {
            TLogger.writeInConsole("Begin Time to sleep", TLogger.NORMAL_LEVEL);
            Thread.sleep(time);
            TLogger.writeInConsole("The time for sleep is completed", TLogger.NORMAL_LEVEL);
        } catch (InterruptedException e) {
            TestController.validateTest("Impossible sleep the curent Thread", TLogger.ERROR_LEVEL);
            TLogger.writeInConsole(e.getMessage(), TLogger.ERROR_LEVEL);
            TestController.validateTest(TLogger.ERROR_LEVEL);
        }
    }
}

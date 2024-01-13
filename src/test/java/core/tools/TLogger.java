package core.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TLogger {
    public static final String  MSG_STEP_ERROR = "[!!!ERROR!!!]";
    private static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";

    public static final int STEP_LEVEL = 0;
    public static final int WARNING_LEVEL = 1;
    public static final int ERROR_LEVEL = 2;
    public static final int NORMAL_LEVEL =3;
    public static final int HEADER_TEXT_LEVEL = 4;

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void writeInConsole(String msg, int logLevel){
        msg = giveFormatAtMsg(msg,logLevel);
        System.out.println(msg);
    }

    private static String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String currentDateTime = "["+dateFormat.format(new Date())+"]-> ";
        return currentDateTime;
    }

    private static String giveFormatAtMsg (String msg, int logLevel){

        msg = (logLevel==ERROR_LEVEL)?MSG_STEP_ERROR+msg:msg;
        logLevel = (msg.contains(MSG_STEP_ERROR))?ERROR_LEVEL:logLevel;

    //Format for a father test.
        if(msg.contains("**")){
            msg = msg.replace("\n","\n\t\t\t  ");
            msg = "\t"+msg;
        }else if (! msg.contains("====")){
            msg = msg.replace("\n","\n\t\t\t  ");
            msg = "\t\t- "+msg;
        }

        switch (logLevel){
            case STEP_LEVEL:
                msg =(ANSI_GREEN+getTime()+ msg+ANSI_RESET).replace("\t","");
                break;
            case WARNING_LEVEL:
                msg = ANSI_YELLOW+msg+ANSI_RESET;
                break;
            case NORMAL_LEVEL:
                msg = ANSI_RESET+msg+ANSI_RESET;
                break;
            case ERROR_LEVEL:
                msg = ANSI_RED+msg+ANSI_RESET;
                break;
            case HEADER_TEXT_LEVEL:
                msg = ANSI_BLUE+msg+ANSI_RESET;
                break;
        }
        return msg;
    }
}

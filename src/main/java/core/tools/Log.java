package core.tools;
import core.keys.AppKeys;
import java.io.*;

public class Log {
    private static String logDirPath;
    private final String logExtension = ".txt";
    private String logAbsolutePath;

    public Log (String logName){
       this.logAbsolutePath = AppKeys.TEST_REPOSITORY_PATH+ logDirPath+logName.replace(" ","")+this.logExtension;
    }
    public static void setLogDirPath(String s){
        logDirPath = s;
    }
    public void write (String msg){
        File fLog = new File(this.logAbsolutePath);
        try {
         BufferedWriter bw = new BufferedWriter(new FileWriter(fLog,true));
         bw.append(msg);
         bw.newLine();
         bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

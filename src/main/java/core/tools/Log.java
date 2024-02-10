package core.tools;
import core.keys.AppKeys;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static String logFilePath;
    private final String logExtension = ".txt";
    private String logAbsolutePath;

    public Log (String logName){
       this.logAbsolutePath = logFilePath+logName.replace(" ","")+this.logExtension;
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

    public static void createLog (){
        File principalFolder = new File(AppKeys.TEST_REPOSITORY_PATH+AppKeys.LOG_RELATIVE_PATH);
        if(!principalFolder.exists()) principalFolder.mkdir();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
        logFilePath =principalFolder.getAbsolutePath()+"/Execution_"+dateFormat.format(new Date())+"/";
        File f = new File(logFilePath);
        f.mkdir();
    }
}

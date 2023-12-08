package core.tools;

import java.io.File;

public class LocationPathFinderT {
    private final String ERROR_0= LoggerT.MSG_STEP_ERROR+ "Not was possible find the file \"";
    private final String ERROR_1= LoggerT.MSG_STEP_ERROR+ "Exist more that one File with the same name \"";

    private String targetDirectory;
    private String targetFileName;
    private String matchPath;
    private int matchCount=0;

    public LocationPathFinderT(String directory, String fileName){
        this.targetDirectory = directory;
        this.targetFileName = fileName;
    }

    /**
     * This method is used to find a file and return the absolute path.
     **/
    public String getPath (){
        String resultPath = getPath(this.targetDirectory);
        switch ( this.matchCount){
            case 0:
                return this.ERROR_0 + this.targetFileName +"\" .Verify that test file exists";
            case 1:
                return  resultPath;
            default:
                return this.ERROR_1 + this.targetFileName +"\". Delete repet files";
        }
    }

    private String  getPath (String directoryPath){
        for(File f:new File(directoryPath).listFiles()){
            if (!f.isDirectory()){
                if (f.getName().equals(this.targetFileName)){
                    matchPath = f.getAbsolutePath();
                    matchCount ++;
                }
            }else{
                this.getPath(f.getAbsolutePath());
            }
        }
        return matchPath;
    }

}

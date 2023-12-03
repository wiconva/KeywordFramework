package utilities.tools;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utilities.framework.TestRunner;
import utilities.framework.TestFile;
import utilities.keys.AppKeys;

import java.io.FileInputStream;


public class ExcelReaderT {

    public static void readDataTest(){

    }

    public static void readFileTest (TestFile test){
        try {
            if(!test.getUrl().contains(LoggerT.MSG_STEP_ERROR)) {
                final String excelDataType = "STRING";
                FileInputStream fis = new FileInputStream(test.getUrl());
                XSSFWorkbook workbook = new XSSFWorkbook(fis);
                XSSFSheet sheet = workbook.getSheetAt(AppKeys.TEST_SHEET_NUMBER);

                int rowCount = sheet.getLastRowNum();
                String [][] dataFileInput = new String [rowCount+1][test.getCELLS()] ;

                for (int i=0; i<= rowCount;i++){
                    for(int j=0; j< test.getCELLS(); j++){
                        try{
                            XSSFCell cell = sheet.getRow(i).getCell(j);
                            dataFileInput [i][j] = (cell.getCellType().toString().contains(excelDataType))?
                                    cell.getStringCellValue():Double.toString(cell.getNumericCellValue());
                        }catch  (NullPointerException e){
                            dataFileInput [i][j] =null;
                        }
                    }
                }
                test.setSteps(dataFileInput);
                workbook.close();
                fis.close();
            }

        } catch (Exception e) {
            LoggerT.WriteInConsole(e.toString(), LoggerT.WARNING_LEVEL);
        }
    }

    public static String [] readObjectRespositoryFile (String fileName, String webObject){
        int inputRespositorySize = 2; //return the Method and Object locator.
        int methodCellNumber = 1;
        int locatorCellNumber = 2;

        String absolutePath = new LocationPathFinderT(AppKeys.TEST_REPOSITORY_PATH, fileName+ AppKeys.TEST_FILE_EXTENSION).getPath();
        String [] returnValues = new String [inputRespositorySize];
        if(absolutePath.contains(LoggerT.MSG_STEP_ERROR)){
           returnValues[AppKeys.METHOD_LOCATOR_ARRAY_NUMBER] = absolutePath;
           returnValues[AppKeys.OBJET_LOCATOR_ARRAY_NUMBER] = absolutePath;
           return returnValues;
        }
        try{
            FileInputStream fis = new FileInputStream(absolutePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(AppKeys.TEST_SHEET_NUMBER);

            int rowCount = sheet.getLastRowNum();
            for (int i=1; i<= rowCount; i++){
               if(sheet.getRow(i).getCell(0).getStringCellValue().equals(webObject)){
                   returnValues [AppKeys.METHOD_LOCATOR_ARRAY_NUMBER] = sheet.getRow(i).getCell(methodCellNumber).getStringCellValue();
                   returnValues [AppKeys.OBJET_LOCATOR_ARRAY_NUMBER] = sheet.getRow(i).getCell(locatorCellNumber).getStringCellValue();
               }
            }
            workbook.close();
            fis.close();
        }catch (Exception e){
            LoggerT.WriteInConsole(e.toString(), LoggerT.ERROR_LEVEL);
            TestRunner.validateTest("", LoggerT.ERROR_LEVEL);
        }
        return returnValues;
    }
}

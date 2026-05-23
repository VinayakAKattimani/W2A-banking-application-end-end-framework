package com.orangehrm.utilities;

import com.orangehrm.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class TestUtil extends TestBase {

    public static String screenshotPath;
    public static String screenshotName;

    public static void captureScreenshot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Date d = new Date();
        screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
    }

    @DataProvider(name = "dp")
    public Object[][] getData(Method m) {

        String sheetName = m.getName();

        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        ArrayList<Object[]> dataList = new ArrayList<>();

        for (int rowNum = 2; rowNum <= rows; rowNum++) {

            boolean isEmpty = true;

            Object[] rowData = new Object[cols];

            for (int colNum = 0; colNum < cols; colNum++) {

                String cellData =
                        excel.getCellData(sheetName, colNum, rowNum);

                rowData[colNum] = cellData;

                if (cellData != null &&
                        !cellData.trim().isEmpty()) {

                    isEmpty = false;
                }
            }

            if (!isEmpty) {
                dataList.add(rowData);
            }
        }

        return dataList.toArray(new Object[0][]);
    }
}

    /*@DataProvider(name="dp")
    public Object[][] getData(Method m) {

        String sheetName = m.getName();

        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows - 1][1];

        Hashtable<String, String> table = null;

        for (int rowNum = 2; rowNum <= rows; rowNum++) {

            table = new Hashtable<String, String>();

            for (int colNum = 0; colNum < cols; colNum++) {

                table.put(
                        excel.getCellData(sheetName, colNum, 1),
                        excel.getCellData(sheetName, colNum, rowNum)
                );
            }

            data[rowNum - 2][0] = table;
        }

        return data;
    }

    /*@DataProvider(name = "dp")
    public Object[][] getData(Method m) {

        String sheetName = m.getName();

        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        ArrayList<Hashtable<String, String>> dataList =
                new ArrayList<>();

        for (int rowNum = 2; rowNum <= rows; rowNum++) {

            Hashtable<String, String> table =
                    new Hashtable<>();

            for (int colNum = 0; colNum < cols; colNum++) {

                String key =
                        excel.getCellData(sheetName,
                                colNum,
                                1);

                String value =
                        excel.getCellData(sheetName,
                                colNum,
                                rowNum);

                table.put(key, value);
            }

            dataList.add(table);
        }

        Object[][] data =
                new Object[dataList.size()][1];

        for (int i = 0; i < dataList.size(); i++) {

            data[i][0] = dataList.get(i);
        }

        return data;
    }*/


//  public static boolean isTestRunnable(String testName, ExcelReader excel){
//        String sheetName = "test_suite";
//        int rows = excel.getRowCount(sheetName);
//
//      for (int rNum = 2; rNum <= rows; rNum++) {
//
//          String testCase = excel.getCellData(sheetName,"TCID", rNum);
//
//          if (testCase.equalsIgnoreCase(testName)){
//              String runmode = excel.getCellData(sheetName, "Runmode", rNum);
//
//              if(runmode.equalsIgnoreCase("Y")) {
//                  return true;
//              }else {
//                  return false;
//              }
//
//          }
//      }
//      return false;
//  }
//    }
//}

package com.buts.AttendanceSystem;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AttendanceLog extends AttendanceGUI { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String newFilePath = "";
	static int attendanceLogErrorFlag = 0;
public static String createFile(String path) throws InvalidFormatException, IOException 
    { 
	attendanceLogErrorFlag = 0;
        // Blank workbook 
        @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(); 
  
        // Create a blank sheet 
        XSSFSheet sheet = workbook.createSheet("student Details"); 
   
        // This data needs to be written (Object[]) 
        Map<String, Object[]> data = new TreeMap<String, Object[]>(); 
        data.put("1", new Object[]{  "Date: " + date(), "Class time: " + FilePath.formattedClassTime, AttendanceGUI.classTimeHr, AttendanceGUI.classTimeMin }); 
        data.put("2", new Object[]{ "NAME" , "TIME IN", "IN STATUS" }); 
  
        // Iterate over data and write to sheet 
        Set<String> keyset = data.keySet(); 
        int rownum = 0; 
        for (String key : keyset) { 
            // this creates a new row in the sheet 
            Row row = sheet.createRow(rownum++); 
            Object[] objArr = data.get(key); 
            int cellnum = 0; 
            for (Object obj : objArr) { 
                // this line creates a cell in the next column of that row 
                Cell cell = row.createCell(cellnum++); 
                if (obj instanceof String) 
                    cell.setCellValue((String)obj); 
                else if (obj instanceof Integer) 
                    cell.setCellValue((Integer)obj); 
            } 
        } 
        try { //Check if file already exists
        	newFilePath = path +"\\" + FilePath.fName + ".xlsx";
        	File file = new File(newFilePath);
            if (file.exists() && file.isFile()) {
            	Toolkit.getDefaultToolkit().beep();
      		  int result = JOptionPane.showConfirmDialog(new JFrame(), "FILE ALREADY EXISTS. OVERWRITE IT?", "ERROR",
  			        JOptionPane.ERROR_MESSAGE);
      		  if (result == 0) {
      			  FileOutputStream out = new FileOutputStream(new File(newFilePath));       		  
      			  workbook.write(out);
      			  out.close();
              }
      		  else {attendanceLogErrorFlag = 1;}
            }
            else {
            FileOutputStream out = new FileOutputStream(new File(newFilePath)); 
            workbook.write(out);
            out.close();
            }
        } 
        catch (Exception e) { 
        	Toolkit.getDefaultToolkit().beep();
    		  JOptionPane.showMessageDialog(new JFrame(), "PLEASE CLOSE STUDENTS' LIST!!", "ERROR",
    			        JOptionPane.ERROR_MESSAGE); 
    		  attendanceLogErrorFlag = 1;
        }
        return newFilePath;
    }

}
//static String dailyAttendanceLog = "C:\\Users\\Buts\\Desktop\\XL\\Attendance Log.xlsx";
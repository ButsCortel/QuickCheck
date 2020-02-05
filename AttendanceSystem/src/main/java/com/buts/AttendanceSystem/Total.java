package com.buts.AttendanceSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Total extends AttendanceGUI  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String Log = null;
	static JFileChooser chooser = null;
	static File cFile = null;
	static ArrayList<String> studArr = null;
	static ArrayList<String> logArr = null;
	static ArrayList<String> uniques = null;

	public static void total() throws IOException, InvalidFormatException, InterruptedException {
  	  totalOpenFlag = 1;
		  chooser = new JFileChooser();
		  FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX files", "xlsx");
		  chooser.setFileFilter(filter);
		  chooser.setAcceptAllFileFilterUsed(false);
	      chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	      chooser.setFileHidingEnabled(true);
	      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	  cFile = chooser.getSelectedFile();
	    	  Log = cFile.getPath();
	    	  if (Log != null) {
	    		  
	    			InputStream inp = new FileInputStream(Log); 
	    			wb = new XSSFWorkbook(inp); 
	    		    XSSFSheet sheet = wb.getSheetAt(0);
	    		    int num = sheet.getLastRowNum(); 
	    		    Row row=sheet.getRow(num); //returns the logical row  
	    			Cell cell=row.getCell(0);
	    			if (!cell.getStringCellValue().equals("Finished")) {
	    	        Row row1 = sheet.createRow(++num); 
	    	        row1.createCell(0).setCellValue("Absent:"); 
	    	        try {
	    	        	
	    	    	    FileOutputStream fileOut = new FileOutputStream(Log); 
	    	    	    wb.write(fileOut); 
	    	    	    fileOut.close();
	    	    	    wb.close();
	    	        	}
	    	    	    
	    	    	catch (FileNotFoundException e) {
	    	    	    e.printStackTrace();
	    	      		  JOptionPane.showMessageDialog(new JFrame(), "PLEASE CLOSE ATTENDANCE LOG!!", "ERROR",
	    	    			        JOptionPane.ERROR_MESSAGE);
	    	    	    	}
	    	        uniques = new ArrayList<String>();
	    	        studentArray();
	    	        logArray();
	    	        for (String element: studArr) {
	    	        	if (!logArr.contains(element)) {
	    	        		uniques.add(element);	    	        		
	    	        	}
	    	        }
	    	        absents();

	    	        
	    			}
	    			else {
	    				totalOpenFlag = 0;
		    	        try {
		    	        	
		    	    	    FileOutputStream fileOut = new FileOutputStream(Log); 
		    	    	    wb.write(fileOut); 
		    	    	    fileOut.close();
		    	    	    wb.close();
		    	        	}
		    	    	    
		    	    	catch (FileNotFoundException e) {
		    	    	    e.printStackTrace();
		    	      		  JOptionPane.showMessageDialog(new JFrame(), "PLEASE CLOSE ATTENDANCE LOG!!", "ERROR",
		    	    			        JOptionPane.ERROR_MESSAGE);
		    	    	    	}
	    			}
	    			}

	     
	      }

        
    	  totalOpenFlag = 0;// TODO Auto-generated method stub

	}

	static void studentArray() throws IOException {
		 try
	        {
			 	studArr = new ArrayList<String>();
	            FileInputStream file = new FileInputStream(studentFilePath);
	 
	            //Create Workbook instance holding reference to .xlsx file
	            @SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(file);
	 
	            //Get first/desired sheet from the workbook
	            XSSFSheet sheet = workbook.getSheetAt(0);
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            rowIterator.next();
	            while (rowIterator.hasNext()) 
	            {	
	            	
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Iterator<Cell> cellIterator = row.cellIterator();
	                 
	                while (cellIterator.hasNext()) 
	                {
	                    XSSFCell cell = (XSSFCell) cellIterator.next();
	                    studArr.add(cell.getStringCellValue());

	                }
	            }
	            file.close();
	        } 
	        catch (Exception e) 
	        {	
	        	e.printStackTrace();
	    		  JOptionPane.showMessageDialog(new JFrame(), "PLEASE CLOSE STUDENTS' LIST!!", "ERROR",
	  			        JOptionPane.ERROR_MESSAGE);
	        }
		 }
	static void logArray () throws IOException {
		 try
	        {
			 	logArr = new ArrayList<String>();
	            FileInputStream file = new FileInputStream(Log);
	 
	            //Create Workbook instance holding reference to .xlsx file
	            @SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(file);
	 
	            //Get first/desired sheet from the workbook
	            XSSFSheet sheet = workbook.getSheetAt(0);
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            rowIterator.next();
	            rowIterator.next();
	            while (rowIterator.hasNext()) 
	            {	
	            	
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Iterator<Cell> cellIterator = row.cellIterator();
	                 
	                while (cellIterator.hasNext()) 
	                {
	                    XSSFCell cell = (XSSFCell) cellIterator.next();
	                    if (!cell.getStringCellValue().equals("Absent:"))	
	                    	{
	                    	logArr.add(cell.getStringCellValue());
	                    	}

	                }
	            }
	            file.close();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	    		  JOptionPane.showMessageDialog(new JFrame(), "PLEASE CLOSE ATTENDANCE LOG!!", "ERROR",
	  			        JOptionPane.ERROR_MESSAGE);
	        }}
	static void absents() throws IOException, InvalidFormatException, InterruptedException 
	{
		InputStream inp = new FileInputStream(Log); 
	    wb = new XSSFWorkbook(inp); 
	    Cell cell = null;
	    XSSFSheet sheet = wb.getSheetAt(0);
	    try {
	    	int num = sheet.getLastRowNum() + 1;
	    	for(String name : uniques) 
	    	{
	    		if (!name.equals("")&&!name.equals(null)) {
	    		Row row1 = sheet.createRow(num++);
	    		cell = row1.createCell(0);
	    		cell.setCellValue(name);}

	    	}
	        
	    	Row row1 = sheet.createRow(num++);
    		cell = row1.createCell(0);
    		cell.setCellValue("Finished");
	        FileOutputStream fileOut = new FileOutputStream(Log); 
	        wb.write(fileOut); 
	        fileOut.close();
	        wb.close();
	        totalOpenFlag = 0;
	    } 
	    catch (FileNotFoundException e) 
	    {
	    	e.printStackTrace();
	    	dupLog.setText("LOG IS OPEN!");
	    	inStatus.setText("ERROR!");
	    	JOptionPane.showMessageDialog(new JFrame(), "PLEASE CLOSE ATTENDANCE LOG!!", "ERROR",
			        JOptionPane.ERROR_MESSAGE);
	    	}
	    }
	}

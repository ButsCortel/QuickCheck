package com.buts.AttendanceSystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;


public class FilePath extends JFrame {

	private static final long serialVersionUID = 1L;
	static String dailyAttendanceLog = null;
	static int result = -1;
	static JPanel myPanel = null;
	static FilePath frame = null;
	private JPanel contentPane;
	java.io.File f = null;
	static String formattedClassTime = null;
	static LocalTime rawClassTime = null;
	static String fName = null;
	static JFileChooser fc = null;
	static int errorFlag =0;

	
	public static void choosePath() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new FilePath();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
					      public void windowClosing(WindowEvent we) {
					        int result = JOptionPane.showConfirmDialog(frame,
					            "Do you want to Exit ?", "Exit Confirmation : ",
					            JOptionPane.YES_NO_OPTION);
					        if (result == JOptionPane.YES_OPTION) {
					        	System.exit(0);
					        }
					        else if (result == JOptionPane.NO_OPTION) {
					          frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					        }
					      }
					    });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		


		
	}

	/**
	 * Create the frame.
	 */
	public FilePath() {
		
		
		setTitle("Initialization");
		setBounds(100, 100, 431, 186);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
        
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX files", "xlsx");
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		

        //fc.setMultiSelectionEnabled(true);
        //fc.setCurrentDirectory(new File("C:\\tmp"));
		
		JButton newFileButton = new JButton("New Attendance");
		newFileButton.addActionListener(new ActionListener() { 
			
			
 
            public void actionPerformed(ActionEvent e) {
       		 
            	  
      	      JTextField fileName = new JTextField(15);

      	      JPanel newFile = new JPanel();
      	      newFile.add(new JLabel("FILENAME:"));
      	      newFile.add(fileName);
      	      newFile.add(Box.createHorizontalStrut(15)); // a spacer
      	      newFile.setVisible(true);

      	   

      	      int result1 = JOptionPane.showConfirmDialog(null, newFile, 
      	               "ENTER FILE NAME", JOptionPane.OK_CANCEL_OPTION);
      	      fName = fileName.getText(); 
      	      if (result1 == JOptionPane.OK_OPTION) {
         		errorFlag =0;
      	    	  if (fName != null && !fName.equals("")) {
                   		{ 
    
                   			 
                        	  
      			      AttendanceGUI.hr = new JTextField(5);
      			      AttendanceGUI.min = new JTextField(5);

      			      myPanel = new JPanel();
      			      myPanel.add(new JLabel("Hour:"));
      			      myPanel.add(AttendanceGUI.hr);
      			      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
      			      myPanel.add(new JLabel("Minutes:"));
      			      myPanel.add(AttendanceGUI.min);
      			      myPanel.setVisible(true);

      			   

      			      result = JOptionPane.showConfirmDialog(null, myPanel, 
      			               "Enter Class time (MILITARY TIME)", JOptionPane.OK_CANCEL_OPTION);
      			      if (result == JOptionPane.OK_OPTION) {
      			  		try {
      			    		AttendanceGUI.classTimeHr = Integer.parseInt(AttendanceGUI.hr.getText());
      			    		AttendanceGUI.classTimeMin =Integer.parseInt(AttendanceGUI.min.getText());
      			    		setTime(AttendanceGUI.classTimeHr, AttendanceGUI.classTimeMin);

      			    	  }
      			    	  catch (NumberFormatException e1) {
      			    		  Toolkit.getDefaultToolkit().beep();
      			    		  JOptionPane.showMessageDialog(new JFrame(), "PLEASE ENTER WHOLE NUMBERS", "INVALID INPUT",
      			    			        JOptionPane.ERROR_MESSAGE);	
      			    		  errorFlag=1;
      			    		  
      			    	  }
      			      }
      			}	
                   		
                	if (result != -1 && result != JOptionPane.OK_CANCEL_OPTION && errorFlag == 0) {
                		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        dailyAttendanceLog = fc.getSelectedFile().toString();
                        try {
                       						
       					AttendanceGUI.file_Name = AttendanceLog.createFile(dailyAttendanceLog);
       					if (AttendanceLog.attendanceLogErrorFlag == 0){frame.dispose();AttendanceGUI.startGUI(null);} //prevent program from continuing if an error is encountered
       					
       				} catch (InvalidFormatException | IOException e1) {
       					// TODO Auto-generated catch block
       					e1.printStackTrace();
       					actionPerformed(e);
       								}
                        		}            		
                			}
                		}
      	    	  else {
      	    		Toolkit.getDefaultToolkit().beep();
      	    		  JOptionPane.showMessageDialog(new JFrame(), "ENTER FILE NAME!", "ERROR",
      	    			        JOptionPane.ERROR_MESSAGE);} 
      	    	  }
      	    	  
             	
               	}
        });
		newFileButton.setBounds(10, 65, 190, 75);
		contentPane.add(newFileButton);
		
		JButton existFileButton = new JButton("Existing Attendance");
		existFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			      fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			      fc.setFileHidingEnabled(false);
			      if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			         f = fc.getSelectedFile();
			         AttendanceGUI.file_Name = f.getPath();
			         AttendanceGUI.classTimeHr = (int)readCellData(0,2);
			         AttendanceGUI.classTimeMin = (int)readCellData(0,3);
			         
			         if (errorFlag != 1)
			         {
			         setTime(AttendanceGUI.classTimeHr, AttendanceGUI.classTimeMin);
			         dispose();
			         AttendanceGUI.startGUI(null);
			         }
			         else {
			        	 Toolkit.getDefaultToolkit().beep();
			        	 JOptionPane.showMessageDialog(new JFrame(), "INVALID FILE SELECTED!", "ERROR",
			        		        JOptionPane.ERROR_MESSAGE);
			         }
			      }
		}});
		existFileButton.setBounds(215, 65, 190, 75);
		contentPane.add(existFileButton);
		
		JLabel lblPleaseSelectOne = new JLabel("Initial Settings:");
		lblPleaseSelectOne.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseSelectOne.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPleaseSelectOne.setBounds(113, 11, 188, 43);
		contentPane.add(lblPleaseSelectOne);
	}
	static double readCellData(int vRow, int vColumn)  
	{
		errorFlag = 0;
	double value = 0;          //variable for storing the cell value  
	Workbook wb=null;           //initialize Workbook null  
	try  
	{  
	//reading data from a file in the form of bytes  
	FileInputStream fis=new FileInputStream(AttendanceGUI.file_Name);  
	//constructs an XSSFWorkbook object, by buffering the whole stream into the memory  
	wb=new XSSFWorkbook(fis);  
	}  
	catch(FileNotFoundException e)  
	{  
	e.printStackTrace();  
	}  
	catch(IOException e1)  
	{  
	e1.printStackTrace();  
	}  
	Sheet sheet=wb.getSheetAt(0);   //getting the XSSFSheet object at given index  
	Row row=sheet.getRow(vRow); //returns the logical row  
	Cell cell=row.getCell(vColumn); //getting the cell representing the given column  
	try {value=cell.getNumericCellValue();}
	catch (Exception e) {errorFlag = 1;}//getting cell value  
	return value;               //returns the cell value  
	}
	static void setTime(int hr, int min) {
		DateTimeFormatter classTimeFormat = DateTimeFormatter.ofPattern(" hh:mm:a");
  		try {rawClassTime = LocalTime.of(hr, min);
  		formattedClassTime = rawClassTime.format(classTimeFormat);}
  		catch (Exception e){
  			Toolkit.getDefaultToolkit().beep();
  		  JOptionPane.showMessageDialog(new JFrame(), "PLEASE ENTER NUMBERS 0-23 FOR HOUR AND 0-59 FOR MIN ONLY", "INVALID INPUT",
			        JOptionPane.ERROR_MESSAGE);	
  		  errorFlag=1;}

	}
}

package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewTestController implements Initializable{
	static Stage new_test;
    @FXML
    private JFXTextField test_name;

    @FXML
    private JFXTextField test_date;

    @FXML
    private Label test_status;

    @FXML
    void cancel(ActionEvent event) {
    	new_test.close();
    }
    static String randomCode = "";
    @FXML
    void create(ActionEvent event) {
    	String testName = test_name.getText();
    	String testDate = test_date.getText();
    	File testDir = new File(ClassSessionController.testQ);
    	randomCode = RandomStringUtils.randomAlphanumeric(7);
    	String[] configNames = testDir.list();
    	List<String> list = Arrays.asList(configNames);
    	  while (list.contains(randomCode + ".properties")) {
    		  randomCode = RandomStringUtils.randomAlphanumeric(7) ;
    	  }

    	if (testName.length() >0 &&  testDate.length() >0 && testName.length() < 31) {
    		if (!isValidName(testName) ||
    				!isValidName(testDate) ) 
				{
					test_status.setText("Special Characters are not allowed.");        			
				}
    		else if(Character.isWhitespace(testName.charAt(0)) || 
        			Character.isWhitespace(testDate.charAt(0))) 
        			{
    					test_status.setText("All fields should not\nstart with space/s.");	    	     		    	
        			}
    		else if (!dateFormat(testDate)) 
    				{
    					test_status.setText("Invalid date format.");
    				}
    		else if (!(testName.length() < 31)) 
			{
				test_status.setText("Test name exceeds character limit.");
			}
    		else if (check_test_name(configNames, testName)) //check for dup test name
    			{
    			test_status.setText("Test already exists.");
            	AlertBoxController.label_text = "Overwrite test?";
            	if(AlertBoxController.display("Test Already Exists!")) {
        			//System.out.println(testName + " " + testDate + " " + randomCode);
        			config(randomCode, testName, testDate);
        			changed = true;
        			new_test.close();
        			
            	}

        		
            	else {
            		changed = false;
            		AlertBoxController.alert_box.close();
            	}
    				
    			}
    		else {
    			//System.out.println(testName + " " + testDate + " " + randomCode);
    			config(randomCode, testName, testDate);
    			changed = true;
    			new_test.close();
    			
    		}
    	}
    	
    	else {
    		test_status.setText("Please don't leave blank fields.");
    	}
    }
    boolean check_test_name(String[] files, String tname) {
    	boolean dup = false;
    	for (String file: files) { 
    		InputStream input = null;
 	   		try
 	   			{
 	   			//System.out.println(file);
     	  	input = new FileInputStream(ClassSessionController.testQ + file);

            Properties prop = new Properties();


            // load a properties file
            prop.load(input);
            if(tname.equals(prop.getProperty("Name"))) {
            	dup = true;
            	randomCode = file.substring(0,7);
            }

	        }
	 	    catch(Exception e)
	 	    {
	 	    	e.printStackTrace();
	 	    }
	 	   finally {
	 		   if (input != null){try {input.close();} catch (IOException e) {e.printStackTrace();}}
	 	   }
	    }
    	return dup;
	   }
	void config(String file, String name, String date ) {
		OutputStream output = null;
        try  {
        	output = new FileOutputStream(ClassSessionController.testQ + "\\" + file + ".properties");
        	Properties prop = new Properties();
     
        	
        	
            // set the properties value
            prop.setProperty("Date", date);
            prop.setProperty("Name", name);
            prop.setProperty("Items", "0");
           // prop.setProperty("Answers", "x");

 
            

            // save properties to project root folder
            prop.store(output, null);
            //System.out.println(prop.getProperty("db.url"));

            

        } catch (IOException io) {
            io.printStackTrace();
        }
        finally {
        	if (output != null) {
	            try {
					output.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}

        }
		//boolean dup = false;
		
		FileInputStream fi = null;
		Workbook wb = null;

		//student_info = new ArrayList<String>();
		try
		{
		fi = new FileInputStream(ClassSessionController.test_excel);
		
		wb = new HSSFWorkbook(fi);
		int sheetNum = wb.getSheetIndex(name);
		if (sheetNum != -1) {
			wb.removeSheetAt(sheetNum);
			
		}
		

			Sheet sheet = wb.createSheet(name);
			Row row = sheet.createRow(0);
			Cell s_code = row.createCell(0);
			Cell s_name = row.createCell(1);
			Cell s_sex = row.createCell(2);
			Cell s_id = row.createCell(3);
			Cell s_course = row.createCell(4);
			Cell s_score = row.createCell(5);
			Cell s_ans = row.createCell(6);
			Cell s_attempt = row.createCell(7);
			Cell s_duration = row.createCell(8);
			
			s_code.setCellValue("CODE");
			s_name.setCellValue("NAME");
			s_sex.setCellValue("SEX");
			s_id.setCellValue("ID NO.");
			s_course.setCellValue("COURSE CODE/ GRADE LEVEL");		
			s_score.setCellValue("SCORE");
			s_ans.setCellValue("ANSWERS");
			s_attempt.setCellValue("ATTEMPTS");
			s_duration.setCellValue("DURATION(MIN)");
			
		
	    }

	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		finally {
			
			if (fi != null) {try {fi.close();} catch (IOException e) {e.printStackTrace();}}
	        
	        FileOutputStream out = null;
			try {
				out = new FileOutputStream(new File(ClassSessionController.test_excel));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        if (wb != null) {
	        try {           
	        	wb.write(out);
	        	wb.close();} 
	        catch (IOException e) {e.printStackTrace();}}
	        try {
	        	if (out != null) {
	        		 out.flush();
			            out.close();
	        	}
	           } 
	        catch (IOException e) {e.printStackTrace();}
		    
			
		    
		}
		Path anSheets = Paths.get(ClassSessionController.answers + file);
		try {
			Files.createDirectories(anSheets);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public boolean dateFormat(String date) {
    	boolean parsed = false;
    	DateTimeFormatter ft = DateTimeFormatter.ofPattern("MMM dd, yyyy");

   	    try {
   	    	ft.parse(date);
   	    	parsed = true;
   	    } catch (DateTimeParseException dtpe) {
   	        parsed = false;
   	    }
   	    return parsed;
    }
    public boolean isValidName(String name)
    {
        String specialCharacters="/\\:*?\"<>|";
        String str2[]=name.split("");
        int count=0;
        for (int i=0;i<str2.length;i++)
        {
            if (specialCharacters.contains(str2[i]))
            {
                count++;
            }
        }       

        if (name!=null && count==0 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	static boolean changed = false;
	void display() {

    	try {
    		new_test = new Stage();
    		
    		Parent root = FXMLLoader.load(App.class.getResource("NewTest.fxml"));
			//JFXDecorator decorator = new JFXDecorator(new_test , root, false, false, false);
			//decorator.setCustomMaximize(true); 
			//String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(root);
			//class_scene.getStylesheets().add(uri) ;
			new_test.setScene(class_scene);
			new_test.setTitle("New Test");
			new_test.initModality(Modality.APPLICATION_MODAL);
			//new_test.initStyle(StageStyle.UNDECORATED);
			new_test.setResizable(false);
			new_test.showAndWait();
			//new_test.getIcons().add(new Image(NewTestController.class.getResourceAsStream("icon.png")));
			
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//return changed;
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		test_name.setTooltip(new Tooltip("Should not contain: /\\:*?\"<>|"));
		
	}
	

}


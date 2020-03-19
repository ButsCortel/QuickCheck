package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewTestController {
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
    		if (checkString(testName) ||
					checkString(testDate) ) 
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
	public boolean checkString(String inp) {
		String filter[] = {"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
		boolean result = false;
		for(int i = 0; i < 9; i++)
		{
			if(inp.indexOf(filter[i]) != -1)
				{
					result = true;
				}
			else {
				result = false;
			}
		}
		return result;

	}
	static boolean changed = false;
	void display() {

    	try {
    		new_test = new Stage();
    		
    		Parent root = FXMLLoader.load(App.class.getResource("NewTest.fxml"));
			JFXDecorator decorator = new JFXDecorator(new_test , root, false, false, false);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(decorator);
			class_scene.getStylesheets().add(uri) ;
			new_test.setScene(class_scene);
			new_test.setTitle("New Test");
			new_test.initModality(Modality.APPLICATION_MODAL);
			new_test.initStyle(StageStyle.UNDECORATED);
			new_test.setResizable(false);
			new_test.showAndWait();
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//return changed;
		
	}
	

}


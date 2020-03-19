package com.buts.research;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class NewClassController implements Initializable{
	static Stage newclass_window;
	static String cnew ="";
	static String snew	="";
    static String folderName = "";
    static String masterList = "";
    static String attendance = "";
    static String tests = "";
    static String testQ = "";
    final int initialValue = 1;
    static ArrayList<String> sched;
    static ArrayList<String> hours;
    static ArrayList<String> minutes;
    SpinnerValueFactory<Integer> valueFactory = //
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, initialValue);
	@FXML
    private JFXTextField course_text;

    @FXML
    private JFXTextField subject_text;

    @FXML
    private JFXButton create_button;

    @FXML
    private Label loginstat;
    @FXML
    private GridPane grid;
    @FXML
    private Spinner<Integer> daysperweek;
    private TextField starth;
    private TextField startm;
    private TextField endh;
    private TextField endm;
 

    private Spinner<String> day;
    private Spinner<String> am;
    private Spinner<String> am2;
    static boolean changed = false;
    
    @FXML
    private void exit(ActionEvent event) {
    	newclass_window.close();
    }
    
	    void openNew() {
	    	try {
	    		newclass_window = new Stage();
	    		
	    		Parent root = FXMLLoader.load(App.class.getResource("newclass.fxml"));
				JFXDecorator decorator = new JFXDecorator(newclass_window , root, false, false, false);
				decorator.setCustomMaximize(true); 
				String uri = App.class.getResource("CSS.css").toExternalForm();
				Scene class_scene = new Scene(decorator, 430, 350);
				class_scene.getStylesheets().add(uri) ;
				newclass_window.setScene(class_scene);
				newclass_window.setTitle("New Class");
				newclass_window.initModality(Modality.APPLICATION_MODAL);
				newclass_window.initStyle(StageStyle.UNDECORATED);
				newclass_window.setResizable(false);
				newclass_window.showAndWait();

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    @FXML
	    private void newClass() {
	    	String course = course_text.getText();
			String subject = subject_text.getText();
	    	if (course.equals("") || 
	    			subject.equals("") ) 
	    			{
	    				if (checkString(course) ||
	    					checkString(subject) ) 
	    				{
	    					loginstat.setText("Special Characters are not allowed.");        			
	    				}
	    				else 
	    				{
	    					loginstat.setText("Please don't leave blank field/s.");
	    				}
	        			
	    			} 
	    	else if(Character.isWhitespace(course.charAt(0)) || 
	    			Character.isWhitespace(subject.charAt(0))) 
	    			{
						if (checkString(course) ||
							checkString(subject) ) 
						{
							loginstat.setText("Special Characters are not allowed.");        			
						}
						else 
						{
							loginstat.setText("All fields should not start with space/s.");
						}
	    			    	     		    	
	    			}
	    	else if(checkString(course) ||
					checkString(subject) ) 
				{
					loginstat.setText("Special Characters are not allowed.");        			
				}
	    	else if (!schedString()) {
	    		loginstat.setText("Invalid Time Input.");
	    	}
	    	else {
	    		String c = course.trim();
	    		String s = subject.trim();
	    		cnew = c.replaceAll("\\s+", "_");
	    		snew = s.replaceAll("\\s+", "_");
	            folderName = App.fullp + cnew + " " + snew;
	            masterList = App.fullp + cnew + " " + snew + "\\Students.xls";
	            attendance = App.fullp + cnew + " " + snew + "\\Attendance.xls";
	            tests = App.fullp + cnew + " " + snew + "\\Tests.xls";
	            testQ = App.fullp + cnew + " " + snew + "\\TestConfig\\";
	            Path path = Paths.get(folderName);
	            Path testPath = Paths.get(testQ);
	            if (Files.exists(path)) {
	            	AlertBoxController.label_text = "Overwrite Existing Class?";
	            	if(AlertBoxController.display("Existing Class!")) {
	            		try {
	            			changed = true;
							AlertBoxController.alert_box.hide();
							FileUtils.cleanDirectory( new File(path.toString()));
							Files.createDirectories(path);
							attendanceWorkbook();
		        			studentWorkbook();
		        			testWorkbook();
		        			//schedString();
		        			config();
		        			Files.createDirectories(testPath);
							AlertBoxController.alert_box.close();
							newclass_window.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}

	        		
	            	else {
	            		changed = false;
	            		AlertBoxController.alert_box.close();
	            	}
	            }
	            else {
	                try {
	                	changed = true;
	        			Files.createDirectories(path);
	        			attendanceWorkbook();
	        			studentWorkbook();
	        			testWorkbook();
	        			Files.createDirectories(testPath);
	        			//schedString();
	        			config();
	        			newclass_window.close();
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            }
	            
	    	}

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

		public  void studentWorkbook() throws IOException {
	        FileOutputStream fos = null;
	        Workbook  workbook = null; 
		    try {
		        fos = new FileOutputStream(new File(masterList));
		        workbook = new HSSFWorkbook();            

		        Sheet sheet = workbook.createSheet(cnew + "-" + snew);  

		        Row row = sheet.createRow(0); 
		        
		        Cell cell0 = row.createCell(0);
		        cell0.setCellValue("CODE");

		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("NAME");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("SEX");
		        
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("ID NO.");
		        
		        Cell cell4 = row.createCell(4);
		        cell4.setCellValue("COURSE CODE/ GRADE LEVEL");  

		       // XSSFCell cell2 = row.createCell(2);
		       // cell2.setCellValue("Percent Change");


		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    finally {
		    	if (workbook != null) {
		    		try {
				        workbook.write(fos);
				        workbook.close();
		    		}
		    		catch (IOException e) {
		    			e.printStackTrace();
		    			
		    		}
		    	}
		    	if (fos != null) {
		    		try {
				        fos.flush();
				        fos.close();
		    		}
		    		catch (IOException e) {
		    			e.printStackTrace();
		    			
		    		}
		    	}


		    }
		}
		public  void attendanceWorkbook() throws IOException {
	        FileOutputStream fos = null;
	        Workbook  workbook = null; 
		    try {
		        fos = new FileOutputStream(new File(attendance));
		        workbook = new HSSFWorkbook();            

		        /*Sheet sheet = workbook.createSheet();  
		        Row row = sheet.createRow(0); 
		        
		        Cell cell0 = row.createCell(0);
		        cell0.setCellValue("CODE");
		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("NAME");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("ID NO.");
		        
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("COURSE CODE/ GRADE LEVEL");*/

		       // XSSFCell cell2 = row.createCell(2);
		       // cell2.setCellValue("Percent Change");

		       
		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    finally {
		    	if (workbook != null) {
		    		try {
				    	 workbook.write(fos);
					        workbook.close();
		    		}
		    		catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    	}
		    	if (fos != null) {
		    		try {
				        fos.flush();
				        fos.close();
		    		}
		    		catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    	}


		    }
		}
		public  void testWorkbook() throws IOException {
	        FileOutputStream fos = null;
	        Workbook  workbook = null; 
		    try {
		        fos = new FileOutputStream(new File(tests));
		        workbook = new HSSFWorkbook();            

		       /* Sheet sheet = workbook.createSheet(cnew + "-" + snew);  

		        Row row = sheet.createRow(0); 
		        
		        Cell cell0 = row.createCell(0);
		        cell0.setCellValue("CODE");

		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("NAME");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("SEX");
		        
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("ID NO.");
		        
		        Cell cell4 = row.createCell(4);
		        cell4.setCellValue("COURSE CODE/ GRADE LEVEL"); */  

		       // XSSFCell cell2 = row.createCell(2);
		       // cell2.setCellValue("Percent Change");


		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    finally {
		    	if (workbook != null) {
		    		try {
				    	 workbook.write(fos);
					        workbook.close();
		    		}
		    		catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    	}
		    	if (fos != null) {
		    		try {
				        fos.flush();
				        fos.close();
		    		}
		    		catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    	}
		    }
		}
		void config() {
			OutputStream output = null;
	        try  {
	        	output = new FileOutputStream(folderName + "\\config.properties");
	        	Properties prop = new Properties();
	     
	        	
	        	
	            // set the properties value
	            prop.setProperty("Course", cnew);
	            prop.setProperty("Subject", snew);
	            int i =0;
	            for (String s: sc) {
	            	prop.setProperty("Schedule" + i, s);
	            	i++;
	            }
	            

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
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			daysperweek.setValueFactory(valueFactory);
			schedDisplay();
		}
		List<TextField> textFields;
		List<Spinner<String>> meridianspin;
		List<Spinner<String>> dayspin;
		public void schedDisplay() {
			textFields = new ArrayList<TextField>();
			meridianspin = new ArrayList<Spinner<String>>();
			dayspin = new ArrayList<Spinner<String>>();
			grid.getChildren().clear();
			Integer days = (Integer) daysperweek.getValue();
			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes.add(null);
			nodes.add(null);
			nodes.add(null);
			nodes.add(null);
			nodes.add(null);
			nodes.add(null);
			nodes.add(null);
			nodes.add(null);
			int r, c = 0;
			for (r = 0; r< days; r++) {
				for (c = 0; c< 8 ; c++) {

				Label to = new Label("TO");
				to.setTextFill(Color.BLACK);
			
			
				starth = new TextField("1");
				startm = new TextField("00");
				endh = new TextField("1");
				endm = new TextField("00");

		        day = new Spinner<String>();
		        am = new Spinner<String>();
		        am2 = new Spinner<String>();
		        

		        
		        ObservableList<String> daystring = FXCollections.observableArrayList(//
		                "Sun", "Mon", "Tue", "Wed", //
		                "Thu", "Fri", "Sat");
		        ObservableList<String> amstring = FXCollections.observableArrayList(//
		                "AM", "PM");
		        ObservableList<String> amstring2 = FXCollections.observableArrayList(//
		                "AM", "PM");
		  

		  
		        // Value factory.
		        SpinnerValueFactory<String> valueFactoryDays = //
		                new SpinnerValueFactory.ListSpinnerValueFactory<String>(daystring);
		        SpinnerValueFactory<String> valueFactoryam = //
		                new SpinnerValueFactory.ListSpinnerValueFactory<String>(amstring);
		        SpinnerValueFactory<String> valueFactoryam2 = //
		                new SpinnerValueFactory.ListSpinnerValueFactory<String>(amstring2);
		       
		        // Default value
		        valueFactoryDays.setValue("Mon");
		        valueFactoryam.setValue("AM");
		        valueFactoryam2.setValue("AM");
		  
switch (c) {
		        
		        case 0:
		        day.setUserData("day" + c);
		        dayspin.add(day);
		        break;
		        
		        case 1:
		        starth.setUserData("starth" +c);
		        textFields.add(starth);
		        break;
		        
		        case 2:
		        startm.setUserData("startm" +c);
		        textFields.add(startm);
		        break;
		        
		        case 3:
		        am.setUserData("am" +c);
		        meridianspin.add(am);
		        break;
		        
		        case 4:
		        break;

		        case 5:
		        endh.setUserData("endh" + c);
		        textFields.add(endh);
		        break;
		        
		        case 6:
			    endm.setUserData("endm"+c);
			    textFields.add(endm);   
		        break;
		        
		        case 7:
		        am2.setUserData(c);
		        meridianspin.add(am2);    
		        break;
		        }
		        day.setValueFactory(valueFactoryDays);
		        am.setValueFactory(valueFactoryam);
		        am2.setValueFactory(valueFactoryam2);
		        
		        
		        
		        


		        
		        
				//day.setFont(new Font("Arial Black",12));
				//start.setFont(new Font("Arial Black",12));
				//am.setFont(new Font("Arial Black",12));
				to.setFont(new Font("Arial Black",12));
				//end.setFont(new Font("Arial Black",12));
				//am2.setFont(new Font("Arial Black",12));
				day.setMinHeight(30);
				starth.setMinHeight(30);
				startm.setMinHeight(30);
				am.setMinHeight(30);
				to.setMinHeight(30);
				endh.setMinHeight(30);
				endm.setMinHeight(30);
				am2.setMinHeight(30);

				day.setMaxHeight(30);
				starth.setMaxHeight(30);
				startm.setMaxHeight(30);
				am.setMaxHeight(30);
				to.setMaxHeight(30);
				endh.setMaxHeight(30);
				endm.setMaxHeight(30);
				am2.setMaxHeight(30);
				
			
				
				
				nodes.set(0, day);
				nodes.set(1, starth);
				nodes.set(2, startm);
				nodes.set(3, am);
				nodes.set(4, to);
				nodes.set(5, endh);
				nodes.set(6, endm);
				nodes.set(7, am2);


				/*grid.add(day, 0, r);
				grid.add(start, 1, r);
				grid.add(am, 2, r);
				grid.add(to, 3, r);
				grid.add(end, 4, r);
				grid.add(am2, 5, r);*/
				grid.add(nodes.get(c), c, r);
			} c = 0;
			}
		}
		public boolean schedString() {
			sc.clear();
	    	sched = new ArrayList<String>();
	    	Spinner<String> days = new Spinner<String>();
	    	TextField timesh = new TextField();
	    	TextField timesm = new TextField();
	    	Spinner<String> meridian = new Spinner<String>();
	    	int r = dayspin.size();
	    	int c = 0; //txtfields
			int d = 0; // meridians 

	    	boolean result = false;
	    	
	    	for (int tempRow = 0; tempRow < r; tempRow++)
	    	{
	    		days = dayspin.get(tempRow);
	    		sched.add(days.getValue() + " ");
    			int a = 0; // meridians dash
	    		for (int tempCol = 0; tempCol < 2; tempCol++)
	    		{
	    			timesh = textFields.get(c);
	    			timesm = textFields.get(c+1);
	    			meridian = meridianspin.get(d);

	    			

	    			if (isValidHr(timesh.getText()) &&
	    	    		isValidMn(timesm.getText())) 
	    				{
		    			String newh = timesh.getText();
		    			String newm = timesm.getText();
	    				if (newh.length() < 2) {
	    					newh = "0" + newh;
	    				}
	    				if (newm.length() < 2) {
	    					newm = "0" + newm;
	    				}
		    			sched.add(newh + ":");
		    			sched.add(newm + " ");
		    			//System.out.println(timesh.getText() + timesm.getText() +meridian.getValue());

		    			if (a == 0) { 
		    				sched.add(meridian.getValue() + " - ");
		    			}
		    			
		    			else {
		    				sched.add(meridian.getValue() );
		    			}
		    			
		    			c+=2;
		    			d++;
		    			result = true;
	    			}
	    			else {
	    				result = false;
	    				sched.clear();
	    				break;
	    			} 
	
	    			a++;
	    		}
	    		
	    	}
	    	String b = "";
	    	int x = 0;
	    	for (String s: sched) {
	    		b += s;
	    		if (x == 6) {
	    			sc.add(b);
	    			b="";
	    			x = 0;
	    		}
	    		else
	    		x++;
	    	}
	    	return result;
		}
		static ArrayList<String> sc = new ArrayList<String>();
		public static boolean isValidHr(String token) {
		    try{
		        int num = Integer.parseInt(token);
		        if(num < 13)
		            return true;

		        else
		        	return false;
		    } catch (NumberFormatException ex) {
		        return false;
		    }
		}
		public static boolean isValidMn(String token) {
		    try{
		        int num = Integer.parseInt(token);
		        if (num < 45)
		            return true; 
		        else
		        	return false;
		    } catch (NumberFormatException ex) {
		        return false;
		    }
		}

		
}

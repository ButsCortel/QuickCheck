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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
    final int initialValue = 1;
    static ArrayList<String> sched;
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
    private Spinner<Integer> start;
    private Spinner<Integer> end;

    private Spinner<String> day;
    private Spinner<String> am;
    private Spinner<String> am2;
    
	    void openNew() {
	    	try {
	    		newclass_window = new Stage();
	    		
	    		Parent root = FXMLLoader.load(App.class.getResource("newclass.fxml"));
				JFXDecorator decorator = new JFXDecorator(newclass_window , root, false, false, false);
				decorator.setCustomMaximize(true); 
				String uri = App.class.getResource("CSS.css").toExternalForm();
				Scene class_scene = new Scene(decorator, 360, 350);
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
	    	else {
	    		String c = course.trim();
	    		String s = subject.trim();
	    		cnew = c.replaceAll("\\s+", "_");
	    		snew = s.replaceAll("\\s+", "_");
	            folderName = App.fullp + cnew + " " + snew;
	            masterList = App.fullp + cnew + " " + snew + "\\Students.xls";
	            attendance = App.fullp + cnew + " " + snew + "\\Attendance.xls";
	            tests = App.fullp + cnew + " " + snew + "\\Tests.xls";
	            Path path = Paths.get(folderName);
	            if (Files.exists(path)) {
	            	AlertBoxController.label_text = "Overwrite Existing Class?";
	            	if(AlertBoxController.display("Existing Class!")) {
	            		try {
							AlertBoxController.alert_box.hide();
							FileUtils.cleanDirectory( new File(path.toString()));
							Files.createDirectories(path);
							attendanceWorkbook();
		        			studentWorkbook();
		        			testWorkbook();
		        			schedString();
		        			config();
							AlertBoxController.alert_box.close();
							newclass_window.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	            	}

	        		
	            	else {
	            		AlertBoxController.alert_box.close();
	            	}
	            }
	            else {
	                try {
	        			Files.createDirectories(path);
	        			attendanceWorkbook();
	        			studentWorkbook();
	        			testWorkbook();
	        			schedString();
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
		    try {
		        FileOutputStream fos = new FileOutputStream(new File(masterList));
		        Workbook  workbook = new HSSFWorkbook();            

		        Sheet sheet = workbook.createSheet(cnew + "-" + snew);  

		        Row row = sheet.createRow(0); 
		        
		        Cell cell0 = row.createCell(0);
		        cell0.setCellValue("CODE");

		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("NAME");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("ID NO.");
		        
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("COURSE CODE/ GRADE LEVEL");

		       // XSSFCell cell2 = row.createCell(2);
		       // cell2.setCellValue("Percent Change");

		        workbook.write(fos);
		        workbook.close();
		        fos.flush();
		        fos.close();
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		}
		public  void attendanceWorkbook() throws IOException {
		    try {
		        FileOutputStream fos = new FileOutputStream(new File(attendance));
		        Workbook  workbook = new HSSFWorkbook();            

		        Sheet sheet = workbook.createSheet(cnew + "-" + snew);  

		        Row row = sheet.createRow(0); 
		        
		        Cell cell0 = row.createCell(0);
		        cell0.setCellValue("CODE");

		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("NAME");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("ID NO.");
		        
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("COURSE CODE/ GRADE LEVEL");

		       // XSSFCell cell2 = row.createCell(2);
		       // cell2.setCellValue("Percent Change");

		        workbook.write(fos);
		        workbook.close();
		        fos.flush();
		        fos.close();
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		}
		public  void testWorkbook() throws IOException {
		    try {
		        FileOutputStream fos = new FileOutputStream(new File(tests));
		        Workbook  workbook = new HSSFWorkbook();            

		        Sheet sheet = workbook.createSheet(cnew + "-" + snew);  

		        Row row = sheet.createRow(0); 
		        
		        Cell cell0 = row.createCell(0);
		        cell0.setCellValue("CODE");

		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("NAME");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("ID NO.");
		        
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("COURSE CODE/ GRADE LEVEL");   

		       // XSSFCell cell2 = row.createCell(2);
		       // cell2.setCellValue("Percent Change");

		        workbook.write(fos);
		        workbook.close();
		        fos.flush();
		        fos.close();
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		}
		void config() {

	        try (OutputStream output = new FileOutputStream(folderName + "\\config.properties")) {
	        	int five = 0;
	        	int three = 0;
	        	int l = sched.size();
	        	Properties prop = new Properties();
	        	String schnew = "";
	        	for (String a: sched) {
	        		
    				schnew += a;
    				if (five == 0) {
    					schnew = schnew + ":";
    				}
    				
    				schnew = schnew + " ";
    				five++;
    				three++;
    				if (five == 5 && l > 5) {
    					schnew = schnew + "\n";
    					five =0;
    				}
    				if (three == 3) {
    					schnew = schnew + "- ";
    				}
    				if (three ==5) {
    					three =0;
    					
    				}
    					
    					
    				
	        		
	        	}
	        	schnew = schnew.trim();
	        	if(schnew.endsWith("/"))
	        	{
	        		schnew = schnew.substring(0,schnew.length() - 1);
	        	}
	        	
	            // set the properties value
	            prop.setProperty("Course", cnew);
	            prop.setProperty("Subject", snew);
	            prop.setProperty("Schedule", schnew);

	            // save properties to project root folder
	            prop.store(output, null);
	            //System.out.println(prop.getProperty("db.url"));

	        } catch (IOException io) {
	            io.printStackTrace();
	        }

	    }
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			daysperweek.setValueFactory(valueFactory);
			schedDisplay();
		}
		List<Spinner<Integer>> intspin;
		List<Spinner<String>> meridianspin;
		List<Spinner<String>> dayspin;
		public void schedDisplay() {
			intspin = new ArrayList<Spinner<Integer>>();
			meridianspin = new ArrayList<Spinner<String>>();
			dayspin = new ArrayList<Spinner<String>>();
			grid.getChildren().clear();
			Integer days = (Integer) daysperweek.getValue();
			ArrayList<Node> nodes = new ArrayList<Node>();
			int r, c = 0;
			for (r = 0; r< days; r++) {
				for (c = 0; c< 6 ; c++) {

				Label to = new Label("TO");
				to.setTextFill(Color.WHITE);
			
			
		        start = new Spinner<Integer>();
		        end = new Spinner<Integer>();

		        day = new Spinner<String>();
		        am = new Spinner<String>();
		        am2 = new Spinner<String>();
		        
		        final int initialValue = 1;
		        SpinnerValueFactory<Integer> valueFactoryStart = //
		                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, initialValue);
		        SpinnerValueFactory<Integer> valueFactoryEnd = //
		                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, initialValue);
		        start.setValueFactory(valueFactoryStart);
		        end.setValueFactory(valueFactoryEnd);
		        
		        ObservableList<String> daystring = FXCollections.observableArrayList(//
		                "SUN", "MON", "TUE", "WED", //
		                "THU", "FRI", "SAT");
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
		        valueFactoryDays.setValue("MON");
		        valueFactoryam.setValue("AM");
		        valueFactoryam2.setValue("AM");
		  
		        day.setValueFactory(valueFactoryDays);
		        am.setValueFactory(valueFactoryam);
		        am2.setValueFactory(valueFactoryam2);
		        
		        switch (c) {
		        
		        case 0:
		        day.setUserData(c);
		        dayspin.add(day);
		        break;
		        
		        case 1:
		        start.setUserData(c);
		        intspin.add(start);
		        break;
		        
		        case 2:
		        am.setUserData(c);
		        meridianspin.add(am);
		        break;

		        case 4:
		        end.setUserData(c);
		        intspin.add(end);
		        break;
		        
		        case 5:
		        am2.setUserData(c);
		        meridianspin.add(am2);    
		        break;
		        }
		        
		        


		        
		        
				//day.setFont(new Font("Arial Black",12));
				//start.setFont(new Font("Arial Black",12));
				//am.setFont(new Font("Arial Black",12));
				to.setFont(new Font("Arial Black",12));
				//end.setFont(new Font("Arial Black",12));
				//am2.setFont(new Font("Arial Black",12));
				day.setMinHeight(30);
				start.setMinHeight(30);
				am.setMinHeight(30);
				to.setMinHeight(30);
				end.setMinHeight(30);
				am2.setMinHeight(30);
				day.setMaxHeight(30);
				start.setMaxHeight(30);
				am.setMaxHeight(30);
				to.setMaxHeight(30);
				end.setMaxHeight(30);
				am2.setMaxHeight(30);
				nodes.add(0, day);
				nodes.add(1, start);
				nodes.add(2, am);
				nodes.add(3, to);
				nodes.add(4, end);
				nodes.add(5, am2);


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
		public void schedString() {
	    	sched = new ArrayList<String>();
	    	Spinner<String> days = new Spinner<String>();
	    	Spinner<Integer> times = new Spinner<Integer>();
	    	Spinner<String> meridian = new Spinner<String>();
	    	int r = dayspin.size();
	    	int c = 0;

	    	for (int tempRow = 0; tempRow < r; tempRow++)
	    	{
	    		days = dayspin.get(tempRow);
	    		sched.add(days.getValue());
	    		for (int tempCol = 0; tempCol < 2; tempCol++)
	    		{
	    			times = intspin.get(c);
	    			sched.add(times.getValue().toString());
	    			meridian = meridianspin.get(c);
	    			sched.add(meridian.getValue());
	    			c++;
	    		}
	    	}
		}
		
}

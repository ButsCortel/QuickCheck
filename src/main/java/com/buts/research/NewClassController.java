package com.buts.research;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NewClassController {
	static Stage newclass_window;
	static String cnew ="";
	static String snew	="";
	static String pnew	="";
    static String folderName = "";
    static String masterList = "";
    static String attendance = "";
    static String tests = "";
	@FXML
    private JFXTextField course_text;

    @FXML
    private JFXTextField prof_text;

    @FXML
    private JFXTextField subject_text;

    @FXML
    private JFXButton create_button;

    @FXML
    private Label loginstat;
	   
	    void openNew() {
	    	try {
	    		newclass_window = new Stage();
	    		
	    		Parent root = FXMLLoader.load(App.class.getResource("newclass.fxml"));
				JFXDecorator decorator = new JFXDecorator(newclass_window , root, false, false, false);
				decorator.setCustomMaximize(true); 
				String uri = App.class.getResource("CSS.css").toExternalForm();
				Scene class_scene = new Scene(decorator, 400, 270);
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
			String prof = prof_text.getText();
	    	if (course.equals("") || 
	    			subject.equals("") ||
	    			prof.equals("")) 
	    			{
	    				if (checkString(course) ||
	    					checkString(subject) ||
	    					checkString(prof)) 
	    				{
	    					loginstat.setText("Special Characters are not allowed.");        			
	    				}
	    				else 
	    				{
	    					loginstat.setText("Please don't leave blank field/s.");
	    				}
	        			
	    			} 
	    	else if(Character.isWhitespace(course.charAt(0)) || 
	    			Character.isWhitespace(subject.charAt(0)) ||
	    			Character.isWhitespace(prof.charAt(0))) 
	    			{
						if (checkString(course) ||
							checkString(subject) ||
							checkString(prof)) 
						{
							loginstat.setText("Special Characters are not allowed.");        			
						}
						else 
						{
							loginstat.setText("All fields should not start with space/s.");
						}
	    			    	     		    	
	    			}
	    	else if(checkString(course) ||
					checkString(subject) ||
					checkString(prof)) 
				{
					loginstat.setText("Special Characters are not allowed.");        			
				}
	    	else {
	    		String c = course.trim();
	    		String s = subject.trim();
	    		String p = prof.trim();
	    		cnew = c.replaceAll("\\s+", "_");
	    		snew = s.replaceAll("\\s+", "_");
	    		pnew = p.replaceAll("\\s+", "_");
	            folderName = "C:/QuickCheck/Files/Classes/" + cnew + " " + snew + " " +pnew;
	            masterList = "C:/QuickCheck/Files/Classes/" + cnew + " " + snew + " " +pnew + "/Students.xlsx";
	            attendance = "C:/QuickCheck/Files/Classes/" + cnew + " " + snew + " " +pnew + "/Attendance.xlsx";
	            tests = "C:/QuickCheck/Files/Classes/" + cnew + " " + snew + " " +pnew + "/Tests.xlsx";
	            Path path = Paths.get(folderName);
	            Path masterpath = Paths.get(masterList);
	            Path attpath = Paths.get(attendance);
	            Path testpath = Paths.get(tests);
	            if (Files.exists(path)) {
	        		Stage window = new Stage();		
	        		window.initModality(Modality.APPLICATION_MODAL);
	        		window.initStyle(StageStyle.UTILITY);
	        		window.setTitle("File exists!");
	        		window.setMinWidth(250);
	        		
	        		Label label = new Label();
	        		label.setText("Overwrite existing file?");
	        		Button confirm = new Button("Yes");
	        		confirm.setOnAction(e -> {
						try {
							window.hide();
							FileUtils.cleanDirectory( new File(path.toString()));
							Files.createDirectories(path);
							createWorkbook();
							window.close();
							newclass_window.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
	        		Button unconfirm = new Button("No");
	        		unconfirm.setOnAction(e -> window.close());
	        		
	        		VBox layout = new VBox(10);
	        		layout.getChildren().addAll(label, confirm, unconfirm);
	        		layout.setAlignment(Pos.CENTER);
	        		
	        		Scene scene = new Scene(layout);
	        		window.setScene(scene);
	        		window.showAndWait();
	            }
	            else {
	                try {
	        			Files.createDirectories(path);
	        			createWorkbook();
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

		public  void createWorkbook() throws IOException {
		    try {
		        FileOutputStream fos = new FileOutputStream(new File(attendance));
		        XSSFWorkbook  workbook = new XSSFWorkbook();            

		        XSSFSheet sheet = workbook.createSheet(cnew + "-" + snew);  

		        XSSFRow row = sheet.createRow(0);   
		        XSSFCell cell0 = row.createCell(0);
		        cell0.setCellValue("Student Number");

		        XSSFCell cell1 = row.createCell(1);

		        cell1.setCellValue("Name");       

		       // XSSFCell cell2 = row.createCell(2);
		       // cell2.setCellValue("Percent Change");

		        workbook.write(fos);
		        fos.flush();
		        fos.close();
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		}
}

package com.buts.research;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
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

public class NewClassController {
	static Stage newclass_window;
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
	    		String cnew = course.replaceAll("\\s+", "_");
	    		String snew = subject.replaceAll("\\s+", "_");
	    		String pnew = prof.replaceAll("\\s+", "_");
	            String folderName = "C:/QuickCheck/Files/Classes/" + cnew + " " + snew + " " +pnew;
	            String masterList = "C:/QuickCheck/Files/Classes/" + cnew + " " + snew + " " +pnew + "/Students.xlsx";
	            String attendance = "C:/QuickCheck/Files/Classes/" + cnew + " " + snew + " " +pnew + "/Attendance.xlsx";
	            String tests = "C:/QuickCheck/Files/Classes/" + cnew + " " + snew + " " +pnew + "/Tests.xlsx";
	            Path path = Paths.get(folderName);
	            Path masterpath = Paths.get(masterList);
	            Path attpath = Paths.get(attendance);
	            Path testpath = Paths.get(tests);
	            if (Files.exists(path)) {
	        		Stage window = new Stage();		
	        		window.initModality(Modality.APPLICATION_MODAL);
	        		window.setTitle("File exists!");
	        		window.setMinWidth(250);
	        		
	        		Label label = new Label();
	        		label.setText("Overwrite existing file?");
	        		Button confirm = new Button("Yes");
	        		confirm.setOnAction(e -> {
						try {
							Files.createDirectories(path);
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
	        			Files.createFile(masterpath);
	        			Files.createFile(attpath);
	        			Files.createFile(testpath);
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
}

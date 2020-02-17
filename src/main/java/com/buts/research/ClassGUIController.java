package com.buts.research;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClassGUIController implements Initializable{
	static Stage newclass_window;
	static ArrayList<Path> classes = new ArrayList<>();

    @FXML
    private JFXButton select_class_next;


    @FXML
    private GridPane gridpane;
    
    @FXML
    void openNew(ActionEvent event) throws IOException {
    	NewClassController newclass = new NewClassController();
    	newclass.openNew();
    	checkClasses();
    }
	public void classGUIWindow() {
    	try {
    		Stage classgui_window = new Stage();
    		Parent root = FXMLLoader.load(App.class.getResource("ClassGUI.fxml"));
			JFXDecorator decorator = new JFXDecorator(classgui_window , root, false, false, true);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(decorator, 740, 460);
			class_scene.getStylesheets().add(uri) ;
			classgui_window.setScene(class_scene);
			classgui_window.setTitle("CLASS!");
			classgui_window.initStyle(StageStyle.UNDECORATED);
			classgui_window.setResizable(false);

		
			classgui_window.show();
			
	
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public  void checkClasses() throws IOException {
		classes.clear();
		 String dirName = "C:\\QuickCheck\\Files\\Classes";
			File file = new File(dirName);
			
			if(file.isDirectory()){
					
				if(file.list().length>0){
						
			        Files.list(new File(dirName).toPath())
	                .limit(100)
	                .forEach(path -> {
	                    classes.add(path);
	                    //Collections.sort(classes);
	                });
			        dispClasses();
			        
						
				}
			}


	    }
	public void dispClasses() {
	  	gridpane.getChildren().clear();
		int row = 0;
		ArrayList<Label> classes_display = new ArrayList<>();
		String[] classes_string = {null, null, null};
		classes_display.add(null);
		classes_display.add(null);
		classes_display.add(null);
		for(Path className: classes) {
				String name = className.toString().replace("C:\\QuickCheck\\Files\\Classes\\", "");
				classes_string = name.split("\\s+");
				String course = classes_string[0].replaceAll("_", " ");
				String subject = classes_string[1].replaceAll("_", " ");
				String prof = classes_string[2].replaceAll("_", " ");
				Label coursel = new Label(course);
				Label subjl = new Label(subject);
				Label profl = new Label(prof);			
				classes_display.set(0,coursel);
				classes_display.set(1,subjl);
				classes_display.set(2,profl);
				gridpane.add(classes_display.get(0), 0, row);
				gridpane.add(classes_display.get(1), 1, row);
				gridpane.add(classes_display.get(2), 2, row);
				row++;
				}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			checkClasses();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.Node;

public class ClassGUIController implements Initializable{
	static ArrayList<Path> classes = new ArrayList<>();
	static int rowIndex = 0;
	Rectangle rect;
	int dispclasses2 = 0;
	boolean class_selected = false;
	static Stage classgui_window;


    @FXML
    private GridPane gridpane;
    
    @FXML
    void openNew() throws IOException {
    	NewClassController newclass = new NewClassController();
    	newclass.openNew();
    	class_selected = false;
    	checkClasses();
    }
	public void classGUIWindow() {
    	try {
    		classgui_window = new Stage();
    		Parent root = FXMLLoader.load(App.class.getResource("ClassGUI.fxml"));
			JFXDecorator decorator = new JFXDecorator(classgui_window , root, false, false, true);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(decorator, 740, 460);
			class_scene.getStylesheets().add(uri) ;
			classgui_window.setScene(class_scene);
			classgui_window.setTitle("QuickCheck");
			classgui_window.initStyle(StageStyle.UNDECORATED);
			classgui_window.setResizable(false);
			classgui_window.show();
			
	
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public  void checkClasses() throws IOException {
	  	gridpane.getChildren().clear();
		classes.clear();
		 //String dirName = "C:\\QuickCheck\\Files\\Classes";
			File file = new File(App.fullp);
			
			if(file.isDirectory()){
					
				if(file.list().length>0){
						
			        Files.list(new File(App.fullp).toPath())
	                .limit(100)
	                .forEach(path -> {
	                    classes.add(path);
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
		classes_display.add(null);
		for(Path className: classes) {
				String name = className.toString().replace(App.fullp, "");
				classes_string = name.split("\\s+");
				String course = classes_string[0].replaceAll("_", " ");
				String subject = classes_string[1].replaceAll("_", " ");
				String sched = loadSched(className.toString());
				Label coursel = new Label(course);
				Label subjl = new Label(subject);
				Label schedl = new Label(sched);
				schedl.setWrapText(true);
				Label rows = new Label(Integer.toString(row +1)+".");
				coursel.setFont(new Font("Arial Black",15));
				subjl.setFont(new Font("Arial black",15));
				schedl.setFont(new Font("Arial black",15));
				rows.setFont(new Font("Arial black",15));
				coursel.setTextFill(Color.WHITE);
				subjl.setTextFill(Color.WHITE);
				schedl.setTextFill(Color.WHITE);
				rows.setTextFill(Color.WHITE);


				schedl.setMaxHeight(Control.USE_PREF_SIZE);
				schedl.setMinHeight(Control.USE_PREF_SIZE);
				schedl.setMinWidth(200);
				schedl.setMaxWidth(200);
				schedl.setMaxWidth(200);
				schedl.setAlignment(Pos.CENTER_LEFT);
				
				coursel.setMaxHeight(Double.MAX_VALUE);
				coursel.setMinHeight(schedl.getPrefHeight());
				coursel.setMinWidth(200);
				coursel.setMaxWidth(200);
				coursel.setAlignment(Pos.CENTER);
				
				subjl.setMaxHeight(Double.MAX_VALUE);
				subjl.setMinHeight(schedl.getPrefHeight());
				subjl.setMinWidth(200);
				subjl.setMaxWidth(200);
				subjl.setAlignment(Pos.CENTER);			
				
				rows.setMaxHeight(Double.MAX_VALUE);											
				rows.setMinHeight(schedl.getPrefHeight());												
				rows.setMaxWidth(100);
				rows.setMinWidth(100);
				rows.setAlignment(Pos.CENTER);
		        


				
				
				classes_display.set(0,rows);
				classes_display.set(1,coursel);
				classes_display.set(2,subjl);
				classes_display.set(3,schedl);
				if (dispclasses2 == 0){
					gridpane.add(classes_display.get(0), 0, row);
					gridpane.add(classes_display.get(1), 1, row);
					gridpane.add(classes_display.get(2), 2, row);
					gridpane.add(classes_display.get(3), 3, row);
					}
				else {
					if(row != rowIndex) {
					gridpane.add(classes_display.get(0), 0, row);
					gridpane.add(classes_display.get(1), 1, row);
					gridpane.add(classes_display.get(2), 2, row);
					gridpane.add(classes_display.get(3), 3, row);
					}
					else {
						coursel.setStyle("-fx-background-color: tomato;");
						subjl.setStyle("-fx-background-color: tomato;");
						schedl.setStyle("-fx-background-color: tomato;");
						rows.setStyle("-fx-background-color: tomato;");
						gridpane.add(classes_display.get(0), 0, rowIndex);
						gridpane.add(classes_display.get(1), 1, rowIndex);
						gridpane.add(classes_display.get(2), 2, rowIndex);
						gridpane.add(classes_display.get(3), 3, rowIndex);
						dispclasses2 =0;
					}}
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
    @FXML
    public void clickGrid(MouseEvent event) {
    Node clickedNode = event.getPickResult().getIntersectedNode();

    if (clickedNode != gridpane) {
    	Node parent = clickedNode.getParent();
        while (parent != gridpane) {
            clickedNode = parent;
            parent = clickedNode.getParent();
        }
        rowIndex = GridPane.getRowIndex(clickedNode);
        class_selected = true;
        dispclasses2 = 1;
        dispClasses();
        
    	}

    }
    public void deleteDirectory(Path directoryFilePath) throws IOException
    {
        Path directory = directoryFilePath;

        if (Files.exists(directory))
        {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
                {
                    Files.delete(path);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException
                {
                    Files.delete(directory);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
    @FXML
    void deleteClass(ActionEvent event) throws IOException {
    	if (class_selected) {
        	AlertBoxController.label_text = "Delete this class?";
        	if(AlertBoxController.display("Delete Class!")) {
        		deleteDirectory(classes.get(rowIndex));
        		checkClasses();
        		class_selected = false;
        	}

    		
        	else {
        		AlertBoxController.alert_box.close();
        	}
    		

    	}
    	
    }
    String loadSched(String pathname) {
    	String sched = "";
        try (InputStream input = new FileInputStream(pathname + "\\config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            sched = prop.getProperty("Schedule");


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sched;
    }
   public void classNext() {
    	if (class_selected) {
    		classgui_window.close();
    		String[] classes_string;
    		
    		String path = classes.get(rowIndex).toString().replace(App.fullp, "");
    		classes_string = path.split("\\s+");
    		
    		

    		ClassSessionController.path = classes.get(rowIndex).toString() + "\\Students.xls";
    		ClassSessionController.course = classes_string[0];
    		ClassSessionController.subject = classes_string[1];
    		ClassSessionController start = new ClassSessionController();

    		start.startClass();

    		
    	}
    	
    }
}

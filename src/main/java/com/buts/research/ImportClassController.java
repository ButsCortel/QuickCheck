package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.jfoenix.controls.JFXDecorator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImportClassController implements Initializable {

	static Stage importclass_window;
	static int dispclasses2 = 0;
	static int rowIndex = 0;
	static boolean student_selected = false;
	ArrayList<Path> classes = null;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private GridPane gridpane;

    @FXML
    void import_list(ActionEvent event) {
    	if (student_selected) {
    		try {
				implist();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    	student_selected = false;
    	importclass_window.close();
    	}

    }

	public void importList() {
		try {
			importclass_window = new Stage();
			
			Parent root = FXMLLoader.load(App.class.getResource("ImportClass.fxml"));
			JFXDecorator decorator = new JFXDecorator(importclass_window , root, false, false, false);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(decorator);
			class_scene.getStylesheets().add(uri) ;
			importclass_window.setScene(class_scene);
			importclass_window.setTitle("Import Student List");
			importclass_window.initModality(Modality.APPLICATION_MODAL);
			importclass_window.initStyle(StageStyle.UNDECORATED);
			importclass_window.setResizable(false);
			importclass_window.showAndWait();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		display_classes();
		
	}
	public void display_classes() {
	  	gridpane.getChildren().clear();
		int row =0;
		 //String dirName = "C:\\QuickCheck\\Files\\Classes";
		
						String[] classes_string = {null, null};
						for(Path className: classes) {
							
							
								
							String name = className.toString().replace(App.fullp, "");
							classes_string = name.split("\\s+");
							String course = classes_string[0].replaceAll("_", " ");
							String subject = classes_string[1].replaceAll("_", " ");
							Label coursel = new Label(course);
							Label subjl = new Label(subject);

							coursel.setFont(new Font("Arial Black",15));
							subjl.setFont(new Font("Arial black",15));

							coursel.setTextFill(Color.BLACK);
							subjl.setTextFill(Color.BLACK);

							subjl.setMaxHeight(Double.MAX_VALUE);
							subjl.setMinHeight(30);
							subjl.setMinWidth(120);
							subjl.setMaxWidth(120);
							subjl.setAlignment(Pos.CENTER);
							
							coursel.setMaxHeight(Double.MAX_VALUE);
							coursel.setMinHeight(subjl.getPrefHeight());
							coursel.setMinWidth(115);
							coursel.setMaxWidth(115);
							coursel.setAlignment(Pos.CENTER);
							
			
							
						
	
							gridpane.add(coursel, 0, row);
							gridpane.add(subjl, 1, row);

							row++;
							}					
								
								
						

			       
			        
						

	}
    public void clickGrid(MouseEvent event) throws IOException {
        Node clickedNode = event.getPickResult().getIntersectedNode();

        if (clickedNode != gridpane) {
        	Node parent = clickedNode.getParent();
            while (parent != gridpane) {
                clickedNode = parent;
                parent = clickedNode.getParent();
            }
            rowIndex = GridPane.getRowIndex(clickedNode);
            for (Node node : gridpane.getChildren()) {
            	if (GridPane.getRowIndex(node) == rowIndex) {
            		node.setStyle("-fx-background-color: 	#D3D3D3;");
            	}
            	else {
            		node.setStyle("-fx-background-color: white;");
            	}
            }student_selected = true;
            //System.out.println(classes.get(rowIndex));
        	}
        

    }
	public void implist() throws IOException {
	       FileSystem system = FileSystems.getDefault();
	        Path original = system.getPath(classes.get(rowIndex).toString() + "\\Students.xls");
	        Path target = system.getPath(ClassSessionController.student_excel);

	        try {
	            // Throws an exception if the original file is not found.
	            Files.copy(original, target, StandardCopyOption.REPLACE_EXISTING);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	}

	public  void checkClasses() throws IOException {
		classes = new ArrayList<Path>();
		

		 //String dirName = "C:\\QuickCheck\\Files\\Classes";
			File file = new File(App.fullp);
			
			if(file.isDirectory()){
					
				if(file.list().length>0){
						
			        Files.list(new File(App.fullp).toPath())
	                .limit(100)
	                .forEach(path -> {
	                	if (!path.toString().equals(ClassSessionController.path)) {
	                		FileInputStream myxls = null;
	                		Workbook studentsSheet = null;
							try {
								myxls = new FileInputStream(path.toString() + "\\Students.xls");
								studentsSheet = new HSSFWorkbook(myxls);
			         		       Sheet worksheet = studentsSheet.getSheetAt(0);
			         		       if (worksheet.getLastRowNum() > 0) {
			         		    	  classes.add(path); 
			         		       }
			         		       studentsSheet.close();
			         		      myxls.close();
			                		
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
	         		       

	                	}
	                    
	                });

			        
						
				}
			}


	    }

}

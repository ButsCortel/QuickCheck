package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
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
			}
    	}
    	student_selected = false;
    	importclass_window.close();
    }

	public void importList() {
		try {
			importclass_window = new Stage();
			
			Parent root = FXMLLoader.load(App.class.getResource("ImportClass.fxml"));
			JFXDecorator decorator = new JFXDecorator(importclass_window , root, false, false, false);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(decorator, 280, 230);
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
		display_classes();
		
	}
	public void display_classes() {
	  	gridpane.getChildren().clear();
		int row =0;
		 //String dirName = "C:\\QuickCheck\\Files\\Classes";
		
						ArrayList<Label> classes_display = new ArrayList<>();
						String[] classes_string = {null, null};
						classes_display.add(null);
						classes_display.add(null);
						for(Path className: ClassGUIController.classes) {
							String name = className.toString().replace(App.fullp, "");
							classes_string = name.split("\\s+");
							String course = classes_string[0].replaceAll("_", " ");
							String subject = classes_string[1].replaceAll("_", " ");
							Label coursel = new Label(course);
							Label subjl = new Label(subject);

							coursel.setFont(new Font("Arial Black",15));
							subjl.setFont(new Font("Arial black",15));

							coursel.setTextFill(Color.WHITE);
							subjl.setTextFill(Color.WHITE);

							subjl.setMaxHeight(Double.MAX_VALUE);
							subjl.setMinHeight(30);
							subjl.setMinWidth(115);
							subjl.setMaxWidth(115);
							subjl.setAlignment(Pos.CENTER);
							
							coursel.setMaxHeight(Double.MAX_VALUE);
							coursel.setMinHeight(subjl.getPrefHeight());
							coursel.setMinWidth(115);
							coursel.setMaxWidth(115);
							coursel.setAlignment(Pos.CENTER);
							
			
							
						
							classes_display.set(0,coursel);
							classes_display.set(1,subjl);
							if (dispclasses2 == 0){
								gridpane.add(classes_display.get(0), 0, row);
								gridpane.add(classes_display.get(1), 1, row);

								}
							else {
								if(row != rowIndex) {
								gridpane.add(classes_display.get(0), 0, row);
								gridpane.add(classes_display.get(1), 1, row);

								}
								else {
									coursel.setStyle("-fx-background-color: tomato;");
									subjl.setStyle("-fx-background-color: tomato;");
									gridpane.add(classes_display.get(0), 0, rowIndex);
									gridpane.add(classes_display.get(1), 1, rowIndex);
									dispclasses2 =0;
								}}
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
        dispclasses2 = 1;
        display_classes();
        student_selected = true;
    	}
    

    }
	public void implist() throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(ClassGUIController.classes.get(rowIndex).toString() + "\\Students.xls");
	        os = new FileOutputStream(ClassSessionController.path);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}

}

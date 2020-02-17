package com.buts.research;

import java.io.IOException;

import com.jfoenix.controls.JFXDecorator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AttendanceGUIController {
	public static void attendanceGUIWindow() {
    	try {
    		Stage attendancegui_window = new Stage();
    		Parent root = FXMLLoader.load(App.class.getResource("AttendanceGUI.fxml"));
			JFXDecorator decorator = new JFXDecorator(attendancegui_window , root, false, false, true);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene att_scene = new Scene(decorator, 740, 455);
			att_scene.getStylesheets().add(uri) ;
			attendancegui_window.setScene(att_scene);
			attendancegui_window.setTitle("CLASS!");
			attendancegui_window.initStyle(StageStyle.UNDECORATED);
			attendancegui_window.setResizable(false);
			
			attendancegui_window.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		double x,y =0;
		@FXML
	    void dragged(MouseEvent event) {
	    	Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
	    	stage.setX(event.getScreenX() -x);
	    	stage.setY(event.getScreenY() -y);
	    }

	    @FXML
	    void pressed(MouseEvent event) {
	    	x = event.getSceneX();
	    	y = event.getSceneY();

	    }

}

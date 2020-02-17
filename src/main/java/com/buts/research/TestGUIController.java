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

public class TestGUIController {
	public static void testGUIWindow() {
    	try {
    		Stage testgui_window = new Stage();
    		Parent root = FXMLLoader.load(App.class.getResource("TestGUI.fxml"));
			JFXDecorator decorator = new JFXDecorator(testgui_window , root, false, false, true);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene test_scene = new Scene(decorator, 740, 455);
			test_scene.getStylesheets().add(uri) ;
			testgui_window.setScene(test_scene);
			testgui_window.setTitle("CLASS!");
			testgui_window.initStyle(StageStyle.UNDECORATED);
			testgui_window.setResizable(false);
			
			testgui_window.show();
			
			
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

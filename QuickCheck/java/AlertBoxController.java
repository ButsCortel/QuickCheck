package com.buts.research;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBoxController implements Initializable {
	static boolean result = false;
	static Stage alert_box;
	static String label_text;
    @FXML
    private Label alert_label;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;


	
	public static boolean display(String title) {

    	try {
    		alert_box = new Stage();
    		
    		Parent root = FXMLLoader.load(App.class.getResource("Alert.fxml"));
			//JFXDecorator decorator = new JFXDecorator(alert_box , root, false, false, false);
			//decorator.setCustomMaximize(true); 
			//String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(root);
			//class_scene.getStylesheets().add(uri) ;
			alert_box.setScene(class_scene);
			alert_box.setTitle(title);
			alert_box.initModality(Modality.APPLICATION_MODAL);
			//alert_box.initStyle(StageStyle.UNDECORATED);
			alert_box.setResizable(false);
			alert_box.showAndWait();
			//alert_box.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		alert_label.setText(label_text);
		confirm.setOnAction(e -> {
			result = false;
			alert_box.close();
		});
		cancel.setOnAction(e -> {
			result = true;
			alert_box.close();
		});
		
	}
}

package com.buts.research;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LoginController {

	//String passcode;
    @FXML
    private Button login_button; 
    //@FXML
    //private JFXPasswordField passcode_field;

   // @FXML
    //private Label pass_check;
    @FXML
    private void switchToPrimary() throws IOException {
    	//passcode = passcode_field.getText();
    	//if (passcode.equals("4114")) {
        	
        	ClassGUIController startClass = new ClassGUIController();
        	startClass.classGUIWindow();
        	App.window.close();
    	
    	//}else {
    		//pass_check.setText("Invalid Passcode!");
    		//pass_check.isWrapText();
    	//}

    }
    @FXML
    void hover(MouseEvent event) {
    	login_button.setStyle("-fx-background-color: tomato; ");
    }

    @FXML
    void hoverout(MouseEvent event) {
    	login_button.setStyle("-fx-background-color: firebrick; ");
    }

}

package com.buts.research;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoginController {

	String passcode;
    @FXML
    private Button login_button;

    @FXML
    private JFXPasswordField passcode_field;

    @FXML
    private Label pass_check;
    @FXML
    private void switchToPrimary() throws IOException {
    	passcode = passcode_field.getText();
    	if (passcode.equals("4114")) {
        	App.window.close();
        	ClassGUIController startClass = new ClassGUIController();
        	startClass.classGUIWindow();
    	}
    	else {
    		pass_check.setText("Invalid Passcode!");
    		pass_check.isWrapText();
    	}

    }

}

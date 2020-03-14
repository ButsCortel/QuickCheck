package com.buts.research;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class LoginController {

	//String passcode;
    @FXML
    private Button login_button; 
    //@FXML
    //private JFXPasswordField passcode_field;

   // @FXML
    //private Label pass_check;

    @FXML
    private AnchorPane loginPane;
    @FXML
    private void switchToPrimary() throws IOException {

    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), loginPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0);
    	fadeOutTransition.play();
    	loginPane.setDisable(true);
    	
    	fadeOutTransition.setOnFinished((e) -> {
        	ClassGUIController startClass = new ClassGUIController();
        	startClass.classGUIWindow();
    	});
    	//passcode = passcode_field.getText();
    	//if (passcode.equals("4114")) {
        	

        	//SplashController.stage.close();
    	
    	//}else {
    		//pass_check.setText("Invalid Passcode!");
    		//pass_check.isWrapText();
    	//}

    }
    @FXML
    void hover(MouseEvent event) {
    	login_button.setStyle("-fx-background-color: tomato; -fx-border-color: white; ");
    }

    @FXML
    void hoverout(MouseEvent event) {
    	login_button.setStyle("-fx-background-color: firebrick; -fx-border-color: white;");
    }

}

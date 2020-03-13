package com.buts.research;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class SplashController implements Initializable{
    @FXML
    private AnchorPane anchorpane;
    static Stage stage = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new SplashScreen().start();
		
	}
	class SplashScreen extends Thread {
		@Override
		public void run() {
			 try {
				Thread.sleep(5000);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("Login.fxml"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	
				    	//stage.getIcons().setAll(new Image("http://icons.iconarchive.com/icons/tooschee/misc/128/Present-icon.png"));


				    	stage = new Stage();
				    	
				    	//stage.getIcons().setAll(new Image("http://icons.iconarchive.com/icons/tooschee/misc/128/Present-icon.png"));
				        //JFXDecorator decorator = new JFXDecorator(stage , root, false, false, true);
				        
				        //decorator.setCustomMaximize(true);   
				        //String uri = App.class.getResource("CSS.css").toExternalForm();
				        Scene scene = new Scene(root);
				        //scene.getStylesheets().add(uri) ;
				        stage.setTitle("QuickCheck"); 

				        stage.setResizable(false);
				        //stage.getIcons().setAll(new Image(App.class.getResourceAsStream("icon.png")));
				        //stage.getIcons().add(new Image("C:\\Users\\Ana\\eclipse-workspace2\\research\\src\\main\\resources\\com\\buts\\research\\icon.png"));
				        /*Image icon = new Image(getClass().getResourceAsStream("icon.png"));
				        Image icon1 = new Image(getClass().getResourceAsStream("icon1.png"));
				        stage.getIcons().addAll(icon, icon1);*/		
				        stage.setScene(scene);
						stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
						    @Override
						    public void handle(WindowEvent t) {
					        	AlertBoxController.label_text = "Are you sure you want to exit?";
					        	if(AlertBoxController.display("Exit")) {
							        Platform.exit();
							        System.exit(0);
					        	}
					        	else {
					        		t.consume();
					        	}

						    }
						});

				        //stage.initStyle(StageStyle.UNDECORATED);
				        stage.setResizable(false);
				       				        
				        stage.show();
				        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
				        anchorpane.getScene().getWindow().hide();
					}
				});
				

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

package com.buts.research;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jfoenix.controls.JFXDecorator;

/**
 * JavaFX App
 */
public class App extends Application{
	static String fileName;
	static String fullp;
	static Stage window;
	static String textName;
    @Override
    public void start(Stage stage) throws IOException {
    	window = stage;
    	Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        JFXDecorator decorator = new JFXDecorator(stage , root, false, false, true);
        decorator.setCustomMaximize(true);   
        String uri = App.class.getResource("CSS.css").toExternalForm();
        Scene scene = new Scene(decorator, 740, 455);
        scene.getStylesheets().add(uri) ;
        
        stage.setScene(scene);
        stage.setTitle("QuickCheck"); 
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
       
        stage.show();
    }
    
    
    



    public static void main(String[] args) throws IOException {
    	fileName = "\\QuickCheck-workspace\\Files\\Classes\\";
    	textName = "\\QuickCheck-workspace\\Files\\";
    	String sys = System.getProperty("user.home");
    	fullp =  sys + fileName;
        Path path1 = Paths.get(fullp);
        Path path2 = Paths.get("C:"+ fileName);
        String data = "Student Codes";
        if (Files.exists(path1)) {

        }
        else {
            try {
            	fullp = sys+ fileName;
    			Files.createDirectories(path1);
    			
    			Files.write(Paths.get(sys + textName + "codes.txt"), data.getBytes());
    		} catch (IOException e) {
    			fullp = "C:" + fileName;
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Files.createDirectories(path2);
    			Files.write(Paths.get("C:"+textName + "codes.txt"), data.getBytes());
    		}
        }


        launch();
 
        
    }
    





    

}
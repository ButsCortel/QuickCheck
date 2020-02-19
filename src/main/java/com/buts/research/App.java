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

import javax.swing.filechooser.FileSystemView;

import com.jfoenix.controls.JFXDecorator;

/**
 * JavaFX App
 */
public class App extends Application{
	static String fileName;
	static String fullp;
	static Stage window;
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
        stage.setTitle("CLASS!"); 
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
       
        stage.show();
    }
    
    
    



    public static void main(String[] args) throws IOException {
    	fileName = "\\QuickCheck-workspace\\Files\\Classes\\";
    	fullp = System.getProperty("user.home") + fileName;
        Path path1 = Paths.get(fullp);
        Path path2 = Paths.get("C:"+ fileName);
        if (Files.exists(path1)) {

        }
        else {
            try {
    			Files.createDirectories(path1);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Files.createDirectories(path2);
    		}
        }


        launch();
 
        
    }
    





    

}
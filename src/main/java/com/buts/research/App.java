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
    
    
    



    public static void main(String[] args) {
        String fileName = "C:/QuickCheck/Files/Classes";
        Path path = Paths.get(fileName);
        if (Files.exists(path)) {

        }
        else {
            try {
    			Files.createDirectories(path);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }


        launch();
 
        
    }
    





    

}
package com.buts.research;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.Dimension;
import java.awt.Toolkit;
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
    	Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));
    	

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);
		
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));



        
        
        Dimension d= Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
        stage.show(); //show stage because you wouldn't be able to get Height & width of the stage
        stage.setX(d.width/2-(stage.getWidth()/2));
        stage.setY(d.height/2-(stage.getHeight()/2));
        

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
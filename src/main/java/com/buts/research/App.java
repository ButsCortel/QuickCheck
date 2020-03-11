package com.buts.research;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    	
    	//stage.getIcons().setAll(new Image("http://icons.iconarchive.com/icons/tooschee/misc/128/Present-icon.png"));
        JFXDecorator decorator = new JFXDecorator(stage , root, false, false, true);
        
        decorator.setCustomMaximize(true);   
        String uri = App.class.getResource("CSS.css").toExternalForm();
        Scene scene = new Scene(decorator, 740, 455);
        scene.getStylesheets().add(uri) ;
        stage.setTitle("QuickCheck"); 

        stage.initStyle(StageStyle.TRANSPARENT);
        //stage.getIcons().setAll(new Image(App.class.getResourceAsStream("icon.png")));
        //stage.getIcons().add(new Image("C:\\Users\\Ana\\eclipse-workspace2\\research\\src\\main\\resources\\com\\buts\\research\\icon.png"));
        /*Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        Image icon1 = new Image(getClass().getResourceAsStream("icon1.png"));
        stage.getIcons().addAll(icon, icon1);*/		
        stage.setScene(scene);

        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
       
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
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
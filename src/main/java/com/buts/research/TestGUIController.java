package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TestGUIController implements Initializable {


    @FXML
    private GridPane gridpane;

    @FXML
    private JFXTextField searchB;
    @FXML
    private AnchorPane testPane;

    
    @FXML
    void back(ActionEvent event) {
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), testPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0.5);
    	fadeOutTransition.play();
    	testPane.setDisable(true);
    	
    	fadeOutTransition.setOnFinished((e) -> {
        	ClassGUIController startClass = new ClassGUIController();
        	startClass.classGUIWindow();
    	});
    	

    }

    @FXML
    void deleteTest(ActionEvent event) {

    }
 

    @FXML
    void home(MouseEvent event) {
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), testPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0.5);
    	fadeOutTransition.play();
    	testPane.setDisable(true);
    	
    	fadeOutTransition.setOnFinished((e) -> {
        	ClassGUIController startClass = new ClassGUIController();
        	startClass.classGUIWindow();
    	});
    }

    @FXML
    void newTest(ActionEvent event) {
    	NewTestController test = new NewTestController();
    	test.display();
    	loadTests();
    }

    @FXML
    void openTest(ActionEvent event) {

    }

    @FXML
    void searchClass() {
    	//gridpane.setAlignment(Pos.TOP_CENTER);
    	ArrayList<Integer> indices = new ArrayList<Integer>();
        for (Node node : gridpane.getChildren()) {
        	node.setStyle(null);
        	if(containsIgnoreCase(((Labeled) node).getText(), searchB.getText())) {
 
        		indices.add(GridPane.getRowIndex(node));
        	}
        	
        }
        for (Node node : gridpane.getChildren()) {
        	//double h = ((Region) node).getHeight();
        	if (indices.contains(GridPane.getRowIndex(node))) {
           		//System.out.println(GridPane.getRowIndex(node) + " "+((Labeled) node).getText() );
        		
        		node.setVisible(true);
           		node.setManaged(true);
        		
        	}
        	else {
        		
        		node.setVisible(false);
        		node.setManaged(false);
        	}

        }
        //student_selected = false;

    }
    public boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }
	public void testGUIWindow() {
    	try {
    		Parent root = FXMLLoader.load(App.class.getResource("TestGUI.fxml"));
			Scene test_scene = new Scene(root);
			SplashController.stage.setScene(test_scene);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		testPane.setOpacity(0);
    	testPane.setDisable(true);
		FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(300), testPane);
    	fadeOutTransition.setFromValue(0);
    	fadeOutTransition.setToValue(1);
    	fadeOutTransition.play();
    	initClock();
    	
    	fadeOutTransition.setOnFinished((e) -> {
    		loadTests();
    		
    		testPane.setDisable(false);
            
    	});



		
	}
	ArrayList<String> testCodes = null;
	ArrayList<String> testNames = null;
	ArrayList<String> testDates = null;
	String[] test_codes = null;
	
	void loadTests() {
		testCodes = new ArrayList<String>();
		testNames = new ArrayList<String>();
		testDates = new ArrayList<String>();
		gridpane.getChildren().clear();
		File testDir = new File(App.testp);
		test_codes = testDir.list();
    	for (String file: test_codes) {
    		InputStream input = null;
 	   		try
 	   			{
     	  	input = new FileInputStream(App.testp + file);

            Properties prop = new Properties();


            // load a properties file
            prop.load(input);
            testCodes.add(file.substring(0,7));
            testNames.add(prop.getProperty("Name"));
            testDates.add(prop.getProperty("Date"));

	        }
	 	    catch(Exception e)
	 	    {
	 	    	e.printStackTrace();
	 	    }
	 	   finally {
	 		   if (input != null){try {input.close();} catch (IOException e) {e.printStackTrace();}}
	 	   }
	    }
    	for (int testNum = 0; testNum < testCodes.size(); testNum++) {
    		
    		Label codes = new Label(testCodes.get(testNum));
    		Label names = new Label(testNames.get(testNum));
    		Label dates = new Label(testDates.get(testNum));
    		Label rows = new Label(Integer.toString(testNum+1) +".");
    		
    		rows.setFont(new Font("Arial Black",15));
			codes.setFont(new Font("Arial black",15));
			names.setFont(new Font("Arial black",15));
			dates.setFont(new Font("Arial black",15));
			rows.setTextFill(Color.BLACK);
			codes.setTextFill(Color.BLACK);
			names.setTextFill(Color.BLACK);
			dates.setTextFill(Color.BLACK);
			
			names.setPadding(new Insets(0,20,0,0));

			rows.setMaxHeight(30);
			rows.setMinHeight(30);
			rows.setMinWidth(50);
			rows.setMaxWidth(50);
			rows.setAlignment(Pos.CENTER);
			
			codes.setMaxHeight(30);
			codes.setMinHeight(30);
			codes.setMinWidth(150);
			codes.setMaxWidth(150);
			codes.setAlignment(Pos.CENTER_LEFT);
			
			names.setMaxHeight(30);
			names.setMinHeight(30);
			names.setMinWidth(260);
			names.setMaxWidth(260);
			names.setAlignment(Pos.CENTER_LEFT);			
			
			dates.setMaxHeight(30);											
			dates.setMinHeight(30);												
			dates.setMaxWidth(200);
			dates.setMinWidth(200);
			dates.setAlignment(Pos.CENTER_LEFT);
			
			gridpane.add(rows, 0, testNum);
			gridpane.add(codes, 1, testNum);
			gridpane.add(names, 2, testNum);
			gridpane.add(dates, 3, testNum);
    	}
	}
    @FXML
    private Label date_label;

    @FXML
    private Label time_label;
	private void initClock() {
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy\nEEE");
		

        //SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        // DateFormat = new SimpleDateFormat("MMM dd, yyyy EEE");
        //Date myDateObj = new Date();


        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
            LocalTime currentTime = LocalTime.now();
    		LocalDateTime myDateObj = LocalDateTime.now();
            date_label.setText(dateFormat.format(myDateObj));
           // 
            if (currentTime.getHour() > 11) {
            	time_label.setText(timeFormat.format(currentTime) + " PM");
            }
            else {
            	time_label.setText(timeFormat.format(currentTime) + " AM");
            }
        }),
             new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
	}
	static int rowIndex = 0;
	 @FXML
	    public void clickGrid(MouseEvent event) {
	    	if (event.isStillSincePress()) {
	        Node clickedNode = event.getPickResult().getIntersectedNode();
	        
	        if (clickedNode != gridpane) {
	        	Node parent = clickedNode.getParent();
	            while (parent != gridpane) {
	                clickedNode = parent;
	                parent = clickedNode.getParent();
	            }
	            rowIndex = GridPane.getRowIndex(clickedNode);
	            for (Node node : gridpane.getChildren()) {
	            	if (GridPane.getRowIndex(node) == rowIndex) {
	            		node.setStyle("-fx-background-color: #D3D3D3;");
	            	}
	            	else {
	            		node.setStyle(null);
	            	}
	            	
	            }//class_selected = true;
	        	}
	    	}
	        

	    }

}

package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TestGUIController implements Initializable {
    @FXML
    private JFXButton open_button;

    @FXML
    private JFXButton new_button;

    @FXML
    private JFXButton delete_button;
    
    @FXML
    private JFXButton back0;
    
    @FXML
    private JFXButton back1;


    @FXML
    private Pane select_test_pane;

    @FXML
    private Label course_label;

    @FXML
    private Label subject_label;
    @FXML
    private GridPane gridpane;
    @FXML
    private GridPane open_gridpane;

    @FXML
    private JFXTextField searchB;
    @FXML
    private AnchorPane testPane;

    boolean test_selected = false;
    static int mode = 0;
    @FXML
    void back(ActionEvent event) {
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), testPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0.5);
    	fadeOutTransition.play();
    	testPane.setDisable(true);
    	
    	fadeOutTransition.setOnFinished((e) -> {
    		if (mode == 0) {
        		ClassSessionController start = new ClassSessionController();
                start.startClass();
    		}
    		else if (mode == 1){
            	AttendanceGUIController start = new AttendanceGUIController(); 	
            
            	start.attendanceGUIWindow(); 
    		}


    	});
    	

    }

    @FXML
    void deleteTest(ActionEvent event) {
    	if (test_selected) {
    		File file = new File(ClassSessionController.testQ + test_codes[rowIndex]);

        	AlertBoxController.label_text = "Delete Test?";
        	if(AlertBoxController.display("Delete Test")) {
        		file.delete();
        		loadTests();
        		
        	}
        	else {
        		AlertBoxController.alert_box.close();
        		
        	}

    	}

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
    	System.out.println(NewTestController.changed);
    	if (NewTestController.changed) {
    		loadTests();
    	}
    	
    }


    @FXML
    void openTest(ActionEvent event) {
    	if (test_selected) {
    		loadSelectedTest();
    	}
    	
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
		course_label.setText(ClassSessionController.course.replaceAll("_", " "));
		subject_label.setText(ClassSessionController.subject.replaceAll("_", " "));
		testPane.setOpacity(0);
    	testPane.setDisable(true);
		FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(300), testPane);
    	fadeOutTransition.setFromValue(0);
    	fadeOutTransition.setToValue(1);
    	fadeOutTransition.play();
    	initClock();
		 back1.setVisible(false);
    	
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
		
		test_selected = false;
		testCodes = new ArrayList<String>();
		testNames = new ArrayList<String>();
		testDates = new ArrayList<String>();
		gridpane.getChildren().clear();
		File testDir = new File(ClassSessionController.testQ);
		test_codes = testDir.list();
    	for (String file: test_codes) {
    		InputStream input = null;
 	   		try
 	   			{
     	  	input = new FileInputStream(ClassSessionController.testQ + file);

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
    		String name = testNames.get(testNum);
    		Label codes = new Label(testCodes.get(testNum));
    		Label names = new Label(name);
    		Label dates = new Label(testDates.get(testNum));
    		Label rows = new Label(Integer.toString(testNum+1) +".");
    		
    		rows.setFont(new Font("Arial Black",15));
			codes.setFont(new Font("Arial black",15));
			names.setFont(new Font("Arial black",15));
			names.setTooltip(new Tooltip(name));
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
	int rowIndex = 0;
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
	            //System.out.println(test_codes[rowIndex]);
	            for (Node node : gridpane.getChildren()) {
	            	if (GridPane.getRowIndex(node) == rowIndex) {
	            		node.setStyle("-fx-background-color: #D3D3D3;");
	            	}
	            	else {
	            		node.setStyle(null);
	            	}
	            	
	            }test_selected = true;
	        	}
	    	}
	        

	    }
	    @FXML
	    private Label code_label;

	    @FXML
	    private Label name_label;

	    @FXML
	    private Label date_labelt;
	    @FXML
	    private Spinner<Integer> item_spinner;
	    


	    @FXML
	    private JFXButton check_button;



	    @FXML
	    private JFXButton records_button;
	    
	    @FXML
	    private Pane test_settings_pane;
	    
	   static String answerKey ="";
	   static String cd = "";
	 void loadSelectedTest() 
	 	{
		 edit_button.setText("Edit");
		 back0.setVisible(false);
		 back1.setVisible(true);
		 select_test_pane.setVisible(false);
		 new_button.setVisible(false);
		 open_button.setVisible(false);
		 delete_button.setVisible(false);
 		open_gridpane.setDisable(true);
 		item_spinner.setDisable(true);
		 
		 InputStream input = null;
		 try
 			{
			 cd = test_codes[rowIndex];
			 input = new FileInputStream(ClassSessionController.testQ + cd);
			 //open_gridpane.setDisable(true);
			 Properties prop = new Properties();
			 prop.load(input);
			 code_label.setText(cd.substring(0,7));
			 name_label.setText(prop.getProperty("Name"));
			 date_labelt.setText(prop.getProperty("Date"));
			 answerKey = prop.getProperty("Answers");
			 final int initialValue = Integer.parseInt(prop.get("Items").toString());
			 SpinnerValueFactory<Integer> valueFactory = //
		                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, initialValue);
			 item_spinner.setValueFactory(valueFactory);
			 loadItems();
			 item_spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
				addItems();
				 
	    		    
 		        //display_att();
 		    
 		});
     
			 check_button.setVisible(true);
			 records_button.setVisible(true);
			 test_settings_pane.setVisible(true);

 			}
	    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
	   finally {
		   		if (input != null){try {input.close();} catch (IOException e) {e.printStackTrace();}}
	   		}
 
	 }
	 ArrayList<ToggleGroup> radio_values = null;
	 void loadItems() {
		 radio_values = new ArrayList<ToggleGroup>();		 
		 open_gridpane.getChildren().clear();
		 for(int rows = 0; rows < item_spinner.getValue(); rows++) {
			 Label no = new Label(Integer.toString(rows +1) + ".");
			 ToggleGroup tg = new ToggleGroup();
			 RadioButton a = new RadioButton("A");					
			 RadioButton b = new RadioButton("B"); 
			 RadioButton c = new RadioButton("C");	
			 RadioButton d = new RadioButton("D"); 
			 RadioButton e = new RadioButton("E");
			 radio_values.add(tg);
			 
	    	no.setFont(new Font("Arial Black",15));
			a.setFont(new Font("Arial black",15));
			b.setFont(new Font("Arial black",15));
			c.setFont(new Font("Arial black",15));
			d.setFont(new Font("Arial black",15));
			e.setFont(new Font("Arial black",15));
			no.setTextFill(Color.BLACK);
			a.setTextFill(Color.BLACK);
			b.setTextFill(Color.BLACK);
			c.setTextFill(Color.BLACK);
			d.setTextFill(Color.BLACK);
			e.setTextFill(Color.BLACK);
			 
			 no.setAlignment(Pos.CENTER);
			 a.setAlignment(Pos.CENTER);
			 b.setAlignment(Pos.CENTER);
			 c.setAlignment(Pos.CENTER);
			 d.setAlignment(Pos.CENTER);
			 e.setAlignment(Pos.CENTER);
			 
			 a.setToggleGroup(tg);
			 b.setToggleGroup(tg);
			 c.setToggleGroup(tg);
			 d.setToggleGroup(tg);
			 e.setToggleGroup(tg);
			 if (answerKey != null) {
				 String[] answers = answerKey.split("\\s+");
				 switch(answers[rows]) {
				 
				 case "A":
	
					 a.setSelected(true);
					 break;
				 case "B":

					 b.setSelected(true);
					 break;
				 case "C":
					 c.setSelected(true);
					 break;
				 case "D":	
					 d.setSelected(true);
					 break;
				 case "E":
					 e.setSelected(true);
					 break;				 
				 }
					 
			 }
			 else {
				 a.setSelected(true);
			 }
			 open_gridpane.add(no, 0, rows);
			 open_gridpane.add(a, 1, rows);
			 open_gridpane.add(b, 2, rows);
			 open_gridpane.add(c, 3, rows);
			 open_gridpane.add(d, 4, rows);
			 open_gridpane.add(e, 5, rows);
		 }
		 
	 }
	 void addItems() {
		 int rowCount = open_gridpane.getRowCount();
		 System.out.println(rowCount);
		 System.out.println(item_spinner.getValue());
		 if (rowCount < item_spinner.getValue()) {

			 Label no = new Label(Integer.toString(rowCount +1));
			 ToggleGroup tg = new ToggleGroup();
			 RadioButton a = new RadioButton("A");					
			 RadioButton b = new RadioButton("B"); 
			 RadioButton c = new RadioButton("C");	
			 RadioButton d = new RadioButton("D"); 
			 RadioButton e = new RadioButton("E");
			 radio_values.add(tg);
			 
		    	no.setFont(new Font("Arial Black",15));
				a.setFont(new Font("Arial black",15));
				b.setFont(new Font("Arial black",15));
				c.setFont(new Font("Arial black",15));
				d.setFont(new Font("Arial black",15));
				e.setFont(new Font("Arial black",15));
				no.setTextFill(Color.BLACK);
				a.setTextFill(Color.BLACK);
				b.setTextFill(Color.BLACK);
				c.setTextFill(Color.BLACK);
				d.setTextFill(Color.BLACK);
				e.setTextFill(Color.BLACK);
			 
			 no.setAlignment(Pos.CENTER);
			 a.setAlignment(Pos.CENTER);
			 b.setAlignment(Pos.CENTER);
			 c.setAlignment(Pos.CENTER);
			 d.setAlignment(Pos.CENTER);
			 e.setAlignment(Pos.CENTER);
			 
			 a.setToggleGroup(tg);
			 b.setToggleGroup(tg);
			 c.setToggleGroup(tg);
			 d.setToggleGroup(tg);
			 e.setToggleGroup(tg);

			 a.setSelected(true);
			 
			 open_gridpane.add(no, 0, rowCount);
			 open_gridpane.add(a, 1, rowCount);
			 open_gridpane.add(b, 2, rowCount);
			 open_gridpane.add(c, 3, rowCount);
			 open_gridpane.add(d, 4, rowCount);
			 open_gridpane.add(e, 5, rowCount);
		 }
		 else {
			 open_gridpane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == rowCount - 1);
			 radio_values.remove(rowCount - 1);
		 }
		 
	 }
	    @FXML
	    void openRecords(ActionEvent event) {

	    }



	    @FXML
	    void quickCheck(ActionEvent event) {

	    }
	    @FXML
	    private JFXButton edit_button;
	    @FXML
	    void edit(ActionEvent event) {
	    	String answer = "";
	    	int items = 0;
	    	if(edit_button.getText().equals("Edit")) {
	    		open_gridpane.setDisable(false);
	    		item_spinner.setDisable(false);
	    		edit_button.setText("Save");
	    	}
	    	else{
	    		
	    		for (ToggleGroup tg: radio_values) {
	    			RadioButton rb = (RadioButton) tg.getSelectedToggle();
	    			System.out.println(rb.getText());
	    			answer += rb.getText() + " ";
	    			items++;
	    		}
	    		try {
					changeProperty(ClassSessionController.testQ + cd, "Answers", answer, "Items", Integer.toString(items));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		open_gridpane.setDisable(true);
	    		item_spinner.setDisable(true);
	    		edit_button.setText("Edit");
	    	}
	    }
	    @FXML
	    void back_1() {
            for (Node node : gridpane.getChildren()) {
        		node.setStyle(null);
        	}
		 select_test_pane.setVisible(true);
		 new_button.setVisible(true);
		 open_button.setVisible(true);
		 delete_button.setVisible(true);
		 test_settings_pane.setVisible(false);
		 check_button.setVisible(false);
		 records_button.setVisible(false);
		 test_selected = false;
		 back0.setVisible(true);
		 back1.setVisible(false);
	    }
	    public static void changeProperty(String filename, String key, String value, String key2, String value2) throws FileNotFoundException, IOException  {
	    	   Properties prop =new Properties();
	    	   prop.load(new FileInputStream(filename));
	    	   prop.setProperty(key, value);
	    	   prop.setProperty(key2, value2);
	    	   prop.store(new FileOutputStream(filename),null);
	    	}

}

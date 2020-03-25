package com.buts.research;

import java.awt.List;
import java.awt.image.BufferedImage;
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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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
    		File file = new File(ClassSessionController.testQ + test_codes.get(rowIndex));
    		Path test = Paths.get(ClassSessionController.answers + test_codes.get(rowIndex).substring(0,7));

        	AlertBoxController.label_text = "Delete Test?";
        	if(AlertBoxController.display("Delete Test")) {
        		file.delete();
        		try {
					deleteDirectory(test);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		deleteSheet(testNames.get(rowIndex));
        		loadTests();
        		
        	}
        	else {
        		AlertBoxController.alert_box.close();
        		
        	}

    	}

    }
    void deleteSheet(String sheet_name) {
    	FileInputStream input = null;
    	Workbook wb = null;
    	int sheet_num = 0;
    	try {
    		input = new FileInputStream(ClassSessionController.test_excel);
    		wb = new HSSFWorkbook(input);
    		Sheet sheet = wb.getSheet(sheet_name);
    		if(sheet != null)   {
    		    sheet_num = wb.getSheetIndex(sheet);
    		    wb.removeSheetAt(sheet_num);
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		if (input != null) {
    			try {input.close();} catch(IOException e) {e.printStackTrace();}
    		}
    		FileOutputStream out = null;
    		try {
				out = new FileOutputStream(new File(ClassSessionController.test_excel));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		if (wb != null && out != null){
    			try {
    				wb.write(out);
    				wb.close();
    				} 
    			catch(IOException e) {e.printStackTrace();}
    		}
	        try {           
	            out.flush();
	            out.close();} 
	        catch (IOException e) {e.printStackTrace();}

	    }
    	}
    public void deleteDirectory(Path directoryFilePath) throws IOException
    {
        Path directory = directoryFilePath;

        if (Files.exists(directory))
        {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
                {
                    Files.delete(path);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException
                {
                    Files.delete(directory);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

 

    @FXML
    void home(MouseEvent event) {
    	disposeCamera();
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
        	try {
    			importStudents();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

    }


    @FXML
    void openTest(ActionEvent event) {
    	//edit_button.setVisible(true);
    	settings_button.setVisible(true);
    	check_button.setVisible(true);
    	records_button.setVisible(true);
    	records_button.setDisable(false);
    	settings_button.setDisable(true);
    	new_button.setVisible(false);
		 open_button.setVisible(false);
		 delete_button.setVisible(false);
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
    @FXML
    private Pane quickcheck_pane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		blockCamera.setVisible(true);
		//select_camera_first_pane.setDisable(true);
		check_name.setText("");
		check_code.setText("");
		check_student.setText("");
		check_score.setText("");
		check_status.setText("");
		items_label.setText("");
		course_label.setText(ClassSessionController.course.replaceAll("_", " "));
		subject_label.setText(ClassSessionController.subject.replaceAll("_", " "));
		testPane.setOpacity(0);
    	testPane.setDisable(true);
    	testPane.setVisible(true);
    	records_button.setVisible(false);
    	select_test_pane.setVisible(true);
    	//quickcheck_pane.setVisible(false);
		FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(300), testPane);
    	fadeOutTransition.setFromValue(0);
    	fadeOutTransition.setToValue(1);
    	fadeOutTransition.play();
    	initClock();
		 back1.setVisible(false);
    	
    	fadeOutTransition.setOnFinished((e) -> {
	    	try {
				importStudents();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		loadTests();
    		
    		testPane.setDisable(false);
            
    	});



		
	}
	ArrayList<String> testCodes = null;
	ArrayList<String> testNames = null;
	ArrayList<String> testDates = null;
	ArrayList<String> test_codes = null;
	
	void loadTests() {
		
		test_selected = false;
		testCodes = new ArrayList<String>();
		testNames = new ArrayList<String>();
		testDates = new ArrayList<String>();
		test_codes = new ArrayList<String>();
		gridpane.getChildren().clear();
		File testDir = new File(ClassSessionController.testQ);
		for (String s: testDir.list()) {
			if (s.contains(".properties")) {
				test_codes.add(s);
			}
		}
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
	   static String quiz_name = "";
	   static String quiz_date = "";
	 void loadSelectedTest() 
	 	{
		 open_gridpane.getChildren().clear();
		 //settings_button.setVisible(false);
		 edit_button.setText("Edit");
		 back0.setVisible(false);
		 back1.setVisible(true);
		 select_test_pane.setVisible(false);
		 
 		open_gridpane.setDisable(true);
 		item_spinner.setDisable(true);
		 
		 InputStream input = null;
		 
		 try
 			{
			 cd = test_codes.get(rowIndex);
			 input = new FileInputStream(ClassSessionController.testQ + cd);
			 //open_gridpane.setDisable(true);
			 Properties prop = new Properties();
			 prop.load(input);
			 String code = cd.substring(0,7);
			 code_label.setText(code);
			 test_code_label.setText(code);
			 check_code.setText(code);
			 String qname = prop.getProperty("Name");
			 check_name.setText(qname);
			 test_name_label.setText(qname);
			 quiz_name = qname;
			 name_label.setText(qname);
			 String qdate = prop.getProperty("Date");
			 quiz_date = qdate;
			 date_labelt.setText(qdate);
			 test_date_label.setText(qdate);
			 answerKey = prop.getProperty("Answers");
			 String qtems = prop.getProperty("Items");
			 items_label.setText(qtems);
			 test_items_label.setText(qtems);
			 final int initialValue = Integer.parseInt(qtems);
	    		if (initialValue > 0) {
	    			check_button.setDisable(false);
	    			records_button.setDisable(false);
	    		}
	    		else {
	    			check_button.setDisable(true);
	    			records_button.setDisable(true);
	    		}
			 SpinnerValueFactory<Integer> valueFactory = //
		                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, initialValue);
			 
			 
			 valueFactory.valueProperty().addListener((observable, oldValue, newValue) -> {
				addItems(newValue);
		});
			 item_spinner.setValueFactory(valueFactory);
			 loadItems();
			
			 check_button.setVisible(true);
			 
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
	 String[] answers;
	 int quiz_items = 0;
	 void loadItems() {
		 radio_values = new ArrayList<ToggleGroup>();		 
		 open_gridpane.getChildren().clear();
		 quiz_items = item_spinner.getValue();
		 for(int rows = 0; rows < quiz_items ; rows++) {
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
			 if (answerKey != null && answerKey.length() >0) {
				 answers = answerKey.split("\\s+");
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
				 answers = null;
			 }
			 open_gridpane.add(no, 0, rows);
			 open_gridpane.add(a, 1, rows);
			 open_gridpane.add(b, 2, rows);
			 open_gridpane.add(c, 3, rows);
			 open_gridpane.add(d, 4, rows);
			 open_gridpane.add(e, 5, rows);
		 }

	 }
	 void addItems(int spinner) {
		 quiz_items = spinner;
		 int rowCount = open_gridpane.getRowCount();
		 
		 if (rowCount < spinner) {

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
	 	private Pane test_records_pane;
	    @FXML
	    void openRecords(ActionEvent event) {
	    	stopCamera();
	    	test_records_pane.setVisible(true);
	    	
	    	test_settings_pane.setVisible(false);
	    	quickcheck_pane.setVisible(false);
	    	records_button.setDisable(true);
	    	check_button.setDisable(false);
	    	settings_button.setDisable(false);
	    	displayRecords();
	    }

	    @FXML
	    private JFXButton settings_button;

	    @FXML
	    void quickCheck(ActionEvent event) {
	    	startCamera();
			check_student.setText("");
			check_score.setText("");
			check_status.setText("");
			//edit_button.setVisible(false);
	    	
	    	try {
				importStudents();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	quickcheck_pane.setVisible(true);
	    	test_settings_pane.setVisible(false);
	    	test_records_pane.setVisible(false);
	    	settings_button.setDisable(false);
	    	check_button.setDisable(true);
	    	records_button.setDisable(false);
	    	
	    	initializeCam();
	    }
	    @FXML
	    void settings() {
	    	stopCamera();
	    	quickcheck_pane.setVisible(false);
	    	test_settings_pane.setVisible(true);
	    	test_records_pane.setVisible(false);
	    	check_button.setDisable(false);
	    	records_button.setDisable(false);
	    	settings_button.setDisable(true);
	    }
	    @FXML
	    private JFXButton edit_button;
	    @FXML
	    void edit(ActionEvent event) {
	    	String answer = "";
	    	int items = 0;
	    	String i = "";
	    	if(edit_button.getText().equals("Edit")) {
	    		check_button.setDisable(true);
	    		records_button.setDisable(true);
	    		check_button.setTooltip(new Tooltip("Please save settings first."));
	    		records_button.setTooltip(new Tooltip("Please save settings first."));
	    		open_gridpane.setDisable(false);
	    		item_spinner.setDisable(false);
	    		edit_button.setText("Save");
	    	}
	    	else{
	    		ArrayList<Integer> blank = new ArrayList<Integer>();
	    		
	    		for (int tgNum = 0; tgNum < radio_values.size(); tgNum++) {
	    			
	    			RadioButton rb = (RadioButton) radio_values.get(tgNum).getSelectedToggle();
	    			try {
		    			String a = rb.getText();

			    		answer += a + " ";
		    			
		    		

		    			
	    			}
	    			catch (Exception e) {
		    			
	    				blank.add(tgNum + 1);
	    			}


	    			
	    			items++;
	    		}
	    		answers = answer.split("\\s+");
	    		if (blank.size() == 0) {
		    		try {
		    			i = Integer.toString(items);
						changeProperty(ClassSessionController.testQ + cd, "Answers", answer, "Items", i);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		test_items_label.setText(i);
		    		open_gridpane.setDisable(true);
		    		item_spinner.setDisable(true);
		    		edit_button.setText("Edit");	    		
		    		items_label.setText(i);
		    		if (items > 0) {
		    			check_button.setDisable(false);
		    			records_button.setDisable(false);
		    		}
		    		else {
		    			check_button.setDisable(true);
		    			records_button.setDisable(true);
		    		}
	    		}
	    		else {
	    			String ans ="";
	    			for (int j =0; j< blank.size(); j++) {
	    				ans += blank.get(j) + ", ";
	    			}
	    			ans = ans.substring(0, ans.length() - 2);
	    			Alert alert = new Alert(AlertType.WARNING);
	    			alert.setTitle("Blank item");
	    			alert.setHeaderText("All items should have right answer.");
	    			alert.setContentText("Item/s: " + ans);

	    			alert.showAndWait();
	    			
	    		}

	    	}
	    }
	    @FXML
	    private Pane select_camera_first_pane;
	    @FXML
	    private Pane blockCamera;
	    @FXML
	    void back_1() {
	    	disposeCamera();
	    	select_camera_first_pane.setDisable(true);
	    	blockCamera.setVisible(true);
	    	check_name.setText("");
			check_code.setText("");
			check_student.setText("");
			check_score.setText("");
			check_status.setText("");
			items_label.setText("");
	    	quickcheck_pane.setVisible(false);
	    	test_records_pane.setVisible(false);
            for (Node node : gridpane.getChildren()) {
        		node.setStyle(null);
        	}
		 select_test_pane.setVisible(true);
		 new_button.setVisible(true);
		 open_button.setVisible(true);
		 delete_button.setVisible(true);
		 test_settings_pane.setVisible(false);
		 check_button.setVisible(false);
		 check_button.setDisable(false);
		 records_button.setVisible(false);
		 test_selected = false;
		 back0.setVisible(true);
		 back1.setVisible(false);
		 settings_button.setVisible(false);
	    }
	    public static void changeProperty(String filename, String key, String value, String key2, String value2) throws FileNotFoundException, IOException  {
	    	   Properties prop =new Properties();
	    	   prop.load(new FileInputStream(filename));
	    	   prop.setProperty(key, value);
	    	   prop.setProperty(key2, value2);
	    	   prop.store(new FileOutputStream(filename),null);
	    	}
	    //@FXML
		//Button btnStartCamera;

		//@FXML
		//Button btnStopCamera;

		//@FXML
		//Button btnDisposeCamera;

		@FXML
		ComboBox<WebCamInfo> cbCameraOptions;

		@FXML
		BorderPane bpWebCamPaneHolder;

		//@FXML
		//FlowPane fpBottomPane;

		@FXML
		ImageView imgWebCamCapturedImage;

		private class WebCamInfo {

			private String webCamName;
			private int webCamIndex;

			public String getWebCamName() {
				return webCamName;
			}

			public void setWebCamName(String webCamName) {
				this.webCamName = webCamName;
			}

			public int getWebCamIndex() {
				return webCamIndex;
			}

			public void setWebCamIndex(int webCamIndex) {
				this.webCamIndex = webCamIndex;
			}

			@Override
			public String toString() {
				return webCamName;
			}
		}

		private BufferedImage grabbedImage;
		private Webcam selWebCam = null;
		private boolean stopCamera = false;
		private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
		private boolean camOpen = false;

		private String cameraListPromptText = "Choose Camera";

		int webCamNum = 0;
		public void initializeCam() {

			if (!camOpen) {
			ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
			int webCamCounter = 0;
			for (Webcam webcam : Webcam.getWebcams()) {
				WebCamInfo webCamInfo = new WebCamInfo();
				webCamInfo.setWebCamIndex(webCamCounter);
				webCamInfo.setWebCamName(webcam.getName());
				options.add(webCamInfo);
				webCamCounter++;
			}
			cbCameraOptions.setItems(options);
			cbCameraOptions.setPromptText("Default");
			if (webCamCounter > 1) {
				cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

					@Override
					public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
						if (arg2 != null) {
							System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
							webCamNum = arg2.getWebCamIndex();
							//quickcheck_pane.setDisable(true);
							
						}
					}
				});
			}
    		else {
    			cbCameraOptions.setDisable(true);
    			start_button.setDisable(true);
    			initializeWebCam(0);
    		}

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					setImageViewSize();
				}
			});
			//initializeWebCam(webCamNum);
		}
			else {
				startCamera();
			}
			
		}

	    @FXML
	    private JFXButton start_button;
	    @FXML
	    void start_cam(ActionEvent event) {
	    	initializeWebCam(webCamNum);
	    	select_camera_first_pane.setDisable(false);
	    	
	    }
		

		protected void setImageViewSize() {

			double height = bpWebCamPaneHolder.getHeight();
			double width = bpWebCamPaneHolder.getWidth();
			/*select_camera_first_pane.setMinHeight(height);
			select_camera_first_pane.setMinWidth(width);
			select_camera_first_pane.setMaxHeight(height);
			select_camera_first_pane.setMaxWidth(width);
			*/
	
			//System.out.println(height);
			//System.out.println(width);
			imgWebCamCapturedImage.setFitHeight(height);
			imgWebCamCapturedImage.setFitWidth(width);
			imgWebCamCapturedImage.prefHeight(height);
			imgWebCamCapturedImage.prefWidth(width);
			imgWebCamCapturedImage.setPreserveRatio(true);

		}

		protected void initializeWebCam(final int webCamIndex) {
			
			camOpen = true;
			Task<Void> webCamIntilizer = new Task<Void>() {

				@Override
				protected Void call() throws Exception {

					if (selWebCam == null) {
						selWebCam = Webcam.getWebcams().get(webCamIndex);
						selWebCam.open();
					} else {
						closeCamera();
						selWebCam = Webcam.getWebcams().get(webCamIndex);
						selWebCam.open();
					}
					startWebCamStream();
					blockCamera.setVisible(false);
					return null;
				}

			};

			new Thread(webCamIntilizer).start();
			//fpBottomPane.setDisable(false);
			//btnStartCamera.setDisable(true);
			
		}
		String last = "";

		protected void startWebCamStream() {

	
			stopCamera = false;
			Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {

					while (!stopCamera) {

						try {
							if ((grabbedImage = selWebCam.getImage()) != null) {
								

								Platform.runLater(new Runnable() {

									@Override
									public void run() {
										LuminanceSource source = null;
										BinaryBitmap bitmap = null;
										
										try {
											source = new BufferedImageLuminanceSource(grabbedImage);
											bitmap = new BinaryBitmap(new HybridBinarizer(source));
											//result = null;
										}
										catch (Exception e) {
											
										}
										Result result = null;
										try {
											result = new MultiFormatReader().decode(bitmap);
											
											if (result != null && !result.getText().equals("") && !result.getText().equals(last)) //prevent cam from reading continuously
											{
												
												last = result.getText();
												System.out.println(last);
												String[] split = last.split("\\s+");
												if (split.length == 6) {
													
													String test_code = split[0];
													String student_code = split[1];
													String no_of_items = split[2];
													String student_answer = split[3];
													String attempt = split[4];
													String duration = split[5];
													
													
													String right_code = cd.substring(0,7);
													String right_items = Integer.toString(quiz_items);
													if (right_code.equals(test_code) && check_log(student_code) && no_of_items.equals(right_items))
													{
														checkAnswer(student_answer, student_info.get(1), quiz_name, student_code, right_code, attempt, duration);
														
														check_student.setText(student_info.get(1));
														check_status.setText("Logged");
													}
													else if (!right_code.equals(test_code)) {
														check_status.setText("Wrong Quiz Code!\n" + "(" + test_code + ")");
													}
													else if (!check_log(student_code)) {
														check_status.setText("Student not logged");
													}
													else if (!no_of_items.equals(right_items)) {
														check_status.setText("Wrong no. of items!\n" + "(" + no_of_items + ")" );
													}
													
												}
												
											}
											
											
											
										} catch (NotFoundException e) {
											// TODO Auto-generated catch block
										}
										
										final Image mainiamge = SwingFXUtils
											.toFXImage(grabbedImage, null);
										imageProperty.set(mainiamge);
									}
								});

								grabbedImage.flush();

							}
						} catch (Exception e) {
							
						}
					}

					return null;
				}

			};
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
			imgWebCamCapturedImage.imageProperty().bind(imageProperty);

		}

		private void closeCamera() {
			if (selWebCam != null) {
				selWebCam.close();
			}
		}

		public void stopCamera() {
			stopCamera = true;
			//btnStartCamera.setDisable(false);
			//btnStopCamera.setDisable(true);
		}

		public void startCamera() {
			stopCamera = false;
			startWebCamStream();
			//btnStartCamera.setDisable(true);
			//btnStopCamera.setDisable(false);
		}

		public void disposeCamera() {
			stopCamera = true;
			closeCamera();
			
			//btnStopCamera.setDisable(true);
			//btnStartCamera.setDisable(true);
		}
		
		
		public void checkAnswer(String answer, String sname, String qname, String scode, String tcode, String attempt, String duration) {
			String[] ansarr = answer.split("/");
			String rightAns ="";
			for (String s: answers) {
				rightAns = rightAns + s +"/";
			}
			int right = 0;
			int wrong = 0;
			int no_right = 0;
			for (int items = 0; items < answers.length; items++) {
				String correct = answers[items];
				if (!correct.equals("x")) {
					if (ansarr[items].equals(correct)) {
						System.out.println(ansarr[items] + " Correct answer: " + correct + " Correct");
						right++;
					}
					else {
						System.out.println(ansarr[items] + " Correct answer: " + correct + " Wrong");
						wrong++;
					}
				}
				else {
					no_right++;
				}

			}
			System.out.println("Correct: " + right);
			System.out.println("Wrong " + wrong);
			System.out.println("Bonus " + no_right);
			check_score.setTextFill(Color.GREEN);
			check_score.setText(right + "/" + Integer.toString(quiz_items));
			String qn = qname.replaceAll("\\s+", "/");
			String qd = quiz_date.replaceAll("\\s+", "/");
			String sn = sname.replaceAll("\\s+", "/");
			String sc = ClassSessionController.course.replaceAll("_", "/");
			
	        try {
	            generateQRCodeImage(tcode, qn + " " + qd + " " + answer + " " + rightAns + " " + right + " " + quiz_items + " " + sn + " " + scode + " " + sc, sname);
	        } catch (WriterException e) {
	            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
	        } catch (IOException e) {
	            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
	        }
	        
	        FileInputStream fi = null;
	        Workbook wb = null;
	        try {
	        	fi = new FileInputStream(ClassSessionController.test_excel);
				wb = new HSSFWorkbook(fi);
				Sheet sheet = wb.getSheet(qname);
				int rowNum = sheet.getLastRowNum();
				for (int rowCount = 1; rowCount <= rowNum; rowCount++) {
					Row row = sheet.getRow(rowCount);
					Cell code = row.getCell(0);
					if (code.getStringCellValue().equals(student_info.get(0))) {
						Cell score = row.getCell(5);
						if (score != null && !score.getStringCellValue().equals(""))
							{
								check_status.setText("Already Logged");
							}
						else {
							score = row.createCell(5);
							score.setCellValue(Integer.toString(right));
							Cell ans = row.createCell(6);
							ans.setCellValue(answer);
							Cell tries = row.createCell(7);
							tries.setCellValue(attempt);
							Cell dur = row.createCell(8);
							dur.setCellValue(duration);
						}

						break;
					}
				}
				
	        }
	        catch (Exception e) {
	        	
	        }
	        finally {
	        	if (fi != null) {try {fi.close();} catch (IOException e) {e.printStackTrace();}}
		        
		        FileOutputStream out = null;
				try {
					out = new FileOutputStream(new File(ClassSessionController.test_excel));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        if (wb != null && out !=null) {
		        try {           
		        	wb.write(out);
		        	wb.close();} 
		        catch (IOException e) {e.printStackTrace();}}
		        try {           
		            out.flush();
		            out.close();} 
		        catch (IOException e) {e.printStackTrace();}
	        }
			
		}
		ArrayList<String> student_info = null;
		public boolean check_log(String scode) {
			
			DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM-yyyy");
			DateTimeFormatter day = DateTimeFormatter.ofPattern("dd-EEE");
			
			LocalDateTime dateobj = LocalDateTime.now();
			
			FileInputStream fi = null;
			Workbook wb = null;
			boolean present = false;
			student_info = new ArrayList<String>();
			try
			{
			fi = new FileInputStream(ClassSessionController.attendance_excel);
			
			wb = new HSSFWorkbook(fi);
			int sheets = wb.getNumberOfSheets();
		    for (int s = 0; s < sheets; s++) {
		    	Sheet sh = wb.getSheetAt(0);
		    	int lastRow = sh.getLastRowNum();
		    	if (sh.getSheetName().equals(df.format(dateobj))) {
		    		
		    		Row row = sh.getRow(0);
		    		int today = row.getLastCellNum()-1;
		    		
		    		Cell now = row.getCell(today);
		    		if (now.getStringCellValue().equals(day.format(dateobj))) {
		    			for (int rowNum = 1; rowNum <= lastRow; rowNum++) {
		    				Row att = sh.getRow(rowNum);
		    				Cell code = att.getCell(0); 
		    					if (code.getStringCellValue().equals(scode)) {
		    						Cell attendance = att.getCell(today);
		    						if (attendance != null && !attendance.getStringCellValue().equals("Absent") && !attendance.getStringCellValue().equals("")) {
			    						Cell name = att.getCell(1);
			    						//Cell sex = att.getCell(2);
			    						//Cell id = att.getCell(3);
			    						//Cell course = att.getCell(4);
			    						
			    						student_info.add(scode);
			    						student_info.add(name.getStringCellValue());
			    						//student_info.add(sex.getStringCellValue());
			    						//student_info.add(id.getStringCellValue());
			    						//student_info.add(course.getStringCellValue());
			    						present = true;	
		    						}

		    					}
		    				}
		    			}
		    		}
		    		
		    	}
		    }


		    
		    
		    
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
			finally {
				if (fi != null) {
					try {fi.close();} catch (IOException e) {e.printStackTrace();}
				}
				if (wb != null) {
					try {wb.close();} catch (IOException e) {e.printStackTrace();}
				}
			    
				
			    
			}
			return present; 
		}
		/*void logQuiz() {
			//DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM-yyyy");
			//DateTimeFormatter day = DateTimeFormatter.ofPattern("dd-EEE");
			
			//LocalDateTime dateobj = LocalDateTime.now();
			
			String qname = quiz_name; 
			
			
			boolean dup = false;
			
			FileInputStream fi = null;
			Workbook wb = null;

			student_info = new ArrayList<String>();
			try
			{
			fi = new FileInputStream(ClassSessionController.test_excel);
			
			wb = new HSSFWorkbook(fi);
			int sheets = wb.getNumberOfSheets();
			for (int sh = 0; sh < sheets; sh++) {
				Sheet sheet = wb.getSheetAt(sh);
				if (sheet.getSheetName().equals(qname)) {
					dup = true;
					break;
				}
			}
			if (!dup) {
				Sheet sheet = wb.createSheet(qname);
				Row row = sheet.createRow(0);
				Cell s_code = row.createCell(0);
				Cell s_name = row.createCell(1);
				Cell s_sex = row.createCell(2);
				Cell s_id = row.createCell(3);
				Cell s_course = row.createCell(4);
				
				s_code.setCellValue("CODE");
				s_name.setCellValue("NAME");
				s_sex.setCellValue("SEX");
				s_id.setCellValue("ID NO.");
				s_course.setCellValue("COURSE CODE/ GRADE LEVEL");
				
			}
		    }

		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
			finally {
				
				if (fi != null) {try {fi.close();} catch (IOException e) {e.printStackTrace();}}
		        
		        FileOutputStream out = null;
				try {
					out = new FileOutputStream(new File(ClassSessionController.test_excel));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        if (wb != null) {
		        try {           
		        	wb.write(out);
		        	wb.close();} 
		        catch (IOException e) {e.printStackTrace();}}
		        try {
		        	if (out != null) {
		        		 out.flush();
				            out.close();
		        	}
		           } 
		        catch (IOException e) {e.printStackTrace();}
			    
				
			    
			}
		} */
		@FXML
	    private Label check_name;

	    @FXML
	    private Label check_code;

	    @FXML
	    private Label items_label;

	    @FXML
	    private Label check_student;

	    @FXML
	    private Label check_score;

	    @FXML
	    private Label check_status;
	    /*private boolean logScore(ArrayList<String> sinfo) {
	    	FileInputStream fi = null;
			Workbook wb = null;

			student_info = new ArrayList<String>();
			try
			{
			fi = new FileInputStream(ClassSessionController.test_excel);
			
			wb = new HSSFWorkbook(fi);
			int testSheetsNum = wb.getNumberOfSheets();
			for (int shNum = 0; shNum < testSheetsNum; shNum++) {
				
			}
		    }

		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
			finally {
				
				if (fi != null) {try {fi.close();} catch (IOException e) {e.printStackTrace();}}
		        
		        FileOutputStream out = null;
				try {
					out = new FileOutputStream(new File(ClassSessionController.test_excel));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        if (wb != null) {
		        try {           
		        	wb.write(out);
		        	wb.close();} 
		        catch (IOException e) {e.printStackTrace();}}
		        try {
		        	if (out != null) {
		        		 out.flush();
				            out.close();
		        	}
		           } 
		        catch (IOException e) {e.printStackTrace();}
			    
				
			    
			}
	    }*/
	    
		void importStudents() throws IOException {
			
			FileInputStream fi = null;
			Workbook wb = null;
			ArrayList<Row> studentsList = new ArrayList<Row>();
			try
			{fi = new FileInputStream(ClassSessionController.student_excel);
			wb = new HSSFWorkbook(fi);
			Sheet sh = wb.getSheetAt(0);
			
		    int endRow = sh.getLastRowNum();
		    for (int i = 1; i <= endRow ; i++) {
		    	Row row = sh.getRow(i);
		    	studentsList.add(row);
		    }
			}
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
			finally{
				if (fi != null) {
					   try {fi.close();} catch (IOException e) {e.printStackTrace();}
				}	
				if (wb != null) {
					   try {wb.close();} catch (IOException e) {e.printStackTrace();}
				}
				}
			
			try {
				fi = new FileInputStream(ClassSessionController.test_excel);
				wb = new HSSFWorkbook(fi);
				int sheetsNum = wb.getNumberOfSheets();
				for (int shNum = 0; shNum <= sheetsNum; shNum++) {
					boolean dup = false;
					//Sheet studentSheet = studentsList.get(shNum);
					Sheet testSheet = wb.getSheetAt(shNum);
					int attRow = studentsList.size();
					int testrownum = testSheet.getLastRowNum();
					//int testRow = testSheet.getLastRowNum();
					for (int rowNum = 0; rowNum < attRow; rowNum++) {
						//Row test_row = testSheet.getRow(rowNum);
						Row att_row = studentsList.get(rowNum);
						Cell att_cell = studentsList.get(rowNum).getCell(0);
						String attString = att_cell.getStringCellValue();
						for (int test_row = 1; test_row < testrownum; test_row++) {
							Cell test_cell = testSheet.getRow(test_row).getCell(0);
							
							if (attString.equals(test_cell.getStringCellValue())) {
								dup = true;
							}
						}
						if (!dup) {
							Row new_test = testSheet.createRow(++testrownum);
							Cell code = new_test.createCell(0);
							Cell name = new_test.createCell(1);
							Cell sex = new_test.createCell(2);
							Cell id = new_test.createCell(3);
							Cell course = new_test.createCell(4);
							
							Cell scode = att_row.getCell(0);
							Cell sname = att_row.getCell(1);
							Cell ssex = att_row.getCell(2);
							Cell sid = att_row.getCell(3);
							Cell scourse = att_row.getCell(4);
							
							code.setCellValue(scode.getStringCellValue());
							name.setCellValue(sname.getStringCellValue());
							sex.setCellValue(ssex.getStringCellValue());
							id.setCellValue(sid.getStringCellValue());
							course.setCellValue(scourse.getStringCellValue());
							dup = false;
							
								
							
						}
						
					}

				}
			}
			catch (Exception e) {
				
			}
		    finally {
		    	if (fi != null) {try {fi.close();} catch (IOException e) {e.printStackTrace();}}
		        
		        FileOutputStream out = new FileOutputStream(new File(ClassSessionController.test_excel));
		        if (wb != null) {
		        try {           
		        	wb.write(out);
		        	wb.close();} 
		        catch (IOException e) {e.printStackTrace();}}
		        try {           
		            out.flush();
		            out.close();} 
		        catch (IOException e) {e.printStackTrace();}

		    }
		
					
		}
		// private static String imagepath = "./MyQRCode.png";

		    private static void generateQRCodeImage(String tcode, String text, String sname)
		            throws WriterException, IOException {
		        QRCodeWriter qrCodeWriter = new QRCodeWriter();
		        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);

		        Path path = Paths.get(ClassSessionController.answers+ tcode +"\\" + sname +".png");
		        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		    }
		    @FXML
		    private ImageView qr_imageview;

		    @FXML
		    private GridPane record_gridpane;
		 
		    void displayRecords() {
		    	record_gridpane.getChildren().clear();
		    	sname = new ArrayList<String>();
		    	scourse = new ArrayList<String>();
		    	sscore = new ArrayList<String>();
		    	sans = new ArrayList<String>();
		    	stries = new ArrayList<String>();
		    	sdur = new ArrayList<String>();
		    	FileInputStream fi = null;
		    	Workbook wb = null;
		    	try {
		    		fi = new FileInputStream(ClassSessionController.test_excel);
					wb = new HSSFWorkbook(fi);
					Sheet sheet = wb.getSheet(quiz_name);
					int lastRow = sheet.getLastRowNum();
					for (int count = 1; count <= lastRow; count++) {
						Row row = sheet.getRow(count);
						Cell name = row.getCell(1);
						Cell course = row.getCell(4);
						Cell score = row.getCell(5);
						Cell ans = row.getCell(6);
						Cell tries = row.getCell(7);
						Cell duration = row.getCell(8);
						
							if (score != null && !score.getStringCellValue().equals("")) {
								sname.add(name.getStringCellValue());
								scourse.add(course.getStringCellValue());
								sscore.add(score.getStringCellValue());
								sans.add(ans.getStringCellValue());
								stries.add(tries.getStringCellValue());
								sdur.add(duration.getStringCellValue());
							}

						
							
						
						
						
					}
		    	}
		    	catch (Exception e) {
		    		e.printStackTrace();
		    	}
		    	finally {
					if (fi != null) {
						try {fi.close();} catch (IOException e) {e.printStackTrace();}
					}
					if (wb != null) {
						try {wb.close();} catch (IOException e) {e.printStackTrace();}
					}
		    
		    	}
		    	for (int gridRow = 0; gridRow < sname.size(); gridRow++) {
		    		
		    		
		    		Label nm = new Label(sname.get(gridRow));
		    		Label sc = new Label(sscore.get(gridRow));
		    		Label tr = new Label(stries.get(gridRow));
		    		Label dr = new Label(sdur.get(gridRow));
		    		
		    		nm.setFont(new Font("Arial black",15));
					sc.setFont(new Font("Arial black",15));
					tr.setFont(new Font("Arial black",15));
					dr.setFont(new Font("Arial black",15));
					
					nm.setMinWidth(250);
					nm.setMaxWidth(250);
					sc.setMinWidth(100);
					sc.setMaxWidth(100);
					tr.setMinWidth(90);
					tr.setMaxWidth(90);
					dr.setMinWidth(120);
					dr.setMaxWidth(120);
					
					nm.setMaxHeight(30);
					nm.setMinHeight(30);
					sc.setMaxHeight(30);
					sc.setMinHeight(30);
					tr.setMaxHeight(30);
					tr.setMinHeight(30);
					dr.setMaxHeight(30);
					dr.setMinHeight(30);
					
					sc.setAlignment(Pos.CENTER);
					tr.setAlignment(Pos.CENTER);
					dr.setAlignment(Pos.CENTER);
					nm.setPadding(new Insets(0,0,0,10));
					
					
					
					record_gridpane.add(nm, 0, gridRow);
					record_gridpane.add(sc, 1, gridRow);
					record_gridpane.add(dr, 2, gridRow);
					record_gridpane.add(tr, 3, gridRow);
		    	}
		    }
		    @FXML
		    private Label test_items_label;

		    @FXML
		    private Label test_code_label;

		    @FXML
		    private Label test_name_label;

		    @FXML
		    private Label test_date_label;
		    boolean record_selected = false;
		    int test_rowIndex = 0;
		    @FXML
		    void clickGridTest(MouseEvent event) {
		    	
		    	 record_selected = true;
		    	if (event.isStillSincePress()) {
			        Node clickedNode = event.getPickResult().getIntersectedNode();
			        
			        if (sname.size()>0) {
			        if (clickedNode != record_gridpane) {
			        	Node parent = clickedNode.getParent();
			            while (parent != record_gridpane) {
			                clickedNode = parent;
			                parent = clickedNode.getParent();
			            }
			            test_rowIndex = GridPane.getRowIndex(clickedNode);
			            //System.out.println(test_codes[rowIndex]);
			            for (Node node : record_gridpane.getChildren()) {
			            	if (GridPane.getRowIndex(node) == test_rowIndex) {
			            		node.setStyle("-fx-background-color: #D3D3D3;");
			            	}
			            	else {
			            		node.setStyle(null);
			            	}
			            	
			            }
			          
			        	}
			        }
			    	}
		    	
		    }
		    
		    @FXML
		    private JFXButton analysis_button;

		    
		    @FXML
		    private JFXButton del11;

		    @FXML
		    private Pane analysis_pane;

		    @FXML
		    void deleteSheet(ActionEvent event) {
		    	FileInputStream fi = null;
		    	Workbook wb = null;
		    	try {
		    		
		    	
		    	}
		    	catch (Exception e) {
		    		
		    	}
		    }



		    @FXML
		    void exportCurrentTest(ActionEvent event) {

		    }

		   

		    @FXML
		    void openAnalysis(ActionEvent event) {

		    }
		    static ArrayList<String> sname;
		    static ArrayList<String> scourse;
		    static ArrayList<String> sscore;
		    static ArrayList<String> sans;
		    static ArrayList<String> stries;
		    static ArrayList<String> sdur;

		    @FXML
		    void openSheet(ActionEvent event) {
		    	String rightAns ="";
				for (String s: answers) {
					rightAns = rightAns + s +"/";
				}
		    	String tcode = cd.substring(0, 7);
		    	if (record_selected) {
		    		System.out.println(tcode + " " + sname.get(test_rowIndex)+ " " +scourse.get(test_rowIndex)+ " " + sscore.get(test_rowIndex)+ " " +
		    				items_label.getText()+ " " + sans.get(test_rowIndex)+ " " +rightAns);
		    		OpenSheetController.display(tcode, sname.get(test_rowIndex), scourse.get(test_rowIndex), sscore.get(test_rowIndex), 
		    				items_label.getText(), sans.get(test_rowIndex), rightAns);
		    		
		    	}
		    }
		   

		   

 }

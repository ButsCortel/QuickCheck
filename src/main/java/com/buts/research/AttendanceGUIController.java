package com.buts.research;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.animation.Timeline;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class AttendanceGUIController implements Initializable{
	@FXML
	private ComboBox<WebCamInfo> cbCameraOptions;
    @FXML
    private ComboBox<String> date_combo;
    @FXML
    private Spinner<String> day_spinner;

	@FXML
	BorderPane bpWebCamPaneHolder;
    @FXML
    private Label name_label;

    @FXML
    private Label timein_label;
    @FXML
    private Pane webcampane;
    @FXML
    private Pane attendancePane;
    @FXML
    private JFXButton take_attendance;
    @FXML
    private JFXButton view_attendance;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Label in_status;
    @FXML
    private Label time_label;
    @FXML
    private Label date_label;
    @FXML
    private JFXButton exp_month;

    @FXML
    private JFXButton exp_all;
    
    @FXML
    private AnchorPane attendanceGPane;
    ArrayList<String> month = null;
    ObservableList<String> days =null;
    SpinnerValueFactory<String> valueFactory = null;
    String date = null;
    int window;



	@FXML
	ImageView imgWebCamCapturedImage;

	    @FXML
	    Label course_label;

	    @FXML
	    Label subject_label;	

	    @FXML
	    GridPane take_gridpane;
	    @FXML
	    private GridPane view_gridpane;

	    @FXML
	    ComboBox<?> attendance_combo;

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

    static ArrayList<String> sheetNames;
    
    //static Stage attendancegui_window;
    private int webcam_num = 0;
    
    private String grace_time = "";
    private String time_in = "";
	

	private BufferedImage grabbedImage;
	private Webcam selWebCam = null;
	private boolean stopCamera = false;
	private boolean camOpen = false;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	static ArrayList<String> scheds_day;
	static ArrayList<String> scheds;

	private String cameraListPromptText = "Choose Camera";
	private String attListPromptText = "No Records";
	private String last = "";
	private String[] results;
	private ObservableList<String> items = null;
	private FileChooser directoryChooser = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		attendanceGPane.setOpacity(0);
		attendanceGPane.setDisable(true);
		FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), attendanceGPane);
    	fadeOutTransition.setFromValue(0);
    	fadeOutTransition.setToValue(1);
    	fadeOutTransition.play();
    	initClock();
    	
    	fadeOutTransition.setOnFinished((e) -> {
    		
    		scrollpane.setPadding(new Insets(0, 0, 0, 10));
    	    directoryChooser = new FileChooser();
    	    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
    	    directoryChooser.getExtensionFilters().add(
    	    	     new FileChooser.ExtensionFilter("XLS files", "*.xls")
    	    	);
    		day_spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
    		    
    		        display_att();
    		    
    		});
    		items = FXCollections.observableArrayList();
    		date_combo.setItems(items);
    		date_combo.setEditable(true);

    		day_spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
    		

    		

    		try {
    			importStudents();
    		} catch (IOException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		}
        	try {
    			createAttendance();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        	try {
    			sortSheet();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		try {
    			avail_attendances();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		


    		name_label.setText("");
    		name_label.setMaxWidth(260);
    		timein_label.setText("");
    		in_status.setText("");
        	attendanceRPane.setVisible(true);  	
        	attendanceCPane.setVisible(false);
        	
        	take_attendance.setVisible(true);
        	view_attendance.setVisible(false);
        	

    		course_label.setText(ClassSessionController.course.replaceAll("_", " "));
    		subject_label.setText(ClassSessionController.subject.replaceAll("_", " "));
    		attendanceGPane.setDisable(false);

    	});
		

		
	}
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
	protected void setImageViewSize() {

		double height = bpWebCamPaneHolder.getHeight();
		double width = bpWebCamPaneHolder.getWidth();

		imgWebCamCapturedImage.setFitHeight(height);
		imgWebCamCapturedImage.setFitWidth(width);
		imgWebCamCapturedImage.prefHeight(height);
		imgWebCamCapturedImage.prefWidth(width);
		
		//imgWebCamCapturedImage.setPreserveRatio(true);

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
				attendanceGPane.setDisable(false);
				return null;
				
			}

		};

		new Thread(webCamIntilizer).start();
		
		
	}

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
									LuminanceSource source = new BufferedImageLuminanceSource(grabbedImage);
									BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
									Result result = null;
									
									try {
										result = new MultiFormatReader().decode(bitmap);										
										if (result != null && !result.getText().equals("") && !result.getText().equals(last)) //prevent cam from reading continuously
										{
											last = result.getText();
											results = last.split("\\s+");
									        if (results.length > 3 && results[results.length -1].chars().allMatch(Character::isDigit) && results[results.length -1].length() > 3)
									        {
									        	
									        	try {


													if (checkStudent(results[results.length -1]).size() ==  5) {
														
														logStudent (checkStudent(results[results.length -1]));
													}
													else {
														in_status.setText("No Record!");
													}
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
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
						e.printStackTrace();
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
	private ArrayList<String> checkStudent(String s) throws IOException {
		FileInputStream fi = null;
		Workbook wb = null;
		ArrayList<String> student_info = new ArrayList<String>();
		try
		{
		fi = new FileInputStream(ClassSessionController.student_excel);
		
		wb = new HSSFWorkbook(fi);
		Sheet sh = wb.getSheetAt(0);
	    int endRow = sh.getLastRowNum();


	    for (int i = 1; i <= endRow; i++) {
	        Cell c0 = wb.getSheetAt(0).getRow(i).getCell(0);
	        if(c0.getStringCellValue().equals(s)) {
	        	Cell c1 = wb.getSheetAt(0).getRow(i).getCell(1);
	        	Cell c2 = wb.getSheetAt(0).getRow(i).getCell(2);
	        	Cell c3 = wb.getSheetAt(0).getRow(i).getCell(3);
	        	Cell c4 = wb.getSheetAt(0).getRow(i).getCell(4);
	        	student_info.add(c0.getStringCellValue());
	        	student_info.add(c1.getStringCellValue());
	        	student_info.add(c2.getStringCellValue());
	        	student_info.add(c3.getStringCellValue());
	        	student_info.add(c4.getStringCellValue());
	        	break;
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


	    return student_info;


	}
	private void logStudent (ArrayList<String> info) throws IOException {
		int late = 0;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM-yyyy");
		DateTimeFormatter day = DateTimeFormatter.ofPattern("EEE");
		DateTimeFormatter timein = DateTimeFormatter.ofPattern("hh:mm");
        DateTimeFormatter format = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT);
        DateTimeFormatter daylog = DateTimeFormatter.ofPattern("dd-EEE");
		LocalDateTime dateobj = LocalDateTime.now();
		String ti = "";
		
		String tid = "";
		boolean dup = false;
		
		for (String s: scheds_day) {
			//System.out.println(s);
			String[] t = s.split("\\s+");
			//System.out.println(day.format(dateobj));
			if (t[0].equals(day.format(dateobj))) {
				String hr = t[1].substring(0,3);
				String min = t[1].substring(3, 5);
				int m = Integer.parseInt(min) + 15;
				String min2 = Integer.toString(m);
				grace_time = hr+min2 + " " + t[2];
				time_in = hr+min + " " + t[2];
				//System.out.println(time_in);
				//System.out.println(grace_time);
				break;
			}
			else {
				time_in = "11:59 PM";
				grace_time = "11:59 PM";
			}
			
		}
	    //DateFormat df = new SimpleDateFormat("MMM-yyyy");
	    //DateFormat timein = new SimpleDateFormat("hh:mm aa");
        //Date dateobj = new Date();
		if (dateobj.getHour() > 11) {
			ti = timein.format(dateobj) + " PM";
			tid = timein.format(dateobj) + " PM";
		}
		else {
			ti = timein.format(dateobj) + " AM";
			tid = timein.format(dateobj) + " AM";
		}
		//System.out.println(ti);

        LocalTime timeIn = LocalTime.parse(ti, format);
        LocalTime classTime = LocalTime.parse(time_in, format);
        LocalTime graceTime = LocalTime.parse(grace_time, format);
        //Duration duration = Duration.between(time1, time2);
       if (timeIn.compareTo(graceTime) > 0 ) {
        	ti += " (LATE)"; 
        	late = 1;
        }
        else if (timeIn.compareTo(classTime) > 0 && timeIn.compareTo(graceTime) < 0) {
        	ti += " (GRACE PERIOD)";
        	late = -1;
        }
        else {
        	ti += " (ON TIME)";
        	late = 0;
        }
	    
	    Row row = null;
	    int gRow = 0;
		FileInputStream fi = null;
		Workbook wb = null;
		int current_att = 0;
		try
		{
			fi = new FileInputStream(ClassSessionController.attendance_excel);
			wb = new HSSFWorkbook(fi);
		Sheet sh = wb.getSheet(df.format(dateobj));
	    int endRow = sh.getLastRowNum();
	    
	    Row r = sh.getRow(0);
	    int maxCell=  r.getLastCellNum();
   
        for (int j = 5; j < maxCell ; j++ ) {
        	if (r.getCell(j) != null) {
        		Cell c0 = r.getCell(j);
        		if (c0.getStringCellValue().equals(daylog.format(dateobj))) {
        			current_att = c0.getColumnIndex();
        			break;
        		}
        		else {
        			current_att = maxCell;
        		}
        	}
        	else {
        		current_att = maxCell;
        		break;
        	}
        }


	    for (int i = 0; i <= endRow; i++) {
	        Cell c0 = sh.getRow(i).getCell(0);

	        if (c0.getStringCellValue().equals(info.get(0))) {
	        	dup = true;
	        	gRow = i;

	        }

	        
	    }
	    name_label.setText(info.get(1));
        timein_label.setText(tid);
	    if (dup) {
        		row = sh.getRow(gRow);
        		if (row.getCell(current_att) == null) {
        			row.createCell(current_att).setCellValue(ti);
        		
        			if (late == 1) {
        				in_status.setText("LATE");
        			}
        			else if (late == -1) {
        				in_status.setText("GRACE PERIOD");
        			}
        			else {
        				in_status.setText("ON TIME");
        			}
        			
        			
        		}
        		else {
        			in_status.setText("Already logged");
        		}
        		
	    }
	    else {
    			row = sh.createRow(++endRow);
    			row.createCell(0).setCellValue(info.get(0));
		       	row.createCell(1).setCellValue(info.get(1));
		       	row.createCell(2).setCellValue(info.get(2));
		       	row.createCell(3).setCellValue(info.get(3));
		       	row.createCell(4).setCellValue(info.get(4));
		       	row.createCell(current_att).setCellValue(ti);
    			if (late == 1) {
    				in_status.setText("LATE");
    			}
    			else if (late == -1) {
    				in_status.setText("GRACE PERIOD");
    			}
    			else {
    				in_status.setText("ON TIME");
    			}
    	}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		finally {
			if (fi != null) {try {fi.close();} catch (IOException e) {e.printStackTrace();}}
		    FileOutputStream output_file =new FileOutputStream(new File(ClassSessionController.attendance_excel));  
		    if (wb != null) {try {wb.write(output_file);
		    						wb.close();} 
		    				catch (IOException e) {e.printStackTrace();}
		    						}
		    try {output_file.flush();} catch (IOException e) {e.printStackTrace();}
		    try {output_file.close();} catch (IOException e) {e.printStackTrace();}

		    
		    
		    sortSheet();
		    checkCurrentAttendance();
		}
	    


	}

	private void closeCamera() {
		if (selWebCam != null) {
			selWebCam.close();
		}
	}




	public void attendanceGUIWindow() {
    	try {
    		//attendancegui_window = new Stage();
    		Parent root = FXMLLoader.load(App.class.getResource("AttendanceGUI.fxml"));
			//JFXDecorator decorator = new JFXDecorator(attendancegui_window , root, false, false, true);
			//decorator.setCustomMaximize(true); 
			//String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene att_scene = new Scene(root);
			//att_scene.getStylesheets().add(uri) ;
			SplashController.stage.setScene(att_scene);
			/*attendancegui_window.setTitle("QuickCheck");
			//attendancegui_window.initStyle(StageStyle.UNDECORATED);
			//attendancegui_window.setResizable(false);

			
			attendancegui_window.show();
			ClassSessionController.class_window.hide(); 

			
			attendancegui_window.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
		        	AlertBoxController.label_text = "Are you sure you want to exit?";
		        	if(AlertBoxController.display("Exit")) {
		        		disposeCamera();
				        Platform.exit();
				        System.exit(0);
				        
		        	}
		        	else {
		        		t.consume();
		        	}
		        		
		        	

			    }
			});
			
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	void checkCurrentAttendance() throws IOException {
		take_gridpane.getChildren().clear();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM-yyyy");
		DateTimeFormatter day = DateTimeFormatter.ofPattern("dd-EEE");
		//DateTimeFormatter timein = DateTimeFormatter.ofPattern("hh:mm aa");
		
		LocalDateTime dateobj = LocalDateTime.now();
		FileInputStream fi = null;
		Workbook wb = null;
		ArrayList<String> students_name = new ArrayList<String>();
		ArrayList<String> students_timein = new ArrayList<String>();
		int current_att= 0;
		try 
		{
		fi = new FileInputStream(ClassSessionController.attendance_excel);
		wb = new HSSFWorkbook(fi);

		
		Sheet sh = wb.getSheet(df.format(dateobj)) ;
		ArrayList <Row> rows = new ArrayList <Row>();
		Row rw = sh.getRow(0);
	    
        int lc = rw.getLastCellNum();
        for (int j = 5; j < lc ; j++ ) {
        	if (rw.getCell(j) != null) {
        		Cell c0 = rw.getCell(j);
        		if (c0.getStringCellValue().equals(day.format(dateobj))) {
        			current_att = c0.getColumnIndex();
        			break;
        		}
        		else {
        			current_att = lc;
        		}
        	}
        	else {
        		current_att = lc;
        	}
        }
        //copy all rows to temp
		int r =0;
		for (Row myrows: sh) {
			if (r != 0) {
				if (myrows.getCell(current_att) != null) {
				rows.add(myrows);	
				}
				
			}
			r++;
			
		}
		final int current_att2 = current_att;
        rows.sort(Comparator.comparing(cells -> cells.getCell(current_att2).getStringCellValue()));
        for (Row myrow: rows) {
            Cell c0 = myrow.getCell(1);
            Cell c1 = myrow.getCell(current_att2);
            
            if (c1 != null) {
            	String t = c1.getStringCellValue();
            	t = t.substring(9, t.length());
            	students_name.add(c0.getStringCellValue());
            	students_timein.add(t);
            }
        }}
	    catch(Exception e)
	    {
	    }
		finally {
			if (fi != null) {
				try {fi.close();} catch (IOException e) {e.printStackTrace();}
			}
			if (wb != null) {
				try {wb.close();} catch (IOException e) {e.printStackTrace();}
			}

		    
		    
		}

	    int c =0;
	    for (String name : students_name) {
	    	Label rs = new Label(Integer.toString(c +1)+".");
	    	Label n = new Label(name);
	    	Label t = new Label(students_timein.get(c));
	    	
	    	rs.setFont(new Font("Arial black",15));
	    	n.setFont(new Font("Arial black",15));
			t.setFont(new Font("Arial black",15));
			
			rs.setAlignment(Pos.CENTER_LEFT);
			n.setAlignment(Pos.CENTER_LEFT);
			t.setAlignment(Pos.CENTER_LEFT);
			
			rs.setMinHeight(30);
			n.setMinHeight(30);
			t.setMinHeight(30);
			
			rs.setMaxHeight(30);
			n.setMaxHeight(30);
			t.setMaxHeight(30);
			
			/*rs.setMaxWidth(30);
			n.setMaxWidth(200);
			t.setMaxWidth(105);
			
			rs.setMinWidth(30);
			n.setMinWidth(200);
			t.setMinWidth(105);*/

			
			take_gridpane.add(rs, 0, c);
			take_gridpane.add(n, 1, c);
			take_gridpane.add(t, 2, c);
			c++;
	    }


	}
	
	@FXML
    void back(ActionEvent event) {
		
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), attendanceCPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0);	
    	fadeOutTransition.play();
    	attendanceGPane.setDisable(true);
    	fadeOutTransition.setOnFinished((e) -> {

    		ClassSessionController start = new ClassSessionController();

            start.startClass();
         	disposeCamera();

    	});

    }
    @FXML
    void home(MouseEvent event) {
    	
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), attendanceCPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0);
    	attendanceGPane.setDisable(true);
    	fadeOutTransition.play();
    	
    	fadeOutTransition.setOnFinished((e) -> {

    		

        	ClassGUIController startClass = new ClassGUIController();
        	startClass.classGUIWindow();
     		disposeCamera();

    	});


    }

    @FXML
    private Pane attendanceRPane;
    @FXML
    private Pane attendanceCPane;
    
    @FXML
    void takeAttendance(ActionEvent event) {


    	
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), attendanceRPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0.5);
    	fadeOutTransition.play();
       	take_attendance.setVisible(false);
       	view_attendance.setVisible(true);
    
    	attendanceGPane.setDisable(true); 	
    	fadeOutTransition.setOnFinished((e) -> {
        	try {
    			//createAttendance();
    			checkCurrentAttendance();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		attendanceRPane.setVisible(false);  	
        	attendanceCPane.setVisible(true);

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
        		cbCameraOptions.setPromptText(cameraListPromptText);
        		if (webCamCounter > 1) {
        			cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

        				@Override
        				public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
        					if (arg2 != null) {
        						webcam_num = arg2.getWebCamIndex();
        					}
        				}
        			});
        		}
        		else {
        			cbCameraOptions.setDisable(true);
        			webcam_num = 0;
        		}

        		Platform.runLater(new Runnable() {

        			@Override
        			public void run() {
        				setImageViewSize();
        			}
        		});

        		initializeWebCam(webcam_num);
        	}
        	else {
        		startCamera();
        		attendanceGPane.setDisable(false);
        	}
        	
        	

           	attendanceRPane.setVisible(false);  	
           	attendanceCPane.setVisible(true);
           	attendanceCPane.setOpacity(1);
     
    	});
    	
    }


    
    @FXML
    void viewAttendance(ActionEvent event) {
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), attendanceCPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0.5);
    	fadeOutTransition.play();
    	attendanceGPane.setDisable(true);
    	take_attendance.setVisible(true);
    	view_attendance.setVisible(false);
    	fadeOutTransition.setOnFinished((e) -> {
    		last = "";
        	stopCamera();
    		try {
    			avail_attendances();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}

           	attendanceRPane.setVisible(true);  	
           	attendanceCPane.setVisible(false);
           	attendanceRPane.setOpacity(1);

        	attendanceGPane.setDisable(false);
 

    	});
    	
    }


	public void stopCamera() {
		stopCamera = true;

	}

	public void startCamera() {
		stopCamera = false;
		startWebCamStream();

	}

	public void disposeCamera() {
		stopCamera = true;
		closeCamera();

	}
	public static void sortSheet() throws IOException {
		FileInputStream myxls = null;

		Workbook wb = null;
		try 
		{
		myxls = new FileInputStream(ClassSessionController.attendance_excel);

		wb = new HSSFWorkbook(myxls);
	    Sheet sheet = wb.getSheetAt(0);
		ArrayList <Row> rows = new ArrayList <Row>();
        //copy all rows to temp
		int r =0;
		for (Row myrows: sheet) {
			if (r != 0) {
				rows.add(myrows);
			}
			r++;
			
		}
        //List<Row> rows = Lists.newArrayList(sheet.rowIterator());
        //sort rows in the temp
        rows.sort(Comparator.comparing(cells -> cells.getCell(1).getStringCellValue()));
        //remove all rows from sheet
        removeAllRows(sheet);
        //create new rows with values of sorted rows from temp
        r = 1;
        for (Row myrow: rows) {
            Row newRow = sheet.createRow(r);
            Row sourceRow = myrow;
            // Loop through source columns to add to new row
            for (int j = 0; j < sourceRow.getLastCellNum(); j++) {
                // Grab a copy of the old/new cell
                Cell oldCell = sourceRow.getCell(j);
                Cell newCell = newRow.createCell(j);

                // If the old cell is null jump to next cell
                if (oldCell == null) {
                    newCell = null;
                    continue;
                }

                switch (oldCell.getCellType()) {
                    case BLANK:
                        newCell.setCellValue(oldCell.getStringCellValue());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(oldCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        newCell.setCellErrorValue(oldCell.getErrorCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellFormula(oldCell.getCellFormula());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(oldCell.getNumericCellValue());
                        break;
                    case STRING:
                        newCell.setCellValue(oldCell.getRichStringCellValue());
                        break;
				default:
					break;
                }
            }r++;
        }}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		finally {
			if (myxls!= null) {
				try {
					myxls.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
	        FileOutputStream output_file =new FileOutputStream(new File(ClassSessionController.attendance_excel)); 
			if (wb != null) {
				try {
				       wb.write(output_file);
				       wb.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
				try {
				       output_file.flush();
				       output_file.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
    }
	

    private static void removeAllRows(Sheet sheet) {
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                sheet.removeRow(sheet.getRow(i+1));
            }
        }
	public void createAttendance() throws IOException {
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM-yyyy");
		DateTimeFormatter day = DateTimeFormatter.ofPattern("dd-EEE");
		
		LocalDateTime dateobj = LocalDateTime.now();
	       //DateFormat df = new SimpleDateFormat("MMM-yyyy");
	       //DateFormat day = new SimpleDateFormat("dd-EEE");
	       //Date dateobj = new Date();
        FileInputStream myxls = null;
	       Workbook workbook = null;
	       boolean dup = false;
	       
		
	    try {
	        myxls = new FileInputStream(ClassSessionController.attendance_excel);
		       workbook = new HSSFWorkbook(myxls);
		       Sheet sheet = workbook.getSheet(df.format(dateobj));
		       int current_att = 0;
	        if (workbook.getNumberOfSheets() != 0) {
	            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	            	String wn = workbook.getSheetName(i);
	               if (wn.equals(df.format(dateobj))) {
	            	   
	                    sheet = workbook.getSheet(wn);
	                    int maxRow = sheet.getLastRowNum();
	                    boolean dupstud = false;
	                    for (int rowAtt = 0; rowAtt < studentsList.size(); rowAtt++) {
	                    	Row row_stu = studentsList.get(rowAtt); //get attendance
	                    	Cell cell_code = row_stu.getCell(0); //get cell from attendance
	                    	String stu = cell_code.getStringCellValue(); // get string from attendance cell
	                    	for (Row rw: sheet) {
	                    		Cell cell_att = rw.getCell(0);
	                    		String att = cell_att.getStringCellValue();
	                    		if(att.equals(stu)) {
	                    			dupstud = true;
	                    			break;
	                    		}
	                    		else {
	                    			dupstud = false;
	                    		}
	                    		
	                    		
	                    	}
	                    	
	                    	if (!dupstud) {
	                    		Row newStud = sheet.createRow(++maxRow);
	                    		
	                    		Cell c0 = newStud.createCell(0);
	                    		c0.setCellValue(stu);
	                    		Cell c1 = newStud.createCell(1);
	                    		c1.setCellValue(row_stu.getCell(1).getStringCellValue());
	                    		Cell c2 = newStud.createCell(2);
	                    		c2.setCellValue(row_stu.getCell(2).getStringCellValue());
	                    		Cell c3 = newStud.createCell(3);
	                    		c3.setCellValue(row_stu.getCell(3).getStringCellValue());
	                    		Cell c4 = newStud.createCell(4);
	                    		c4.setCellValue(row_stu.getCell(4).getStringCellValue());
	                    	}
	                    }
	                    Row row = sheet.getRow(0);
	                    int maxCell = row.getLastCellNum();
	                    for (int j = 5; j < maxCell ; j++ ) {
	                    	if (row.getCell(j) != null) {
	                    		Cell c0 = row.getCell(j);
	                    		if (c0.getStringCellValue().equals(day.format(dateobj))) {
	                    			current_att = c0.getColumnIndex();
	                    			break;
	                    		}
	                    		else {
	                    			current_att = maxCell;
	                    		}
	                    }
                    		else {
                    			current_att = maxCell;
                    		}
	                    }
	                    if (current_att == maxCell) {
	                    	Cell c1 = row.createCell(maxCell);
	                    	c1.setCellValue(day.format(dateobj));
	                    }


	                    
	                    dup = true;
	                    break;
	                    
	               		}   
	                }
	            	if (!dup) {
	                	sheet = workbook.createSheet(df.format(dateobj));
	                    Row row = sheet.createRow(0); 
	        	        
	        	        Cell cell0 = row.createCell(0);
	        	        cell0.setCellValue("CODE");

	        	        Cell cell1 = row.createCell(1);
	        	        cell1.setCellValue("NAME");
	        	        
	        	        Cell cell2 = row.createCell(2);
	        	        cell2.setCellValue("SEX");
	        	        
	        	        Cell cell3= row.createCell(3);
	        	        cell3.setCellValue("ID NO.");
	        	        
	        	        Cell cell4 = row.createCell(4);
	        	        cell4.setCellValue("COURSE CODE/ GRADE LEVEL");
	        	        
	        	        Cell cell5 = row.createCell(5);
	        	        cell5.setCellValue(day.format(dateobj));
	        	        for (int rowNum = 0; rowNum< studentsList.size(); rowNum++) {
	        	        	Row rowNew = sheet.createRow(rowNum+1);
	        	        	Row rowOld = studentsList.get(rowNum);
	        	        	int lastCell = rowOld.getLastCellNum();
	        	        	for (int cellNum = 0; cellNum < lastCell; cellNum++) {
	        	        		Cell cellNew = rowNew.createCell(cellNum);
	        	        		Cell cellOld = rowOld.getCell(cellNum);
	        	        		String content = cellOld.getStringCellValue();
	        	        		cellNew.setCellValue(content);
	        	        				
	        	        	}
	        	        }
	            	}
            
	        }
	        else { 
	        	if (studentsList.size()>0){
	            // Create new sheet to the workbook if empty
	        	sheet = workbook.createSheet(df.format(dateobj));
                Row row = sheet.createRow(0); 
    	        
    	        Cell cell0 = row.createCell(0);
    	        cell0.setCellValue("CODE");

    	        Cell cell1 = row.createCell(1);
    	        cell1.setCellValue("NAME");
    	        
    	        Cell cell2 = row.createCell(2);
    	        cell2.setCellValue("SEX");
    	        
    	        Cell cell3= row.createCell(3);
    	        cell3.setCellValue("ID NO.");
    	        
    	        Cell cell4 = row.createCell(4);
    	        cell4.setCellValue("COURSE CODE/ GRADE LEVEL");
    	        
    	        Cell cell5 = row.createCell(5);
    	        cell5.setCellValue(day.format(dateobj));
    	        for (int rowNum = 0; rowNum< studentsList.size(); rowNum++) {
    	        	Row rowNew = sheet.createRow(rowNum+1);
    	        	Row rowOld = studentsList.get(rowNum);
    	        	int lastCell = rowOld.getLastCellNum();
    	        	for (int cellNum = 0; cellNum < lastCell; cellNum++) {
    	        		Cell cellNew = rowNew.createCell(cellNum);
    	        		Cell cellOld = rowOld.getCell(cellNum);
    	        		String content = cellOld.getStringCellValue();
    	        		cellNew.setCellValue(content);
    	        				
    	        	}
    	        }}
	        }
 
	       // XSSFCell cell2 = row.createCell(2);
	       // cell2.setCellValue("Percent Change");

	    } 
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally {
	    	if (myxls != null) {try {myxls.close();} catch (IOException e) {e.printStackTrace();}}
	        
	        FileOutputStream out = new FileOutputStream(new File(ClassSessionController.attendance_excel));
	        if (workbook != null) {
	        try {           
	        	workbook.write(out);
	        	workbook.close();} 
	        catch (IOException e) {e.printStackTrace();}}
	        try {           
	            out.flush();
	            out.close();} 
	        catch (IOException e) {e.printStackTrace();}

	    }
	}
	ArrayList<Row> studentsList = new ArrayList<Row>();
	void importStudents() throws IOException {
		FileInputStream fi = null;
		Workbook wb = null;
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

	    
	}
	
	void avail_attendances() throws IOException {
		
		day_spinner.setDisable(true);
		exp_month.setDisable(true);
		exp_all.setDisable(true);
		
	    ArrayList<String> month = new ArrayList<String>();

        FileInputStream myxls = null;
	    Workbook workbook = null;
	       
		
	    try {
	       
	        myxls = new FileInputStream(ClassSessionController.attendance_excel);
		    workbook = new HSSFWorkbook(myxls);

	    for (int i=0; i<workbook.getNumberOfSheets(); i++) {
	        month.add( workbook.getSheetName(i) );

	    }
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	    	if (myxls != null) {try {myxls.close();} catch (IOException e) {e.printStackTrace();}}
	        if (workbook != null) {try {workbook.close();} 
	        catch (IOException e) {e.printStackTrace();}}
	    }

	    items.clear();
		

		if (month.size() > 0) {
			try {
				Sheet sheet = workbook.getSheetAt(0);
				if (sheet.getLastRowNum() > 0) {
					for (String s: month) {
						items.add(s);
					}

					date_combo.setValue(items.get(0));
					comboAction();
				}
				else {
					items.clear();
					date_combo.setPromptText(attListPromptText);
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		else {
			date_combo.setPromptText(attListPromptText);
		}


		// Set the ComboBox to use the items list


	}

	@FXML	
	void comboAction() {
		days = FXCollections.observableArrayList();

		
	    date = date_combo.getValue();
	    

        FileInputStream myxls = null;
	    Workbook workbook = null;
	       
		if (date != null && items.contains(date)) {
	    try {
	       
	        myxls = new FileInputStream(ClassSessionController.attendance_excel);
		    workbook = new HSSFWorkbook(myxls);

	        Sheet sheet = workbook.getSheet(date);
	        Row row = sheet.getRow(0);
	        int r = row.getLastCellNum();
	        for (int j = 5; j < r ; j++ ) {
	        	if (row.getCell(j) != null) {
	        		Cell c0 = row.getCell(j);
	        		days.add(c0.getStringCellValue());
	        	}
	        }
	    
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	    	if (myxls != null) {try {myxls.close();} catch (IOException e) {e.printStackTrace();}}
	        if (workbook != null) {try {workbook.close();} 
	        catch (IOException e) {e.printStackTrace();}}
	    }

		if (days.size() > 0) {
			day_spinner.setDisable(false);
			exp_month.setDisable(false);
			exp_all.setDisable(false);
			valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(days);			


		    //display_att();

		    day_spinner.setValueFactory(valueFactory);
		    display_att();

		}

	}
	    
	}
	void display_att() {
		view_gridpane.getChildren().clear();
		
		String day = day_spinner.getValue();

		view_gridpane.getChildren().clear();
	    ArrayList<String> names = new ArrayList<String>();
	    ArrayList<String> ins = new ArrayList<String>();

        FileInputStream myxls = null;
	    Workbook workbook = null;
	    int current_att = 0;
	       

	    try {
	       
	        myxls = new FileInputStream(ClassSessionController.attendance_excel);
		    workbook = new HSSFWorkbook(myxls);

	        Sheet sheet = workbook.getSheet(date);
	        Row row = sheet.getRow(0);
	        int r = sheet.getLastRowNum();
	        int lc = row.getLastCellNum();
	        for (int j = 5; j < lc ; j++ ) {
	        	if (row.getCell(j) != null) {
	        		Cell c0 = row.getCell(j);
	        		if (c0.getStringCellValue().equals(day)) {
	        			current_att = c0.getColumnIndex();
	        			break;
	        		}
	        }
	        }
	        for (int i = 0 ; i < r ; i++) {
	        	row = sheet.getRow(i+1);
	        	Cell c0 = row.getCell(1);
	        	names.add(c0.getStringCellValue());
	        	if (row.getCell(current_att) != null) {
	        		Cell c1 = row.getCell(current_att);
	        		if (c1.getStringCellValue().length() > 7) {
	        			ins.add(c1.getStringCellValue());
	        		}
		        	else {
		        		ins.add("Absent");
		        	}
	        		
	        	}
	        	else {
	        		ins.add("Absent");
	        	}
	        }
	    
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	    	if (myxls != null) {try {myxls.close();} catch (IOException e) {e.printStackTrace();}}
	        if (workbook != null) {try {workbook.close();} 
	        catch (IOException e) {e.printStackTrace();}}
	    }
	    for (int i = 0; i < names.size() ; i++) {
	    	Label name = new Label(names.get(i));
    		Label tIn = new Label();
    		Label stat = new Label();
	    	if (ins.get(i).equals("Absent") || ins.get(i).length() == 0) {
	    		tIn.setText("Absent");
	    		stat.setText("Absent");
	    	}
	    	else {
	    		String t = ins.get(i).substring(0, 8);
	    		String s = ins.get(i);
	    		
	    		tIn.setText(t);
	    		stat.setText(s.substring(10, s.length()-1));
	    	}
	    	name.setFont(new Font("Arial black",15));
	    	tIn.setFont(new Font("Arial black",15));
			stat.setFont(new Font("Arial black",15));
			
			name.setAlignment(Pos.CENTER_LEFT);
			tIn.setAlignment(Pos.CENTER_LEFT);
			stat.setAlignment(Pos.CENTER_LEFT);
			
			name.setMinHeight(30);
			tIn.setMinHeight(30);
			stat.setMinHeight(30);
			
			name.setMaxHeight(30);
			tIn.setMaxHeight(30);
			stat.setMaxHeight(30);
			
			view_gridpane.add(name, 0, i);
			view_gridpane.add(tIn, 1, i);
			view_gridpane.add(stat, 2, i);
	    	
	    }
	
	}
    @FXML
    void export_all(ActionEvent event) {
    	attendanceGPane.setDisable(true);
    	File selectedDirectory = directoryChooser.showSaveDialog(SplashController.stage);
     	if (selectedDirectory != null) {
                export(0, selectedDirectory.getAbsolutePath());
     	}
     	attendanceGPane.setDisable(false);
    }

    @FXML
    void export_month(ActionEvent event) {
    	attendanceGPane.setDisable(true);
     	File selectedDirectory = directoryChooser.showSaveDialog(SplashController.stage);
     	if (selectedDirectory != null) {
     		export(1, selectedDirectory.getAbsolutePath());
     	}
     	attendanceGPane.setDisable(false);
    }
    void export(int mode, String loc) {
    	FileInputStream myxls = null;
	    Workbook workbook = null;
	    ArrayList<Sheet> sheets = new ArrayList<Sheet>();
	       

	    try {
	       
	        myxls = new FileInputStream(ClassSessionController.attendance_excel);
		    workbook = new HSSFWorkbook(myxls);

	        if (mode == 1) {
	        	Sheet sheet = workbook.getSheet(date);
	        	sheets.add(sheet);

	        }
	        else {
	        	for (Sheet sh: workbook) {
	        		sheets.add(sh);
	        	}
	        }

	        
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	    	if (myxls != null) {try {myxls.close();} catch (IOException e) {e.printStackTrace();}}
	        if (workbook != null) {try {workbook.close();} 
	        catch (IOException e) {e.printStackTrace();}}
	    }
	    try {
	    	workbook = new HSSFWorkbook();
	    	Sheet s = sheets.get(0);
	    	Row r = s.getRow(0);
	    	int lc = r.getLastCellNum()-1;
	    	for (int i = 0 ; i < sheets.size(); i++) {
	    		Sheet sh = sheets.get(i);
	    		Sheet sheet = workbook.createSheet(sh.getSheetName());
	    		
	    		CellStyle wrapStyle = workbook.createCellStyle();
	    		wrapStyle.setWrapText(true);
	    		wrapStyle.setAlignment(HorizontalAlignment.CENTER);
	    		wrapStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    		
	    		Row head = sheet.createRow(0);
	    		
	    		Cell course = head.createCell(0); 
	            course.setCellValue(ClassSessionController.course + ",\n" +ClassSessionController.subject);
	            course.setCellStyle(wrapStyle);
	            String sch = "";
	            for (int h = 0; h < scheds.size(); h++) {
	            	sch += sch + scheds.get(h) + ",\n";
	            }
	            Cell schedules = head.createCell(1);
	            schedules.setCellStyle(wrapStyle);
	            schedules.setCellValue("Schedules:\n" +sch);

	            Cell month = head.createCell(2);
	            month.setCellStyle(wrapStyle);
	            month.setCellValue("Attendance for:\n" +sh.getSheetName());
	            
	            sheet.autoSizeColumn(0);
	            sheet.autoSizeColumn(1);
	            sheet.autoSizeColumn(2);
	            

	    		int rowIndex = 1;
	    		ArrayList<Row> sortedRow = new ArrayList<Row>();
	    		int lastRow = sh.getLastRowNum();
	    		for (int row = 1; row <= lastRow; row++) {
	    			sortedRow.add(sh.getRow(row));
	    		}
	    		sortedRow.sort(Comparator.comparing(cells -> cells.getCell(2).getStringCellValue()));
	    		sortedRow.add(0,sh.getRow(0));
	    		for (Row rw: sortedRow) {
	    			Row row = sheet.createRow(rowIndex);
	    			for (int cellIndex = 0 ; cellIndex < lc ; cellIndex++) {
	    				Cell newCell = null;
	    				
	    				switch (cellIndex) {
	    				  case 0:
	    					  newCell = row.createCell(1);
	    					    break;
	    				  case 1:
	    					  newCell = row.createCell(2);
	    					    break;
	    				  case 2:
	    					  newCell = row.createCell(0);
	    					    break;
	    				  default:
	    					  newCell = row.createCell(cellIndex);
	    			
	    				}
	    				Cell cll = rw.getCell(cellIndex+1);
	    				if (cll != null && !cll.getStringCellValue().equals("")) {
		    				newCell.setCellValue(cll.getStringCellValue());
	    				}
	    				else {
		    				newCell.setCellValue("Absent");
	    				}

	
	    			}rowIndex++;
	    			
	    		}
	    	}

	    	
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	    	if (workbook != null) 
	    	{
		        try (FileOutputStream outputStream = new FileOutputStream(loc)) {
		            workbook.write(outputStream);
		            outputStream.flush();
		            outputStream.close();
		        }
		        catch (Exception e) {
		        	e.printStackTrace();
		        }
		        try {
		            workbook.close();
		        }
		        catch (Exception e) {
		        	e.printStackTrace();
		        }
	    	}

	    	
	    }
    }
    
}

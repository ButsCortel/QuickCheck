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

import javafx.animation.Animation;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class AttendanceGUIController implements Initializable{
	@FXML
	ComboBox<WebCamInfo> cbCameraOptions;

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
    private Label in_status;
    @FXML
    private Label time_label;
    @FXML
    private Label date_label;



	@FXML
	ImageView imgWebCamCapturedImage;

	    @FXML
	    Label course_label;

	    @FXML
	    Label subject_label;	

	    @FXML
	    GridPane take_gridpane;

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
    
    static Stage attendancegui_window;
    private int webcam_num = 0;
    
    private String grace_time = "";
    private String time_in = "";
	

	private BufferedImage grabbedImage;
	private Webcam selWebCam = null;
	private boolean stopCamera = false;
	private boolean camOpen = false;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	static ArrayList<String> scheds_day;

	private String cameraListPromptText = "Choose Camera";
	private String last = "";
	private String[] results;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initClock();

		name_label.setText("");
		name_label.setMaxWidth(260);
		timein_label.setText("");
		in_status.setText("");
    	attendancePane.setVisible(true);  	
    	webcampane.setVisible(false);
    	
    	take_attendance.setVisible(true);
    	view_attendance.setVisible(false);
    	

		course_label.setText(ClassSessionController.course);
		subject_label.setText(ClassSessionController.subject);

		
	}
	private void initClock() {
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy\nEEE");
		
		LocalDateTime myDateObj = LocalDateTime.now();
        //SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        // DateFormat = new SimpleDateFormat("MMM dd, yyyy EEE");
        //Date myDateObj = new Date();
        final String date = dateFormat.format(myDateObj);
        date_label.setText(date);

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
            LocalTime currentTime = LocalTime.now();
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
									        if (results.length > 3 && results[results.length -1].chars().allMatch(Character::isDigit) && results[results.length -1].length() == 4 )
									        {
									        	
									        	try {


													if (checkStudent(results[results.length -1]).size() ==  4) {
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
	        	student_info.add(c0.getStringCellValue());
	        	student_info.add(c1.getStringCellValue());
	        	student_info.add(c2.getStringCellValue());
	        	student_info.add(c3.getStringCellValue());
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
		try
		{
			fi = new FileInputStream(ClassSessionController.attendance_excel);
			wb = new HSSFWorkbook(fi);
		Sheet sh = wb.getSheet(df.format(dateobj));
	    int endRow = sh.getLastRowNum();
	    
	    Row r = sh.getRow(0);
	    int maxCell=  r.getLastCellNum();


	    for (int i = 0; i <= endRow; i++) {
	        Cell c0 = sh.getRow(i).getCell(0);

	        if (c0.getStringCellValue().equals(info.get(0))) {
	        	dup = true;
	        	gRow = i;

	        }

	        
	    }
	    if (dup) {
        		row = sh.getRow(gRow);
        		if (row.getCell(maxCell - 1) == null) {
        			row.createCell(maxCell - 1).setCellValue(ti);
        			name_label.setText(info.get(1));
        			timein_label.setText(tid);
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
		       	row.createCell(maxCell - 1).setCellValue(ti);
    			name_label.setText(info.get(1));
    			
    			timein_label.setText(tid);
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
    		attendancegui_window = new Stage();
    		Parent root = FXMLLoader.load(App.class.getResource("AttendanceGUI.fxml"));
			JFXDecorator decorator = new JFXDecorator(attendancegui_window , root, false, false, true);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene att_scene = new Scene(decorator, 810, 460);
			att_scene.getStylesheets().add(uri) ;
			attendancegui_window.setScene(att_scene);
			attendancegui_window.setTitle("CLASS!");
			attendancegui_window.initStyle(StageStyle.UNDECORATED);
			attendancegui_window.setResizable(false);

			
			attendancegui_window.show();
			
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
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void checkCurrentAttendance() throws IOException {
		take_gridpane.getChildren().clear();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM-yyyy");
		//DateTimeFormatter timein = DateTimeFormatter.ofPattern("hh:mm aa");
		
		LocalDateTime dateobj = LocalDateTime.now();
		FileInputStream fi = null;
		Workbook wb = null;
		ArrayList<String> students_name = new ArrayList<String>();
		ArrayList<String> students_timein = new ArrayList<String>();
		try 
		{
		fi = new FileInputStream(ClassSessionController.attendance_excel);
		wb = new HSSFWorkbook(fi);

		
		Sheet sh = wb.getSheet(df.format(dateobj)) ;
		ArrayList <Row> rows = new ArrayList <Row>();
		Row rw = sh.getRow(0);
	    int maxCell=  rw.getLastCellNum();
        //copy all rows to temp
		int r =0;
		for (Row myrows: sh) {
			if (r != 0) {
				if (myrows.getCell(maxCell -1) != null) {
				rows.add(myrows);	
				}
				
			}
			r++;
			
		}
        rows.sort(Comparator.comparing(cells -> cells.getCell(maxCell-1).getStringCellValue()));
        for (Row myrow: rows) {
            Cell c0 = myrow.getCell(1);
            Cell c1 = myrow.getCell(maxCell-1);
            
            if (c1 != null) {
            	String t = c1.getStringCellValue();
            	t = t.substring(9, t.length());
            	students_name.add(c0.getStringCellValue());
            	students_timein.add(t);
            }
        }}
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

	    int c =0;
	    for (String name : students_name) {
	    	Label rs = new Label(Integer.toString(c +1)+".");
	    	Label n = new Label(name);
	    	Label t = new Label(students_timein.get(c));
	    	
	    	rs.setFont(new Font("Arial black",15));
	    	n.setFont(new Font("Arial black",15));
			t.setFont(new Font("Arial black",15));
			
			rs.setAlignment(Pos.CENTER);
			n.setAlignment(Pos.CENTER);
			t.setAlignment(Pos.CENTER);
			
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
		disposeCamera();

		
		attendancegui_window.close();
		ClassSessionController.class_window.show();
    }
    @FXML
    void home(MouseEvent event) {
    	disposeCamera();
    	attendancegui_window.close();
    	ClassGUIController.classgui_window.show();

    }
    @FXML
    void takeAttendance(ActionEvent event) {
    	try {
			createAttendance();
			checkCurrentAttendance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	attendancePane.setVisible(false);  	
    	webcampane.setVisible(true);
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
    	}
    	
    	
    	take_attendance.setVisible(false);
    	view_attendance.setVisible(true);
    }

    @FXML
    void viewAttendance(ActionEvent event) {
    	stopCamera();
       	attendancePane.setVisible(true);  	
    	webcampane.setVisible(false);
    	
    	take_attendance.setVisible(true);
    	view_attendance.setVisible(false);
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
	       
		
	    try {
	       
	        myxls = new FileInputStream(ClassSessionController.attendance_excel);
		       workbook = new HSSFWorkbook(myxls);
		       Sheet sheet = workbook.getSheet(df.format(dateobj));
	        if (workbook.getNumberOfSheets() != 0) {
	            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

	               if (workbook.getSheetName(i).equals(df.format(dateobj))) {
	            	   
	                    sheet = workbook.getSheet(df.format(dateobj));
	                    Row row = sheet.getRow(0);
	                    int maxCell = row.getLastCellNum();
	                    Cell c0 = row.getCell(maxCell - 1);
	                    if (!c0.getStringCellValue().equals(day.format(dateobj))) {
	                    	Cell c1 = row.createCell(maxCell);
	                    	c1.setCellValue(day.format(dateobj));
	                    }
	                    
	                } else {
	                	sheet = workbook.createSheet(df.format(dateobj));
	                    Row row = sheet.createRow(0); 
	        	        
	        	        Cell cell0 = row.createCell(0);
	        	        cell0.setCellValue("CODE");

	        	        Cell cell1 = row.createCell(1);
	        	        cell1.setCellValue("NAME");
	        	        
	        	        Cell cell2 = row.createCell(2);
	        	        cell2.setCellValue("ID NO.");
	        	        
	        	        Cell cell3 = row.createCell(3);
	        	        cell3.setCellValue("COURSE CODE/ GRADE LEVEL");
	        	        
	        	        Cell cell4 = row.createCell(4);
	        	        cell4.setCellValue(day.format(dateobj));
	                }
	            }
	        }
	        else {
	            // Create new sheet to the workbook if empty
	            sheet = workbook.createSheet(df.format(dateobj));
	            Row row = sheet.createRow(0); 
		        
		        Cell cell0 = row.createCell(0);
		        cell0.setCellValue("CODE");

		        Cell cell1 = row.createCell(1);
		        cell1.setCellValue("NAME");
		        
		        Cell cell2 = row.createCell(2);
		        cell2.setCellValue("ID NO.");
		        
		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue("COURSE CODE/ GRADE LEVEL");
    	        Cell cell4 = row.createCell(4);
    	        cell4.setCellValue(day.format(dateobj));
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
	
		/*double x,y =0;
		@FXML
	    void dragged(MouseEvent event) {
	    	Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
	    	stage.setX(event.getScreenX() -x);
	    	stage.setY(event.getScreenY() -y);
	    }

	    @FXML
	    void pressed(MouseEvent event) {
	    	x = event.getSceneX();
	    	y = event.getSceneY();

	    }*/
	



	/*void existing_attendance() throws IOException {
		FileInputStream fi = new FileInputStream(ClassSessionController.student_excel);
		Workbook wb = new HSSFWorkbook(fi);
		for (int i=1; i<=wb.getNumberOfSheets(); i++) {
			attendance_combo.getItems().add(wb.getSheetName(i));
		}
	    
	    wb.close();
	    fi.close();

	   
	}*/

}

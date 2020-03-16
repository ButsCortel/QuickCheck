package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


public class ClassSessionController implements Initializable{

	static String path;
	static String student_excel;
	static String attendance_excel;
	static String test_excel;
	static String course;
	static String subject;
	static int rowIndex = 0;
	int dispStudents = 0;
	boolean student_selected = false;
	//static Stage class_window;
	private String scode = "";
	private ArrayList<Integer> sh = null;
	private ArrayList<Integer> rw = null;
	


    @FXML
    private Label course_label;

    @FXML
    private Label subject_label;
    @FXML
    private JFXButton add;

    @FXML
    private JFXButton del;
    @FXML
    private GridPane gridpane;
    
    @FXML
    private JFXButton start_attendance_button;

    @FXML
    private JFXButton start_test__button;
    


	public void startClass() {
    	try {
    		//class_window = new Stage();
    		Parent root = FXMLLoader.load(App.class.getResource("ClassSessionGUI.fxml"));
			//JFXDecorator decorator = new JFXDecorator(class_window , root, false, false, true);
			//decorator.setCustomMaximize(true); 
			//String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(root);
			//class_scene.getStylesheets().add(uri) ;
			SplashController.stage.setScene(class_scene);
			//class_window.setTitle("QuickCheck");
			//class_window.initStyle(StageStyle.UNDECORATED);
			//class_window.setResizable(false);

			//class_window.show();
			
	
			/*class_window.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
		        	AlertBoxController.label_text = "Are you sure you want to exit?";
		        	if(AlertBoxController.display("Exit")) {
				        Platform.exit();
				        System.exit(0);
		        	}
		        	else {
		        		t.consume();
		        	}

			    }
			});*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//gridpane.setFillWidth(arg0, arg1);
    	classSPane.setOpacity(0);
    	classSPane.setDisable(true);
		course_label.setText(course.replaceAll("_", " "));
		subject_label.setText(subject.replaceAll("_", " "));
		add.setAlignment(Pos.CENTER);
		del.setAlignment(Pos.CENTER);
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), classSPane);
    	fadeOutTransition.setFromValue(0);
    	fadeOutTransition.setToValue(1);
    	fadeOutTransition.play();
    	
    	
    	fadeOutTransition.setOnFinished((e) -> {
    		try {
    			checkStudents();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}


    		classSPane.setDisable(false);
    	});




		
	}
	public void addStudent() {
		classSPane.setDisable(true);
		AddStudentController student = new AddStudentController();
		student.newStudent();
		try {
			sortSheet();
			checkStudents();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}student_selected = false;
		classSPane.setDisable(false);
	}
	void checkStudents() throws IOException {
	    FileInputStream fi = null;
		Workbook wb = null;
		ArrayList<String> students_code = new ArrayList<String>();
		ArrayList<String> students_name = new ArrayList<String>();
		ArrayList<String> students_sex = new ArrayList<String>();
		ArrayList<String> students_id = new ArrayList<String>();
		ArrayList<String> students_course= new ArrayList<String>();
		ArrayList<Label> students_display = new ArrayList<Label>();
		students_display.add(null);
		students_display.add(null);
		students_display.add(null);
		students_display.add(null);
		students_display.add(null);
		students_display.add(null);
	try {
		
		gridpane.getChildren().clear();
	    fi = new FileInputStream(student_excel);
		wb = new HSSFWorkbook(fi);


		Sheet sh = wb.getSheetAt(0);
	    int starRow = sh.getFirstRowNum();
	    int endRow = sh.getLastRowNum();
	    if (endRow > 0) {
	    	start_attendance_button.setDisable(false);

	    	//start_test__button.setDisable(false);
	    	
	    }
	    else {
	    	start_attendance_button.setDisable(true);

	    	//start_test__button.setDisable(true);
	    }


	    for (int i = starRow + 1; i <= endRow; i++) {
	        Cell c0 = wb.getSheetAt(0).getRow(i).getCell(0);
	        students_code.add(c0.getStringCellValue());

	        Cell c1 = wb.getSheetAt(0).getRow(i).getCell(1);
	        students_name.add(c1.getStringCellValue());
	        
	        Cell c2 = wb.getSheetAt(0).getRow(i).getCell(2);
	        students_sex.add(c2.getStringCellValue());
	        
	        Cell c3 = wb.getSheetAt(0).getRow(i).getCell(3);
	        students_id.add(c3.getStringCellValue());
	        
	        Cell c4 = wb.getSheetAt(0).getRow(i).getCell(4);
	        students_course.add(c4.getStringCellValue());


	    } 
	}
    catch(Exception e)
    {
    	e.printStackTrace();
    }
	finally {
		if (fi != null) {
			try {fi.close();}catch(IOException e) {e.printStackTrace();}
			
		}
		if (wb != null) {
			try {wb.close();}catch(IOException e) {e.printStackTrace();}
		}
		
	    
	    
	}

	    int c = 0;
	    for (String name: students_name) {
	    	Label row = new Label(Integer.toString(c+1) + ".");
	    	Label scode = new Label(students_code.get(c));
	    	Label sname = new Label(name);
	    	Label ssex = new Label(students_sex.get(c));
	    	Label sid = new Label(students_id.get(c));
	    	Label scourse = new Label(students_course.get(c));
	    	
	    	row.setFont(new Font("Arial Black",15));
	    	scode.setFont(new Font("Arial Black",15));
			sname.setFont(new Font("Arial black",15));
			ssex.setFont(new Font("Arial black",15));
			sid.setFont(new Font("Arial black",15));
			scourse.setFont(new Font("Arial black",15));
			
			row.setTextFill(Color.BLACK);
			scode.setTextFill(Color.BLACK);
			sname.setTextFill(Color.BLACK);
			ssex.setTextFill(Color.BLACK);
			sid.setTextFill(Color.BLACK);
			scourse.setTextFill(Color.BLACK);
			
			sname.setMaxHeight(Control.USE_PREF_SIZE);
			sname.setMinHeight(30);
			sname.setMaxWidth(250);
			sname.setMinWidth(250);
			sname.setAlignment(Pos.CENTER_LEFT);
			
			ssex.setMaxHeight(Double.MAX_VALUE);
			ssex.setMinHeight(sname.getPrefHeight());
			ssex.setMaxWidth(40);
			ssex.setMinWidth(40);
			ssex.setAlignment(Pos.CENTER_LEFT);
			
			row.setMaxHeight(Double.MAX_VALUE);
			row.setMinHeight(sname.getPrefHeight());
			row.setMaxWidth(40);
			row.setMinWidth(40);
			row.setAlignment(Pos.CENTER);
			
			scode.setMaxHeight(Double.MAX_VALUE);
			scode.setMinHeight(sname.getPrefHeight());
			scode.setMaxWidth(75);
			scode.setMinWidth(75);
			scode.setAlignment(Pos.CENTER_LEFT);
			
			sid.setMaxHeight(Double.MAX_VALUE);
			sid.setMinHeight(sname.getPrefHeight());
			sid.setMaxWidth(135);
			sid.setMinWidth(135);
			sid.setAlignment(Pos.CENTER_LEFT);
			
			scourse.setMaxHeight(Double.MAX_VALUE);
			scourse.setMinHeight(sname.getPrefHeight());
			scourse.setMaxWidth(160);
			scourse.setMinWidth(160);
			scourse.setAlignment(Pos.CENTER_LEFT);
			
			/*students_display.set(0, row);
			students_display.set(1, scode);
			students_display.set(2, sname);
			students_display.set(3, sid);
			students_display.set(4, scourse);*/
			
			
				gridpane.add(row, 0, c);
				gridpane.add(scode, 1, c);
				gridpane.add(sname, 2, c);
				gridpane.add(ssex, 3, c);
				gridpane.add(sid, 4, c);
				gridpane.add(scourse, 5, c);


			c++;

	    }

	}
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
        		node.setStyle("-fx-background-color: 	#D3D3D3;");
        	}
        	else {
        		node.setStyle(null);
        	}
        }student_selected = true;

    	}
    	}
    

    }
    public void removeStudent() throws FileNotFoundException {
    	classSPane.setDisable(true);
	    if (student_selected)
	    {
	        AlertBoxController.label_text = "Remove Student?";
	    	if(AlertBoxController.display("Delete Student")) {
	    	try {
				deleteRow(0 , student_excel,  rowIndex + 1, true);				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	check_scode();
	    	try {
	    		for (int i = 0; i < sh.size() ; i++) {
					deleteRow(sh.get(i) , attendance_excel,  rw.get(i) , false );
	    		}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	}
	    	
	    }classSPane.setDisable(false);
	    
    }
    public void deleteRow(int s, String excelPath, int rowNo, boolean t) throws IOException {
    	scode = "";
        Workbook workbook = null;
        Sheet sheet = null;
        FileInputStream file = null;

            try {
                file = new FileInputStream(new File(excelPath));
                workbook = new HSSFWorkbook(file);
                sheet = workbook.getSheetAt(s);
                if (t) {
                    Cell c0 = workbook.getSheetAt(0).getRow(rowNo).getCell(0);
                    scode = c0.getStringCellValue();
                }


                if (sheet == null) {
                    
                }
                int lastRowNum = sheet.getLastRowNum();
                if (rowNo >= 0 && rowNo < lastRowNum) {
                    sheet.shiftRows(rowNo + 1, lastRowNum, -1);
                    Row removingRow=sheet.getRow(lastRowNum);
                    sheet.removeRow(removingRow);
                    
                }
                if (rowNo == lastRowNum) {
                    Row removingRow=sheet.getRow(rowNo);
                    if(removingRow != null) {
                        sheet.removeRow(removingRow);
                    }
                }
                
            } catch(Exception e) {
                e.printStackTrace();
            }
            finally {
            	if (file != null) {
            		try {file.close();} catch (IOException e) {e.printStackTrace();}
            	}
            	
                FileOutputStream outFile = new FileOutputStream(new File(excelPath));
            	if (file != null) {
            		try {
            		workbook.write(outFile);
                    workbook.close();
                    } 
            		catch (IOException e) {
            			e.printStackTrace();
            			}
            	}

                outFile.flush();
                outFile.close();


            	sortSheet();
				checkStudents();
            }
            student_selected = false; 
            
            
    	

    }


    @FXML
    public void import_list() {
    	classSPane.setDisable(true);
    	ImportClassController import_class = new ImportClassController();
    	import_class.importList();
    	try {
   			checkStudents();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	classSPane.setDisable(false);
    }
    

	public static void sortSheet() throws IOException {
		FileInputStream myxls = null;

		Workbook wb = null;
		try {
		myxls = new FileInputStream(student_excel);
		

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
        }
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		finally {
		if (myxls != null) {
			try {myxls.close();} catch (IOException e) {e.printStackTrace();}
			}

        FileOutputStream output_file =new FileOutputStream(new File(student_excel));
        if (wb != null) {
        	try { 	       
        		wb.write(output_file);
        		wb.close();
        		} 
        	catch (IOException e) {
        			e.printStackTrace();
        			}

        }
	       output_file.flush();
	       output_file.close();
		}

    }
	

    private static void removeAllRows(Sheet sheet) {
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                sheet.removeRow(sheet.getRow(i+1));
            }
        }
    @FXML
    private AnchorPane classSPane;
    @FXML
    void start_attendance(ActionEvent event) {
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), classSPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0);
    	fadeOutTransition.play();
    	classSPane.setDisable(true);
    	
    	fadeOutTransition.setOnFinished((e) -> {
        	AttendanceGUIController start = new AttendanceGUIController(); 	
        	//class_window.hide();
        	start.attendanceGUIWindow(); 
    	});

    	


    }

    @FXML
    void back(ActionEvent event) {
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), classSPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0.5);
    	fadeOutTransition.play();
    	classSPane.setDisable(true);
    	
    	fadeOutTransition.setOnFinished((e) -> {
        	ClassGUIController startClass = new ClassGUIController();
        	startClass.classGUIWindow();
    	});
    	

    }

    @FXML
    void home(MouseEvent event) {
    	
    	FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(0300), classSPane);
    	fadeOutTransition.setFromValue(1.0);
    	fadeOutTransition.setToValue(0.5);
    	fadeOutTransition.play();
    	classSPane.setDisable(true);
    	
    	fadeOutTransition.setOnFinished((e) -> {
        	ClassGUIController startClass = new ClassGUIController();
        	startClass.classGUIWindow();
    	});
    }
    public void check_scode()
    {	
    	sh = new ArrayList<Integer>();
    	rw = new ArrayList<Integer>();
    	
		FileInputStream fi = null;
		Workbook wb = null;
		int she = 0;
		try
		{fi = new FileInputStream(attendance_excel);
		wb = new HSSFWorkbook(fi);
		for (Sheet sheet: wb) {
			
			for (Row row: sheet) {
				Cell c0 = row.getCell(0);
				
				if (c0 != null && c0.getStringCellValue().equals(scode)) {
					sh.add(she);
					rw.add(c0.getRowIndex());
				}
			} she++;
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
    @FXML
    private JFXTextField searchB;
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
        student_selected = false;
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
}


	


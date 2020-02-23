package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClassSessionController implements Initializable{
	static String path;
	static String course;
	static String subject;
	static int rowIndex = 0;
	int dispStudents = 0;
	boolean student_selected = false;
	

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



	public void startClass() {
    	try {
    		Stage class_window = new Stage();
    		Parent root = FXMLLoader.load(App.class.getResource("ClassSessionGUI.fxml"));
			JFXDecorator decorator = new JFXDecorator(class_window , root, false, false, true);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(decorator, 810, 460);
			class_scene.getStylesheets().add(uri) ;
			class_window.setScene(class_scene);
			class_window.setTitle("QuickCheck");
			class_window.initStyle(StageStyle.UNDECORATED);
			class_window.setResizable(false);
			class_window.show();
			
	
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			checkStudents();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		course_label.setText(course);
		subject_label.setText(subject);
		add.setAlignment(Pos.BASELINE_LEFT);
		del.setAlignment(Pos.BASELINE_LEFT);

		
	}
	public void addStudent() {
		AddStudentController student = new AddStudentController();
		student.newStudent();
		try {
			checkStudents();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}student_selected = false;
	}
	void checkStudents() throws IOException {
		gridpane.getChildren().clear();
	    FileInputStream fi = new FileInputStream(path);
		ArrayList<String> students_code = new ArrayList<String>();
		ArrayList<String> students_name = new ArrayList<String>();
		ArrayList<String> students_id = new ArrayList<String>();
		ArrayList<String> students_course= new ArrayList<String>();
		ArrayList<Label> students_display = new ArrayList<Label>();
		students_display.add(null);
		students_display.add(null);
		students_display.add(null);
		students_display.add(null);
		students_display.add(null);
	    Workbook wb = new HSSFWorkbook(fi);
	    Sheet sh = wb.getSheetAt(0);
	    int starRow = sh.getFirstRowNum();
	    int endRow = sh.getLastRowNum();


	    for (int i = starRow + 1; i <= endRow; i++) {
	        Cell c0 = wb.getSheetAt(0).getRow(i).getCell(0);
	        students_code.add(c0.getStringCellValue());

	        Cell c1 = wb.getSheetAt(0).getRow(i).getCell(1);
	        students_name.add(c1.getStringCellValue());
	        
	        Cell c2 = wb.getSheetAt(0).getRow(i).getCell(2);
	        students_id.add(c2.getStringCellValue());
	        
	        Cell c3 = wb.getSheetAt(0).getRow(i).getCell(3);
	        students_course.add(c3.getStringCellValue());


	    } 
	    wb.close();
	    int c = 0;
	    for (String name: students_name) {
	    	Label row = new Label(Integer.toString(c+1) + ".");
	    	Label scode = new Label(students_code.get(c));
	    	Label sname = new Label(name);
	    	Label sid = new Label(students_id.get(c));
	    	Label scourse = new Label(students_course.get(c));
	    	
	    	row.setFont(new Font("Arial Black",15));
	    	scode.setFont(new Font("Arial Black",15));
			sname.setFont(new Font("Arial black",15));
			sid.setFont(new Font("Arial black",15));
			scourse.setFont(new Font("Arial black",15));
			
			row.setTextFill(Color.WHITE);
			scode.setTextFill(Color.WHITE);
			sname.setTextFill(Color.WHITE);
			sid.setTextFill(Color.WHITE);
			scourse.setTextFill(Color.WHITE);
			
			sname.setMaxHeight(Control.USE_PREF_SIZE);
			sname.setMinHeight(30);
			sname.setMaxWidth(230);
			sname.setMinWidth(230);
			sname.setAlignment(Pos.CENTER);
			
			row.setMaxHeight(Double.MAX_VALUE);
			row.setMinHeight(sname.getPrefHeight());
			row.setMaxWidth(50);
			row.setMinWidth(50);
			row.setAlignment(Pos.CENTER);
			
			scode.setMaxHeight(Double.MAX_VALUE);
			scode.setMinHeight(sname.getPrefHeight());
			scode.setMaxWidth(70);
			scode.setMinWidth(70);
			scode.setAlignment(Pos.CENTER);
			
			sid.setMaxHeight(Double.MAX_VALUE);
			sid.setMinHeight(sname.getPrefHeight());
			sid.setMaxWidth(120);
			sid.setMinWidth(120);
			sid.setAlignment(Pos.CENTER);
			
			scourse.setMaxHeight(Double.MAX_VALUE);
			scourse.setMinHeight(sname.getPrefHeight());
			scourse.setMaxWidth(120);
			scourse.setMinWidth(120);
			scourse.setAlignment(Pos.CENTER);
			
			students_display.set(0, row);
			students_display.set(1, scode);
			students_display.set(2, sname);
			students_display.set(3, sid);
			students_display.set(4, scourse);
			
			if (dispStudents == 0) {
				gridpane.add(students_display.get(0), 0, c);
				gridpane.add(students_display.get(1), 1, c);
				gridpane.add(students_display.get(2), 2, c);
				gridpane.add(students_display.get(3), 3, c);
				gridpane.add(students_display.get(4), 4, c);
			}
			else {
				if (c != rowIndex) {
					gridpane.add(students_display.get(0), 0, c);
					gridpane.add(students_display.get(1), 1, c);
					gridpane.add(students_display.get(2), 2, c);
					gridpane.add(students_display.get(3), 3, c);
					gridpane.add(students_display.get(4), 4, c);
				}
				else {
					row.setStyle("-fx-background-color: tomato;");
					scode.setStyle("-fx-background-color: tomato;");
					sname.setStyle("-fx-background-color: tomato;");
					sid.setStyle("-fx-background-color: tomato;");
					scourse.setStyle("-fx-background-color: tomato;");
					gridpane.add(students_display.get(0), 0, rowIndex);
					gridpane.add(students_display.get(1), 1, rowIndex);
					gridpane.add(students_display.get(2), 2, rowIndex);
					gridpane.add(students_display.get(3), 3, rowIndex);
					gridpane.add(students_display.get(4), 4, rowIndex);
					dispStudents = 0;
				}
			}

			c++;

	    }



	}
    public void clickGrid(MouseEvent event) {
    Node clickedNode = event.getPickResult().getIntersectedNode();

    if (clickedNode != gridpane) {
    	Node parent = clickedNode.getParent();
        while (parent != gridpane) {
            clickedNode = parent;
            parent = clickedNode.getParent();
        }
        rowIndex = GridPane.getRowIndex(clickedNode);
        dispStudents = 1;
        try {
			checkStudents();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}student_selected = true;
    	}
    

    }
    public void removeStudent() throws FileNotFoundException {
	    if (student_selected)
	    {
	    	try {
				deleteRow(0 , path,  rowIndex + 1);
				checkStudents();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	student_selected = false;
	    	
	    }
    }
    public void deleteRow(int s, String excelPath, int rowNo) throws IOException {

        Workbook workbook = null;
        Sheet sheet = null;
        AlertBoxController.label_text = "Remove Student?";
    	if(AlertBoxController.display("Delete Student")) {
            try {
                FileInputStream file = new FileInputStream(new File(excelPath));
                workbook = new HSSFWorkbook(file);
                sheet = workbook.getSheetAt(s);
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
                file.close();
                FileOutputStream outFile = new FileOutputStream(new File(excelPath));
                workbook.write(outFile);
                workbook.close();
                outFile.close();

            } catch(Exception e) {
                
            } 
            
    	}

    }
    
    
}

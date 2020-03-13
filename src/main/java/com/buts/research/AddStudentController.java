package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddStudentController implements Initializable{
	static Stage newstudent_window;
	static String course = "";
	static String subject = "";
	static String dup = "";

	@FXML
    private JFXTextField student_code;

    @FXML
    private JFXTextField student_name;

    @FXML
    private JFXTextField student_id;

    @FXML
    private JFXTextField student_course;

    @FXML
    private Label stat_label;
    
    @FXML
    private Spinner<String> student_sex;
	void newStudent() {
		try {
			newstudent_window = new Stage();
			
			Parent root = FXMLLoader.load(App.class.getResource("AddStudent.fxml"));
			//JFXDecorator decorator = new JFXDecorator(newstudent_window , root, false, false, false);
			//decorator.setCustomMaximize(true); 
			//String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(root);
			//class_scene.getStylesheets().add(uri) ;
			newstudent_window.setScene(class_scene);
			newstudent_window.setTitle("New Student");
			newstudent_window.initModality(Modality.APPLICATION_MODAL);
			newstudent_window.initStyle(StageStyle.UNDECORATED);
			newstudent_window.setResizable(false);
			newstudent_window.showAndWait();
			
		       

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeStudent(String code, String name, String id, String course, String sex){
		FileInputStream myxls = null;
	       Workbook studentsSheet = null;
	       FileOutputStream output_file = null;
		   try
		   {
		       myxls = new FileInputStream(ClassSessionController.student_excel);
		       studentsSheet = new HSSFWorkbook(myxls);
		       Sheet worksheet = studentsSheet.getSheetAt(0);
		       int lastRow=worksheet.getLastRowNum();
		       Row row = worksheet.createRow(++lastRow);
		       row.createCell(0).setCellValue(code);
		       row.createCell(1).setCellValue(name);
		       row.createCell(2).setCellValue(sex);
		       row.createCell(3).setCellValue(id);
		       row.createCell(4).setCellValue(course);
		       output_file =new FileOutputStream(new File(ClassSessionController.student_excel));  
		       studentsSheet.write(output_file);

		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		   finally {
			   if (myxls != null) {
				   try {myxls.close();} catch (IOException e) {e.printStackTrace();}
			       
			   }
			   if (output_file != null) {
				   try {output_file.flush();} catch (IOException e) {e.printStackTrace();}
				   try {output_file.close();} catch (IOException e) {e.printStackTrace();}	
			   }
			   if (studentsSheet != null) {
				   try {studentsSheet.close();} catch (IOException e) {e.printStackTrace();}
			   }
			   

		   }
		}
	public void addStudent() throws IOException {
		String code = student_code.getText().trim();
		String name = student_name.getText().replaceAll("[\\d]", "").trim();
		String id = student_id.getText().trim();
		String course = student_course.getText().trim();
		String sex = student_sex.getValue();
		if (!code.equals("") && !name.equals("") && !id.equals("") && !course.equals("") && code.length() >3 && name.length() >5 && code.chars().allMatch(Character::isDigit)) {
			if (!checkDup(code, name, id)) {
				writeStudent(code, name, id, course, sex);
				newstudent_window.close();
			}
			else {
				stat_label.setText("Duplicate " + dup + "!");
			}
			
		}
		else {
			stat_label.setText("Invalid Format!");
		}

	}
	public boolean checkDup(String cd, String n, String id) throws IOException {
		FileInputStream fi = null;
		Workbook wb = null;
		boolean result = false;
		try
		{fi = new FileInputStream(ClassSessionController.student_excel);
		wb = new HSSFWorkbook(fi);
		Sheet sh = wb.getSheetAt(0);
		
	    int starRow = sh.getFirstRowNum();
	    int endRow = sh.getLastRowNum();


	    for (int i = starRow + 1; i <= endRow; i++) {
	        Cell c0 = wb.getSheetAt(0).getRow(i).getCell(0);
	        if(c0.getStringCellValue().equals(cd)) {
	        	result = true;
	        	dup = "CODE";
	        }

	        Cell c1 = wb.getSheetAt(0).getRow(i).getCell(1);
	        if(c1.getStringCellValue().equals(n)) {
	        	result = true;
	        	dup = "NAME";
	        }
	        
	        Cell c2 = wb.getSheetAt(0).getRow(i).getCell(3);
	        if(c2.getStringCellValue().equals(id)) {
	        	result = true;
	        	dup = "ID";
	        }
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

		
		

	    return result;

	    
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> sex = FXCollections.observableArrayList("F", "M");
		 
		 
	       // Value factory.
	       SpinnerValueFactory<String> valueFactory = //
	               new SpinnerValueFactory.ListSpinnerValueFactory<String>(sex);
	      
	       // Default value
	       valueFactory.setValue("F");
	       student_sex.setValueFactory(valueFactory);
		
	}
	
	
}

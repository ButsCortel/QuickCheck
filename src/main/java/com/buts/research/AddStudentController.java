package com.buts.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddStudentController {
	static Stage newstudent_window;
	static String course = "";
	static String subject = "";
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
	void newStudent() {
		try {
			newstudent_window = new Stage();
			
			Parent root = FXMLLoader.load(App.class.getResource("AddStudent.fxml"));
			JFXDecorator decorator = new JFXDecorator(newstudent_window , root, false, false, false);
			decorator.setCustomMaximize(true); 
			String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(decorator, 360, 260);
			class_scene.getStylesheets().add(uri) ;
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

	public void writeStudent(String code, String name, String id, String course){
		   try
		   {
		       FileInputStream myxls = new FileInputStream(ClassSessionController.path);
		       Workbook studentsSheet = new HSSFWorkbook(myxls);
		       Sheet worksheet = studentsSheet.getSheetAt(0);
		       int lastRow=worksheet.getLastRowNum();
		       Row row = worksheet.createRow(++lastRow);
		       row.createCell(0).setCellValue(code);
		       row.createCell(1).setCellValue(name);
		       row.createCell(2).setCellValue(id);
		       row.createCell(3).setCellValue(course);
		       myxls.close();
		       FileOutputStream output_file =new FileOutputStream(new File(ClassSessionController.path));  
		       studentsSheet.write(output_file);
		       studentsSheet.close();
		       output_file.close();
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		}
	public void addStudent() {
		String code = student_code.getText();
		String name = student_name.getText();
		String id = student_id.getText();
		String course = student_course.getText();
		if (!code.equals("") && !name.equals("") && !id.equals("") && !course.equals("")) {
			writeStudent(code, name, id, course);
			newstudent_window.close();
		}

	}
}

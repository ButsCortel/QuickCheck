package com.buts.research;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpenSheetController implements Initializable {
	
	static Stage alert_box;
	static String tcode;
    static String[] sans;
    static String[] rans;
    static String sname = "";
    static String scourse = "";
    static String testItems = "";
    @FXML
    private ImageView qr_imageView;

    @FXML
    private GridPane gridpane;

    @FXML
    private Label name_label;

    @FXML
    private Label course_label;

    @FXML
    private Label score_label;

    @FXML
    void close(ActionEvent event) {
    	alert_box.close();
    }

	
	public static void display(String test_code, String name, String course, String items, String student_answer, String right_answer) {
		tcode = test_code;
		sname = name;
		scourse = course;
		
		testItems = items;
		sans = student_answer.split("/");
		rans = right_answer.split("/");
		
    	try {
    		alert_box = new Stage();
    		
    		Parent root = FXMLLoader.load(App.class.getResource("OpenSheet.fxml"));
			//JFXDecorator decorator = new JFXDecorator(alert_box , root, false, false, false);
			//decorator.setCustomMaximize(true); 
			//String uri = App.class.getResource("CSS.css").toExternalForm();
			Scene class_scene = new Scene(root);
			//class_scene.getStylesheets().add(uri) ;
			alert_box.setScene(class_scene);
			alert_box.setTitle("Open Sheet");
			alert_box.initModality(Modality.APPLICATION_MODAL);
			//alert_box.initStyle(StageStyle.UNDECORATED);
			alert_box.setResizable(false);
			alert_box.showAndWait();
			//alert_box.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		displayAns();
		
	}
	void displayAns() {
		int score = 0;
		gridpane.getChildren().clear();
		int i =0;
		if (sans.length < rans.length) {
			i = sans.length;
		}
		else {
			i = rans.length;
		}
		for (int row = 0; row < i; row++) {
			Label item = new Label(Integer.toString(row +1));
			String a = sans[row];
			String b = rans[row];
			Label ans = new Label(a);
			Label r_ans = new Label(b);
			Label pts = new Label();
			if (a.equals(b)) {
				score++;
				pts.setText("C");
				pts.setTextFill(Color.GREEN);
			}
			else {
				pts.setText("X");
				pts.setTextFill(Color.RED);
			}
			
			
			item.setFont(new Font("Arial black",15));
			ans.setFont(new Font("Arial black",15));
			r_ans.setFont(new Font("Arial black",15));
			pts.setFont(new Font("Arial black",15));
			
			item.setAlignment(Pos.CENTER);
			ans.setAlignment(Pos.CENTER);
			r_ans.setAlignment(Pos.CENTER);
			pts.setAlignment(Pos.CENTER);
			
			gridpane.add(item, 0, row);
			gridpane.add(ans, 1, row);
			gridpane.add(r_ans, 2, row);
			gridpane.add(pts, 3, row);
		}
		File file = new File(ClassSessionController.answers + tcode +"\\" +sname +".png");
        Image image = new Image(file.toURI().toString());
        
        qr_imageView.setImage(image); 
		name_label.setText(sname);
		course_label.setText(scourse);
		score_label.setText(score + "/" + testItems);
	}
}

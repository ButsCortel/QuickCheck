package com.buts.research;


import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Test {

public static void main (String[] args) {
	String[] classes_string;
	String name = "hey aere qwet";
	String he = "hey aqqq rqw";
	classes_string = name.split("\\s+");
	System.out.println(classes_string[0]);
	System.out.println(classes_string[1]);
	System.out.println(classes_string[2]);
	classes_string = he.split("\\s+");
	System.out.println(classes_string[0]);
	System.out.println(classes_string[1]);
	System.out.println(classes_string[2]);
}
}
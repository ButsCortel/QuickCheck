package com.buts.research;


import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Test {

public static void main (String[] args) {
	String doc = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
	System.out.println(System.getProperty("user.home"));
}
}
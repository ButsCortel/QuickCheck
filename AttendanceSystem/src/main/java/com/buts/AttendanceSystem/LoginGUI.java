package com.buts.AttendanceSystem;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class LoginGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userName;
	private JPasswordField passWord;
	java.io.File file = null;
	JFileChooser fChoose = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
				         public void windowClosing(WindowEvent e) {
				             System.exit(0);
				         }
				     });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(83, 78, 72, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setBounds(83, 135, 72, 15);
		contentPane.add(lblNewLabel_1);
		
		userName = new JTextField();
		userName.setBounds(165, 74, 155, 23);
		contentPane.add(userName);
		userName.setColumns(10);
		
		final JLabel label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(165, 165, 155, 25);
		contentPane.add(label);
		
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userName.getText();
				String password = passWord.getText();
				
				if (username.contains("Arlene Cortel") && password.contains("0595")) {
					label.setText("");
					selectStudentList();
					if ( AttendanceGUI.studentFilePath != null) {
					dispose();
					FilePath.choosePath();
					
					}

				}
				else {
					userName.setText("");
					passWord.setText("");
					label.setText("Invalid User Credentials!");
				}
			}
		});
		btnLogin.setBounds(165, 201, 89, 23);
		contentPane.add(btnLogin);
		
		JLabel lblAttendanceLogger = new JLabel("Attendance Logger");
		lblAttendanceLogger.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAttendanceLogger.setBounds(151, 11, 184, 30);
		contentPane.add(lblAttendanceLogger);
		
		passWord = new JPasswordField();
		passWord.setBounds(165, 132, 155, 23);
		contentPane.add(passWord);
		

	}
	void selectStudentList() {
		fChoose = new JFileChooser();
		fChoose.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX files", "xlsx");
		fChoose.setFileFilter(filter);
		fChoose.setDialogTitle("Select Student's list");
		 fChoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		 fChoose.setFileHidingEnabled(false);
	      if (fChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	         file = fChoose.getSelectedFile();
	         if (file.isFile()) {
	         AttendanceGUI.studentFilePath = file.getPath();}
	         else {Toolkit.getDefaultToolkit().beep();
   		  JOptionPane.showMessageDialog(new JFrame(), "INVALID FILE SELECTED!", "ERROR",
			        JOptionPane.ERROR_MESSAGE); }
	        
	      }
	}
}
	


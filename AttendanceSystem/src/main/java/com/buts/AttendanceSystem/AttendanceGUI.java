package com.buts.AttendanceSystem;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.Closeable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.SwingConstants;
import javax.swing.Timer;

import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JButton;

public class AttendanceGUI extends JFrame implements Runnable, ThreadFactory, Closeable {
	private static final long serialVersionUID = 6441489157408381878L;
	private Executor executor = Executors.newSingleThreadExecutor(this);
	

	private Webcam webcam = null;
	private WebcamPanel webcamPanel = null;
	private JLabel nameDisp = null;
	private JLabel timeIn = null;
	private JLabel timeLabel;
	static JLabel inStatus = null;
	static int classTimeMin;
	static int classTimeHr;
	static String timeDisp;
	static String dateDisp;
	
    static JTextField hr = null;
    static JTextField min = null;
    //String studentList = "C:\\Users\\Buts\\eclipse-workspace\\AttendanceSystem\\Students.xlsx";
    static String file_Name = "";
    static JLabel dupLog = null;
	static String lastStr = null;
	static LocalTime time = null;
	static LocalTime now = null;
	static String studentFilePath = null;
	static int totalOpenFlag = 0;

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("resource")
	public static void startGUI(String[] args) {
	new AttendanceGUI();

	}

	/**
	 * Create the frame.
	 */
	public AttendanceGUI() {
		super();
		setTitle("Attendance Logger v1");
		JPanel jPanelWebcam = null;
	
		JPanel contentPane;

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {{
		        int result = JOptionPane.showConfirmDialog(null,
			            "Do you want to Exit ?", "Exit Confirmation : ",
			            JOptionPane.YES_NO_OPTION);
			        if (result == JOptionPane.YES_OPTION) {
			        	System.exit(0);
			        }
			        else if (result == JOptionPane.NO_OPTION) {
				          setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				        }
			      }

			}
		});
		setBounds(100, 100, 664, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jPanelWebcam = new JPanel();
		jPanelWebcam.setBounds(10, 11, 186, 154);
		contentPane.add(jPanelWebcam);
        jPanelWebcam.getParent().revalidate();
        
        JLabel lblLastLogged = new JLabel("Logged:");
        lblLastLogged.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblLastLogged.setBounds(206, 11, 86, 30);
        contentPane.add(lblLastLogged);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(10, 569, 620, 2);
        contentPane.add(separator);

        try {webcam = Webcam.getWebcams().get(0);}
        catch (Exception e) {
        	webcam = Webcam.getWebcams().get(1);
        }
        
        webcam.setViewSize(WebcamResolution.QQVGA.getSize());
        webcamPanel = new WebcamPanel(webcam);
        jPanelWebcam.add(webcamPanel);
        
        nameDisp = new JLabel();
        nameDisp.setHorizontalAlignment(SwingConstants.CENTER);
        nameDisp.setText(" ");
        nameDisp.setFont(new Font("Monospaced", Font.PLAIN, 20));
        nameDisp.setBounds(206, 124, 424, 35);
        contentPane.add(nameDisp);
        
        timeIn = new JLabel();
        timeIn.setHorizontalAlignment(SwingConstants.LEFT);
        timeIn.setText(" ");
        timeIn.setFont(new Font("Monospaced", Font.PLAIN, 25));
        timeIn.setBounds(206, 201, 191, 35);
        contentPane.add(timeIn);
        
        inStatus = new JLabel("");
        inStatus.setFont(new Font("Tahoma", Font.PLAIN, 30));
        inStatus.setBounds(331, 11, 191, 45);
        contentPane.add(inStatus);
        
        JLabel lblNewLabel = new JLabel("Name:");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(206, 85, 95, 28);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Time in:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(206, 162, 95, 28);
        contentPane.add(lblNewLabel_1);
        
  
        final DateFormat timeFormat = new SimpleDateFormat("hh:mm:a dd-MM-yyyy");
        ActionListener timerListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Date date = new Date();
                timeDisp = timeFormat.format(date);
                timeLabel.setText(timeDisp);
            }
        };
        Timer timer = new Timer(1000, timerListener);
        // to make sure it doesn't wait one second at the start
        timer.setInitialDelay(0);
        timer.start();

        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
        timeLabel.setBounds(10, 257, 628, 79);
        contentPane.add(timeLabel);
        
        dupLog = new JLabel("");
        dupLog.setForeground(Color.RED);
        dupLog.setHorizontalAlignment(SwingConstants.CENTER);
        dupLog.setFont(new Font("Tahoma", Font.PLAIN, 15));
        dupLog.setBounds(274, 67, 317, 28);
        contentPane.add(dupLog);
        
        JLabel classTime = new JLabel();
        classTime.setHorizontalAlignment(SwingConstants.LEFT);
        classTime.setText(FilePath.formattedClassTime);
        classTime.setFont(new Font("Monospaced", Font.PLAIN, 25));
        classTime.setBounds(400, 201, 191, 35);
        contentPane.add(classTime);
        
        JLabel lblClassTime = new JLabel("Class Time:");
        lblClassTime.setHorizontalAlignment(SwingConstants.LEFT);
        lblClassTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblClassTime.setBounds(400, 162, 122, 28);
        contentPane.add(lblClassTime);
        
        JButton btnNewButton = new JButton("Finish an attendance");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					Total.total();
				} catch (InvalidFormatException | IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        btnNewButton.setBounds(10, 191, 186, 45);
        contentPane.add(btnNewButton);
        setVisible(true);
          
        executor.execute(this);

	}

	//WEBCAM QR CODE SCANNER THREAD
	@Override
	public void run() {

		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Result result = null;
			BufferedImage image = null;

			if (webcam.isOpen()) {

				if ((image = webcam.getImage()) == null) {
					continue;
				}

				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				try {
					result = new MultiFormatReader().decode(bitmap);
				
				} catch (NotFoundException e) {
					// fall thru, it means there is no QR code in image
				}
			}

			if (result != null && !result.getText().equals("") && !result.getText().equals(lastStr) && totalOpenFlag == 0) //prevent cam from reading continuously
				{
				lastStr = result.getText();
				nameDisp.setText(result.getText());
				timeIn.setText(dateTime());
				Toolkit.getDefaultToolkit().beep();
				try {
					checkName(result.getText());
				}
				catch (Exception e) {
			  		  e.printStackTrace();
				}
			}

		} while (true);
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "example-runner");
		t.setDaemon(true);
		return t;
	}

	@Override
	public void close() throws IOException {
		webcam.close();
	}
	//FUNCTION FOR DATE AND TIME DISPLAY
	static String dateTime() {
	    LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("hh:mm:a");
	    String formattedDate = myDateObj.format(myFormatObj);
	    return formattedDate;
	   }
	//FUNCTION FOR TIME OF LOGGING 
	static String timeIn() throws InterruptedException {
		time = LocalTime.of(classTimeHr, classTimeMin, 0);

		now = LocalTime.now();
		String inTimeStatus = null;

		if (now.compareTo(time) > 0) {
			inStatus.setText("LATE");
			inStatus.setForeground(Color.RED);
			inTimeStatus = "LATE";
			}
		else {
			inStatus.setText("ON TIME");
			inStatus.setForeground(Color.GREEN);
			inTimeStatus = "ON TIME";
			}
		return inTimeStatus;
		}
	
	//FUNCTION FOR CHECKING NAME FROM XL
	
	void checkName(String rawName) throws InvalidFormatException, IOException, InterruptedException 
		{
		FileInputStream fis = null;
		String studentName = null;
		try {
			fis = new FileInputStream(studentFilePath);
		} catch (FileNotFoundException e1) {
			Toolkit.getDefaultToolkit().beep();
	  		  JOptionPane.showMessageDialog(new JFrame(), "FILE NOT FOUND", "ERROR",
	  			        JOptionPane.ERROR_MESSAGE);
		}
		XSSFWorkbook workBook = null;
		try {
			workBook = new XSSFWorkbook (fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet sheet    = workBook.getSheetAt (0);
		Iterator<Row> rows= sheet.rowIterator();
		while (rows.hasNext ())
			{
			XSSFRow row = (XSSFRow) rows.next ();  

			Iterator<Cell> cells = row.cellIterator (); 
			while (cells.hasNext ())
			{
	        XSSFCell cell = (XSSFCell) cells.next (); 
	        if (cell.toString().equals(rawName) && rawName != " ") {
	        	studentName = cell.getStringCellValue();
	        	write(studentName);
	        	}
			}
			}
		if (studentName == null) {
			inStatus.setText("NO RECORD!");
			dupLog.setText("");
			inStatus.setForeground(Color.RED);
		}
		}

	static String date() {
		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	    String formattedDate = myDateObj.format(myFormatObj);
	    return formattedDate;
	        }
	static XSSFWorkbook wb = null;
	static void write(String sName) throws IOException, InvalidFormatException, InterruptedException 
	{
		dupLog.setText("");
		int dup = 0;
		InputStream inp = new FileInputStream(file_Name); 
	    wb = new XSSFWorkbook(inp); 
	    XSSFSheet sheet = wb.getSheetAt(0);
	    Iterator<Row> rows= sheet.rowIterator();
	    while (rows.hasNext()) {
	    	XSSFRow row = (XSSFRow) rows.next ();
	    	Iterator<Cell> cells = row.cellIterator (); 
	    	while (cells.hasNext ()) {
	    		XSSFCell cell = (XSSFCell) cells.next ();
	    	    if (cell.toString().contains(sName)) {	    	    	
	    	    	dup = 1;
	    	    }	    	    
	    	}  

	    }
	    try {
	    if (dup == 0)	 {	
	    	int num = sheet.getLastRowNum(); 
	        Row row1 = sheet.createRow(++num); 
	        row1.createCell(0).setCellValue(sName); 
	        row1.createCell(1).setCellValue(dateTime());
	        row1.createCell(2).setCellValue(timeIn());
	        dupLog.setText("");
	        dup = 0;
	    }
	    else {
	    	dupLog.setText("Already Logged!");
	    	inStatus.setText("");
	    	
	    }
	    FileOutputStream fileOut = new FileOutputStream(file_Name); 
	    wb.write(fileOut); 
	    fileOut.close();
	    wb.close();
	    } 
	    catch (FileNotFoundException e) {
	    e.printStackTrace();
	    dupLog.setText("LOG IS OPEN!");
	    inStatus.setText("ERROR!");
  		  JOptionPane.showMessageDialog(new JFrame(), "PLEASE CLOSE ATTENDANCE LOG!!", "ERROR",
			        JOptionPane.ERROR_MESSAGE);
	    	}
	    }
}



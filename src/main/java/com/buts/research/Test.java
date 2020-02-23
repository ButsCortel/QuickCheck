package com.buts.research;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Test{
	public static void main(String[] args) throws IOException {
    InputStream is = null;
    OutputStream os = null;
    try {
        is = new FileInputStream("C:\\Users\\Ana\\QuickCheck-workspace\\Files\\Classes\\1 1\\Students.xls");
        os = new FileOutputStream("C:\\Users\\Ana\\Desktop\\Attendance.xls");
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
    } finally {
        is.close();
        os.close();
    }
}
}
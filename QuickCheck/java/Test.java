package com.buts.research;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String[] args) {

        var url = "jdbc:h2:~/test";
        var uname = "bukleco";
        var pass = "tinapadaing";

        try (var con = DriverManager.getConnection(url, uname, pass);
             var stm = con.createStatement();)
        	
             //var rs = stm.executeQuery("SELECT * FROM tutorials_tbl")) {
        		
        			{stm.executeUpdate("INSERT INTO tutorials_tbl VALUES (1, 'hi', 'hello', null);");
        	/*stm.execute("CREATE TABLE tutorials_tbl ( \r\n" + 
        			"   id IDENTITY NOT NULL PRIMARY KEY, \r\n" + 
        			"   title VARCHAR(50) NOT NULL, \r\n" + 
        			"   author VARCHAR(20) NOT NULL, \r\n" + 
        			"   submission_date DATE \r\n" + 
        			");");
           while (rs.next()) {

                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
               
                
            }*/

        } catch (SQLException ex) {

            var lgr = Logger.getLogger(Test.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
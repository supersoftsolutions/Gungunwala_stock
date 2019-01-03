import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;

import javax.swing.*;



import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;  
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;  
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;


public class Databaseconnection {
	
	private static Object OsSpecificseperator;
    Connection connection = null; 

	
	  public static Connection connection2()
	  {  
	     try 
	     {  
	         Class.forName("org.sqlite.JDBC"); /*C:\Users\SuperSoft\workspace\Billing_application*/
	         Connection connection = DriverManager.getConnection("jdbc:sqlite:Gungunwala.db");  
	         
	         return connection;
	     } 
	     catch (Exception e) 
	     {  
	         e.printStackTrace();
	         JOptionPane.showMessageDialog(null,e);
	         return null;
	     }
	 }  
}

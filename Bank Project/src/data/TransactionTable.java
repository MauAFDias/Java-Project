package data;

import java.util.*;
import java.text.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionTable 
{
	//Table created in oracle:
//	CREATE TABLE transaction
//	(transa_num NUMBER(6),
//	transa_value NUMBER(12,3),
//	transa_AccType VARCHAR2(20),
//	transa_Date DATE,
//	acc_num NUMBER(6),
//	CONSTRAINT transaction_transa_num_pk PRIMARY KEY (transa_num),
//	CONSTRAINT transaction_acc_num_account_fk FOREIGN KEY (acc_num)
//	REFERENCES account(acc_num));
	
	public static int GetTransaIndex() throws SQLException
	{


	int nextNumber=0;
		Connection conn = ConnectionDB.conn();
	  
		String sqlQuery;
		sqlQuery = "select max(transa_num)as TempNumber from transaction";
		Statement myStatement = null;
	    ResultSet myResultSet = null;
	    myStatement = conn.createStatement();
	    myResultSet = myStatement.executeQuery(sqlQuery);
	    
	    if(myResultSet.next())
	    {
	    	nextNumber = myResultSet.getInt(1)+1;
	    	
	    	
	    }
	    
	    conn.close();
	    return nextNumber;
		
	}
	public static void createTransaLog(Connection c, int accNum , String transaAccType , double transaValue)throws SQLException 
	{	
		int transaNum = TransactionTable.GetTransaIndex();
		String newTransaDate = "";
		Date dateNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yy-MM-dd");
		newTransaDate = ft.format(dateNow);
		
		 try
	        {  
	            Statement insertaccStmt = c.createStatement(); 
	              
	            // Inserting Account in database 
	            String insertaccq1 = "insert into transaction(transa_num, transa_value, transa_AccType, transa_Date, acc_num)"
	            + " values('" +transaNum+ "', '" +transaValue+  
	            "', '" +transaAccType+ "',DATE '" +newTransaDate+ "', '" +accNum+ "')"; 
	            int z = insertaccStmt.executeUpdate(insertaccq1); 
	            if (z > 0)             
	                System.out.println("Transaction Log Successfully Created");             
	            else            
	                System.out.println("Transaction Log Failed"); 
	        } 
	        catch(Exception e) 
	        { 
	            System.out.println(e+ "Transaction Log Failed"); 
	        }
	}
}

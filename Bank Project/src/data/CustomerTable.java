package data;

import java.util.*;
import java.text.*;

import buss.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
@SuppressWarnings("resource")
public class CustomerTable  
{
	
	//tables in oracle
//	CREATE TABLE manager
//	(m_num NUMBER(6),
//	m_last VARCHAR2(20),
//	m_first VARCHAR2(20),
//	m_pass VARCHAR2(30),
//	m_id VARCHAR2(20),
//	CONSTRAINT manager_c_num_pk PRIMARY KEY (m_num));
//
//	INSERT INTO manager VALUES
//	(1, 'Myers', 'Mark', 'man123', 'manager1');
//
//	-------------------
//
//	CREATE TABLE customer
//	(c_num NUMBER(6),
//	c_sin NUMBER(9),
//	c_lastn VARCHAR2(20),
//	c_firstn VARCHAR2(20),
//	c_pass VARCHAR2(30),
//	c_userid VARCHAR2(20),
//	CONSTRAINT customer_c_num_pk PRIMARY KEY (c_num));
	
	public static int GetCustomerIndex() throws SQLException
	{
		// Method to Create Auto ID //
	int nextNumber=0;
		Connection conn = ConnectionDB.conn();
		String sqlQuery;
		sqlQuery = "select max(c_num)as TempNumber from customer ";
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
	
	public static void viewAllCustomers(Connection c) throws SQLException 
	{
		    Statement stmt = null;
		    String query = "select * from customer";
	//	    String query = "select c_num, c_sin, c_lastn, " +
	//                   "c_firstn, c_pass, c_userid" +
	//                   "from customer";
		    try 
		    {
		        stmt = c.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) 
		        {
		            int c_num = rs.getInt(1);
		            int c_sin = rs.getInt(2);
		            String c_lastn = rs.getString(3);
		            String c_firstn = rs.getString(4);
		            String c_pass = rs.getString(5);
		            String c_userid = rs.getString(6);
		            System.out.println("Client Number: " +c_num + " || SIN: " + c_sin +
		            "\t|| First Name: " + c_firstn + " || Last Name: " + c_lastn +
		            "\t|| User Login: " + c_userid + " || Password: "+ c_pass );
		            System.out.println("------------------------------------------------------------");
		        }
		    } 
		    catch (SQLException e ) 
		    {
		        System.out.println(e);
		    } 
		    finally 
		    {
		        if (stmt != null) { stmt.close(); }
		    }
	}
	
	public static void createCustomer(Connection c)throws SQLException 
	{
		Scanner scan = new Scanner(System.in);
		// Customer temporary data declared in single lines
		int newCustId;
		int newCustSIN; 
        String newCustFName = ""; 
        String newCustLName = ""; 
        String newCustUserLogin = ""; 
        String NewCustPass = "";  
        // Account temporary data declared together
        int newAccNum, newAccPIN;
        float newAccAvalBal;
        EnumAccType newAccType;
		String newAccOpenDate = "";
        // Checking temporary data
        int check_num, ch_TransaLimit;
        double ch_AvalBal,ch_ExtraFees;
        EnumAccType ch_AccType;
        // -------- Customer Data------ //
	    newCustId = CustomerTable.GetCustomerIndex(); // Automatic ID Creation method
//	    System.out.print("New Customer System ID:");
//	    newCustId = scan.nextInt();
	    System.out.print("New Customer SIN:");
	    newCustSIN = scan.nextInt();
		System.out.print("New Customer First Name:");
		newCustFName = scan.next();
		System.out.print("New Customer Last Name:");
		newCustLName = scan.next();
		System.out.print("New Customer User Login:");
		newCustUserLogin = scan.next();
		System.out.print("New Customer Password:");
		NewCustPass = scan.next();
		// -------- Account Data------//
		//System.out.print("New Account Number:");
		newAccNum = newCustId;//Customer ID and account ID will be the same
		System.out.print("New Account PIN:");
		newAccPIN = scan.nextInt();
		newAccAvalBal = 0;
		newAccType = EnumAccType.MAIN ; //"Main Account";
		Date dateNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yy-MM-dd");
		newAccOpenDate = ft.format(dateNow);
		// -------- Customer Data------//
		check_num = newAccNum;
		ch_TransaLimit = 4;
		ch_AvalBal = 0.000;
		ch_ExtraFees = 10;
		ch_AccType = EnumAccType.CHECKING;
		
        // Inserting Customer in database  //
        try
        {  
            Statement insertStmt = c.createStatement(); 
            String insertq1 = "insert into customer values('" +newCustId+ "', '" +newCustSIN+  
            "', '" +newCustLName+ "', '" +newCustFName+ "', '" +NewCustPass+ "', '" +newCustUserLogin+  "')"; 
            int x = insertStmt.executeUpdate(insertq1); 
            if (x > 0)             
                System.out.println("Customer Created Successfully");             
            else            
                System.out.println("Customer Insert Failed"); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e+ "Customer Insert Failed"); 
        }
        // Inserting Account in database //
        try
        {  
            Statement insertaccStmt = c.createStatement(); 
            String insertaccq1 = "insert into account(acc_num, acc_pin, acc_avalBal, acc_type, acc_openDate, c_num)"
            + " values('" +newAccNum+ "', '" +newAccPIN+  
            "', '" +newAccAvalBal+ "', '" +newAccType+ "',DATE '" +newAccOpenDate+ "', '" +newCustId+ "')"; 
            int z = insertaccStmt.executeUpdate(insertaccq1); 
            if (z > 0)             
                System.out.println("Account Created Successfully");             
            else            
                System.out.println("Account Insert Failed"); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e+ "Account Insert Failed"); 
        }
        // Inserting Checking in database //
        try
        {  
            Statement insertcheckStmt = c.createStatement(); 
            String insertcheckq1 = "insert into checking(check_num, ch_TransaLimit, ch_AvalBal, ch_AccType, ch_ExtraFees, acc_num)"
            + " values('" +check_num+ "', '" +ch_TransaLimit+  
            "', '" +ch_AvalBal+ "', '" +ch_AccType+ "', '"+ch_ExtraFees+ "', '" +newAccNum+ "')"; 
            int y = insertcheckStmt.executeUpdate(insertcheckq1); 
            if (y > 0)             
                System.out.println("Checking Created Successfully");             
            else            
                System.out.println("Checking Insert Failed"); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e + "Checking Insert Failed"); 
        }
        c.commit();
	}
	
	public static void updateCustomer(Connection c)throws SQLException 
   	{
        Scanner scan = new Scanner(System.in);
		String cId = "";   
//        int updateCustId; - earlier version
		int updateCustSIN, updateAccPIN; 
        String updateCustFName = ""; 
        String updateCustLName = ""; 
        String updateCustUserLogin = ""; 
        String updateCustPass = "";
        
        System.out.print("Insert the Customer ID that you want to update:\nCustomer ID:");
        cId = scan.next();
        
	    System.out.print("New Customer SIN:");
	    updateCustSIN = scan.nextInt();
		System.out.print("New Customer First Name:");
		updateCustFName = scan.next();
		System.out.print("New Customer Last Name:");
		updateCustLName = scan.next();
		System.out.print("New Customer User Login:");
		updateCustUserLogin = scan.next();
		System.out.print("New Customer Password:");
		updateCustPass = scan.next();
		System.out.print("New Account PIN:");
		updateAccPIN = scan.nextInt();
        
        // Updating Customer info //
        try
        {  
            Statement stmt = c.createStatement(); 
            String q1 = "UPDATE customer set c_sin = '" +updateCustSIN+  
                    "',c_lastn = '" +updateCustLName+ "', c_firstn = '" +updateCustFName+ 
                    "',c_pass = '" +updateCustPass+ "',c_userid = '" +updateCustUserLogin+ 
                    "' WHERE c_num = '" +cId+ "'"; 
            int x = stmt.executeUpdate(q1); 
              
            if (x > 0)             
                System.out.println("Customer Successfully Updated");             
            else            
                System.out.println("ERROR OCCURED, no changes were made in the customer"); 
              
        } 
        catch(Exception e) 
        { 
            System.out.println(e); 
        }
        // Updating PIN on customer account // 
        try
        {  
            Statement stmt = c.createStatement(); 
            String q1 = "UPDATE account set acc_pin = '" +updateAccPIN+
                    "' WHERE acc_num = '" +cId+ "'"; 
            int x = stmt.executeUpdate(q1); 
            if (x > 0)             
                System.out.println("Customer's PIN Successfully Updated");             
            else            
                System.out.println("ERROR OCCURED, No changes were made in the account"); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e); 
        } 
        c.commit();
   	}
	
	public static void deleteCustomer(Connection c)throws SQLException 
   	{
		// Delete method, starts backwards to avoid Parent-child Key violation on Oracle DB//
		Scanner scan = new Scanner(System.in);
		String cId = "";
		System.out.print("Insert the Customer ID to delete:\nCustomer ID:");
        cId = scan.next();
        // Deleting Credit from database //
        try
        { 
            Statement delCreditstmt = c.createStatement(); 
            String q11 = "DELETE from credit WHERE acc_num = " + cId; 
                      
            int z = delCreditstmt.executeUpdate(q11); 
              
            if (z > 0)             
                System.out.println("Credit acc Successfully Deleted");             
            else
                System.out.println("No Credit acc found so no Credit acc Deleted");   
        } 
        catch(Exception w) 
        { 
            System.out.println(w+"No Credit acc Deleted"); 
        }
        // Deleting Saving from database //
        try
        { 
            Statement delSavingstmt = c.createStatement(); 
            String q11 = "DELETE from saving WHERE acc_num = " + cId;         
            int z = delSavingstmt.executeUpdate(q11); 
            if (z > 0)             
                System.out.println("Saving acc Successfully Deleted");             
            else
                System.out.println("No Saving acc found so no Saving acc Deleted");   
        } 
        catch(Exception f) 
        { 
            System.out.println(f+"No Saving acc Deleted"); 
        }
        // Deleting Transaction Logs from database //
        try
        { 
            Statement delTransastmt = c.createStatement(); 
            String q11 = "DELETE from transaction WHERE acc_num = " + cId;        
            int z = delTransastmt.executeUpdate(q11); 
            if (z > 0)             
                System.out.println("Transaction Log Successfully Deleted");             
            else
                System.out.println("No transactions found so no Transaction Log Deleted");   
        } 
        catch(Exception d) 
        { 
            System.out.println(d+"No Transaction Log Deleted"); 
        }
        // Deleting Checking from database //
        try
        { 
            Statement delCheckstmt = c.createStatement();     
            // Deleting Checking from database 
            String q11 = "DELETE from checking WHERE check_num = " + cId;      
            int z = delCheckstmt.executeUpdate(q11); 
            if (z > 0)             
                System.out.println("Checking Successfully Deleted");             
            else
                System.out.println("ERROR OCCURED, No Checking Deleted");   
        } 
        catch(Exception s) 
        { 
            System.out.println(s+"No Checking Deleted"); 
        }
        // Deleting Account from database //
        try
        { 
            Statement delAccstmt = c.createStatement(); 
            String q11 = "DELETE from account WHERE acc_num = " + cId;         
            int y = delAccstmt.executeUpdate(q11); 
            if (y > 0)             
                System.out.println("Account Successfully Deleted");             
            else
                System.out.println("ERROR OCCURED, No Account Deleted");   
        } 
        catch(Exception r) 
        { 
            System.out.println(r+"No Account Deleted"); 
        }
        // Deleting Customer from database //
        try
        { 
            Statement stmt = c.createStatement(); 
            String q1 = "DELETE from customer WHERE c_num = " + cId;      
            int x = stmt.executeUpdate(q1); 
            if (x > 0)             
                System.out.println("Customer Successfully Deleted");             
            else
                System.out.println("ERROR OCCURED, No Customer Deleted");   
        } 
        catch(Exception e) 
        { 
            System.out.println(e+"No Customer Deleted"); 
        }
        c.commit();
   	}
}

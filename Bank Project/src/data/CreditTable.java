package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import buss.*;
@SuppressWarnings("resource")
public class CreditTable 
{
	//Table created in oracle:
//	CREATE TABLE credit
//	(cred_num NUMBER(6) NOT NULL,
//	cred_avalBal NUMBER(9,3),
//	cred_AccType VARCHAR2(20),
//	cred_extrafees NUMBER(9,3),
//	acc_num NUMBER(6),
//	CONSTRAINT credit_cred_num_pk PRIMARY KEY (cred_num),
//	CONSTRAINT credit_acc_num_account_fk FOREIGN KEY (acc_num)
//	REFERENCES account(acc_num));
	
	public static void checkorCreateCredit(Connection c, int custid) throws SQLException
	{	
		
		double credExtraFees, credAvalBal;
		String accType = EnumAccType.CREDIT.toString();
		credExtraFees = 12;
		credAvalBal = 0;
		
		try
    {  
        Statement insertCreditStmt = c.createStatement(); 
          
        // Inserting Credit in database 
        String insertCredq1 = "insert into credit"
        		+ "(cred_num, cred_avalBal, cred_AccType, cred_extrafees, acc_num)"
        + " values('" +custid+ "', '" +credAvalBal+  
        "', '" +accType+ "', '" +credExtraFees+ "', '" +custid+"')";
        int s = insertCreditStmt.executeUpdate(insertCredq1); 
        if (s > 0)             
            System.out.println("Credit Account Created Successfully");             
        else            
            System.out.println("Credit Insert Failed"); 
    } 
    catch(Exception e) 
    { 
        System.out.println( "Credit Account Found"); 
    }
    c.commit(); 
	}
  
  
	public static Credit getCreditAccount (Connection c, int custid) throws SQLException
	{
		Credit accCredit = new Credit();
		Statement stmt = null;
	    String query = "select * from credit where cred_num = "+ custid;
	    try 
	    {
	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) 
	        {
	        	accCredit.setAccNum(rs.getInt(1));
	        	accCredit.setAvalBal(rs.getDouble(2));
	        	accCredit.setAccType(rs.getString(3));
	        	accCredit.setExtraCredFees(rs.getInt(4));
	        }
	    } 
	    catch (SQLException e ) 
	    {
	        System.out.println(e);
	    } 
	    return accCredit;
     }
	
	public static void viewBalance(Connection c, int custid) throws SQLException 
	{

		    Statement stmt = null;
		    String query = "select * from credit where cred_num = "+ custid;
		    try 
		    {
		        stmt = c.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) 
		        {
		            double cred_avalBal = rs.getDouble(2);
		            System.out.println("The Credit debt balance is: " +cred_avalBal);
		            System.out.println("-------------------------------------------");
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
	
	
	public static void fundsAddorSubtractCredit(Connection c, Credit credAcc, int addremove)throws SQLException 
	{
		//DEPOSIT OR WITHDRAW METHOD
		Scanner scan = new Scanner(System.in);
		double previousBal,newBal;
		previousBal = credAcc.getAvalBal();
		double transaFees = credAcc.getExtraCredFees();
		
		System.out.print("Please input the value you would like to Add to your Credit account\nAmount: ");
	    if (addremove == 1) // adds/deposit funds 
	    {
	    		newBal = scan.nextDouble() - transaFees;
		} 
	    else // remove/withdraw funds
	    {
	    		newBal = (scan.nextDouble() - transaFees);
	    		if (newBal < 0){}
	    		else{newBal = newBal * -1;}
	    }
	    
	    TransactionTable.createTransaLog(c, credAcc.getAccNum(), credAcc.getAccType(), newBal);
	    
	    
	    newBal = previousBal + newBal;

		// -------- Credit Data------
		
        try
        {  
            Statement insertcredStmt = c.createStatement(); 
              
            // Inserting Credit in database 
            String qAddFunds = "UPDATE credit set cred_avalBal = '" +newBal+   
            		"' WHERE cred_num = '" +credAcc.getAccNum()+ "'";  
            int y = insertcredStmt.executeUpdate(qAddFunds); 
            if (y > 0)             
                System.out.println("Credit funds added Successfully");             
            else            
                System.out.println("Credit funds Failed"); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e + "Credit funds Failed"); 
        }
        c.commit();
	}

public static void deleteCustSavingAcc (Connection c, int custid)
{
	try
    { 
        Statement delCreditstmt = c.createStatement(); 
               
        // Deleting Credit from database 
        String q11 = "DELETE from credit WHERE acc_num = " + custid; 
                  
        int z = delCreditstmt.executeUpdate(q11); 
          
        if (z > 0)             
            System.out.println("Credit acc Successfully Deleted");             
        else
            System.out.println("No Credit acc found so no Credit acc Deleted");   
        
    } 
    catch(Exception f) 
    { 
        System.out.println(f+"No Credit acc Deleted"); 
    } 
}
}

package data;

import buss.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
@SuppressWarnings("resource")
public class CheckingTable 
{
	
//	CREATE TABLE checking
//	(check_num NUMBER(6) NOT NULL,
//	ch_TransaLimit NUMBER(4),
//	ch_AvalBal NUMBER(9,3),
//	ch_AccType VARCHAR2(20),
//	ch_ExtraFees NUMBER(3,3),
//	acc_num NUMBER(6),
//	CONSTRAINT checking_check_num_pk PRIMARY KEY (check_num),
//	CONSTRAINT checking_acc_num_account_fk FOREIGN KEY (acc_num)
//	REFERENCES account(acc_num));
	public static Checking getCheckingAccount (Connection c, int custid) throws SQLException
	{
		Checking accCheck = new Checking();
		Statement stmt = null;
	    String query = "select * from checking where check_num = "+ custid;
	    try 
	    {
	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) 
	        {
	        	accCheck.setAccNum(rs.getInt(1));
	        	accCheck.setFreeTL(rs.getInt(2));
	        	accCheck.setAvalBal(rs.getDouble(3));
	        	accCheck.setAccType(rs.getString(4));
	        	accCheck.setExtraFees(rs.getInt(5));
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
		
        return accCheck;
	}
	
	public static void viewBalance(Connection c, int custid) throws SQLException 
	{
		    Statement stmt = null;
		    String query = "select * from checking where check_num = "+ custid;
		    try 
		    {
		        stmt = c.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) 
		        {
		            double check_avalBal = rs.getDouble(3);
		            int freetransa = rs.getInt(2);
		            System.out.println("The Checking balance is: " +check_avalBal + "\tAvaliable free Transacions: "+freetransa );
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
	
	
	public static void fundsAddorSubtractChecking(Connection c, Checking checkAcc, int addremove)throws SQLException 
	{
		//DEPOSIT METHOD
		Scanner scan = new Scanner(System.in);
		double previousBal,newBal;
		previousBal = checkAcc.getAvalBal();
		int freeTL = checkAcc.getFreeTL();
		double transaFees = checkAcc.getExtraFees();
		
		System.out.print("Please input the value you would like to Add to your Checking account\nAmount: ");
	    if (addremove == 1) // adds funds 
	    {
	    	if (freeTL > 0) 
	    	{
	    		freeTL = freeTL -1;
	    		newBal = scan.nextDouble();
			}
	    	else
	    	{
	    		newBal = scan.nextDouble() - transaFees;
	    	}
		} 
	    else // remove funds
	    {
	    	if (freeTL > 0) 
	    	{
	    		freeTL = freeTL -1;
	    		newBal = scan.nextDouble();
	    		if (newBal < 0){}
	    		else{newBal = newBal * -1;}
			}
	    	else
	    	{
	    		newBal = (scan.nextDouble() - transaFees);
	    		if (newBal < 0){}
	    		else{newBal = newBal * -1;}
	    	}
	    }
	    TransactionTable.createTransaLog(c, checkAcc.getAccNum(), checkAcc.getAccType(), newBal);
	    
	    newBal = previousBal + newBal;
	    
		// -------- Checking Data------//
        try
        {  
            Statement insertcheckStmt = c.createStatement(); 
            // Inserting Checking in database 
            String qAddFunds = "UPDATE checking set ch_AvalBal = '" +newBal+   
            		"', ch_TransaLimit = '"+freeTL+"' WHERE acc_num = '" +checkAcc.getAccNum()+ "'";  
            int y = insertcheckStmt.executeUpdate(qAddFunds); 
            if (y > 0)             
                System.out.println("Checking funds added Successfully");             
            else            
                System.out.println("Checking funds Failed"); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e + "Checking funds Failed"); 
        }
        c.commit();
	}
}

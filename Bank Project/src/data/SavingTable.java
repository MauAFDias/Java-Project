package data;
import buss.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
@SuppressWarnings("resource")
public class SavingTable 
{
	public static void checkorCreateSaving(Connection c, int custid) throws SQLException
	{
		//Table created in oracle:
//		CREATE TABLE saving
//		(sav_num NUMBER(6) NOT NULL,
//		sav_annualIR NUMBER(6,3),
//		sav_annualGains NUMBER(9,3),
//		sav_avalBal NUMBER(9,3)CONSTRAINT savBalLimit_CK CHECK (sav_avalBal >= 0),
//		sav_AccType VARCHAR2(20),
//		sav_ExtraFees NUMBER(6,3),
//		acc_num NUMBER(6),
//		CONSTRAINT saving_sav_num_pk PRIMARY KEY (sav_num),
//		CONSTRAINT saving_acc_num_account_fk FOREIGN KEY (acc_num)
//		REFERENCES account(acc_num));
		
		
		double annualIR, annualGain, savAvalBal, extraSavFees;
		String accType = EnumAccType.SAVING.toString();
		annualIR = 1.5;
		annualGain = 18;
		savAvalBal = 0;
		extraSavFees = 5;
		
		try
		{  
			Statement insertSavingStmt = c.createStatement(); 
          
			// Inserting Saving in database 
			String insertSavq1 = "insert into saving"
        		+ "(sav_num, sav_annualIR, sav_annualGains, sav_avalBal, sav_AccType, sav_ExtraFees, acc_num)"
        		+ " values('" +custid+ "', '" +annualIR+  
        		"', '" +annualGain+ "', '" +savAvalBal+
        		"', '"+accType+ "', '" +extraSavFees+ "', '"+custid+ "')";
			int s = insertSavingStmt.executeUpdate(insertSavq1); 
			if (s > 0)             
				System.out.println("Saving Account Created Successfully");             
			else            
				System.out.println("Saving Insert Failed"); 
		} 
		catch(Exception e) 
		{ 
			System.out.println( "Saving Account Found"); 
		}
		c.commit(); 
  

	}
  
  
	public static Saving getSavingAccount (Connection c, int custid) throws SQLException
	{
		Saving accSaving = new Saving();
		Statement stmt = null;
	    String query = "select * from saving where sav_num = "+ custid;
	    try 
	    {
	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) 
	        {
	        	accSaving.setAccNum(rs.getInt(1));
	        	accSaving.setAnnualIR(rs.getDouble(2));
	        	accSaving.setAnnualGain(rs.getDouble(3));
	        	accSaving.setAvalBal(rs.getDouble(4));
	        	accSaving.setAccType(rs.getString(5));
	        	accSaving.setExtraSavFees(rs.getInt(6));
	        }
	    } 
	    catch (SQLException e ) 
	    {
	        System.out.println(e);
	    } 
	    return accSaving;
    }
	
	public static void viewBalance(Connection c, int custid) throws SQLException 
	{

		    Statement stmt = null;
		    String query = "select * from saving where sav_num = "+ custid;
		    try 
		    {
		        stmt = c.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) 
		        {
		            double sav_avalBal = rs.getDouble(4);
		            System.out.println("The Saving balance is: " +sav_avalBal);
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
	
	
	public static void fundsAddorSubtractSaving(Connection c, Saving savAcc, int addremove)throws SQLException 
	{
		//DEPOSIT OR WITHDRAW METHOD
		Scanner scan = new Scanner(System.in);
		double previousBal,newBal;
		previousBal = savAcc.getAvalBal();
		double transaFees = savAcc.getExtraSavFees();
		
		System.out.print("Please input the value you would like to Add to your Saving account\nAmount: ");
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
	    
	    TransactionTable.createTransaLog(c, savAcc.getAccNum(), savAcc.getAccType(), newBal);
	    
	    newBal = previousBal + newBal;

		// -------- Saving Data------
        try
        {  
            Statement insertcheckStmt = c.createStatement(); 
            String qAddFunds = "UPDATE saving set sav_AvalBal = '" +newBal+   
            		"' WHERE acc_num = '" +savAcc.getAccNum()+ "'";  
            int y = insertcheckStmt.executeUpdate(qAddFunds); 
            if (y > 0)             
                System.out.println("Saving funds added Successfully");             
            else            
                System.out.println("Saving funds Failed"); 
        } 
        catch(Exception e) 
        { 
            System.out.println(e + "Saving funds Failed"); 
        }
        c.commit();
	}

	public static void deleteCustSavingAcc (Connection c, int custid)
	{
		// Deleting Saving from database //
		try
		{ 
			Statement delSavingstmt = c.createStatement(); 
			String q11 = "DELETE from saving WHERE acc_num = " + custid;        
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
	}
}

package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Scanner;
@SuppressWarnings("resource")
public class AccountTable 
{
	
//	CREATE TABLE account
//	(acc_num NUMBER(6),
//	acc_pin NUMBER(9),
//	acc_avalBal NUMBER(9,3),
//	acc_type VARCHAR2(20),
//	acc_openDate DATE,
//	c_num NUMBER(6),
//	CONSTRAINT account_acc_num_pk PRIMARY KEY (acc_num),
//	CONSTRAINT account_c_num_customer_fk FOREIGN KEY (c_num)
//	REFERENCES customer(c_num));
	
	public static void viewAccountInfo(Connection c, int custID) throws SQLException 
	{
			DecimalFormat numberFormat = new DecimalFormat("#.000");
		    Statement stmt = null;
		    String query = "select ch.ch_AvalBal, s.sav_avalBal, c.cred_avalBal"+
		    		" from checking ch, saving s, credit c"+
		    		" where ch.acc_num = '"+custID+
		    		"' AND ch.acc_num = s.acc_num"+
		    		" AND s.acc_num = c.acc_num";
		    try 
		    {
		        stmt = c.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        if(rs.next()) 
		        {
		            Double chAvalBal = rs.getDouble(1);
		            Double savAvalBal = rs.getDouble(2);
		            Double credAvalBal = rs.getDouble(3);
		            
		            Double totalAccBal = chAvalBal + savAvalBal + (credAvalBal * -1);
		            
		            
		            System.out.println("Client Number: " +custID + 
		            		" Has a total Balance of: " +numberFormat.format(totalAccBal) );
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
	
	public static void viewAllAccountsBalance(Connection c, int custID) throws SQLException 
	{
		/* This function works as soon the customer as all 3 accs activated */
		
			DecimalFormat numberFormat = new DecimalFormat("#.000");
			  Double totalAccBal, chAvalBal, savAvalBal, credAvalBal;
		    Statement stmt = null;
		    String query = "select ch.ch_AvalBal, s.sav_avalBal, c.cred_avalBal"+
		    		" from checking ch, saving s, credit c"+
		    		" where ch.acc_num = '"+custID+
		    		"' AND ch.acc_num = s.acc_num"+
		    		" AND s.acc_num = c.acc_num";
		    try 
		    {
		        stmt = c.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        if(rs.next()) 
		        {
		        	chAvalBal = rs.getDouble(1);
		            savAvalBal = rs.getDouble(2);
		            credAvalBal = rs.getDouble(3);
		            
		            totalAccBal = chAvalBal + savAvalBal + (credAvalBal * -1);
		            
		            
		            System.out.println("Client Number: " +custID + 
		            		" Has a total Balance of: " +numberFormat.format(totalAccBal) );
		            System.out.println("------------------------------------------------------------");
		            
		            String query2 = "UPDATE account set acc_AvalBal = '" +totalAccBal+   
		            		"' WHERE acc_num = '" +custID+ "'";  
		            try 
				    {
				        stmt = c.createStatement();
				        int y = stmt.executeUpdate(query2); 
			            if (y > 0)             
			                System.out.println("Total Balance Successfully verified");             
			            else            
			                System.out.println("Total Balance check Failed"); 
				    } 
				    catch (SQLException e ) 
				    {
				        System.out.println(e+"Total Balance error");
				    } 
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
	public static void updateCustomerPIN(Connection c, int custID)throws SQLException 
   	{
        Scanner scan = new Scanner(System.in);

		int updatePIN;   
        
        System.out.print("Insert the your new 5-digit PIN:\nNew PIN:");
        updatePIN = scan.nextInt();
        try
        {  
            Statement stmt = c.createStatement(); 
          
            // Updating database 
            String q1 = "UPDATE account set acc_pin = '" +updatePIN+
                    "' WHERE acc_num = '" +custID+ "'"; 
            int x = stmt.executeUpdate(q1); 
              
            if (x > 0)             
                System.out.println("Customer's Account Number Successfully Updated");             
            else            
                System.out.println("ERROR OCCURED, No changes were made in the account"); 
              
        } 
        catch(Exception e) 
        { 
            System.out.println(e); 
        } 
        c.commit();
   	}
}

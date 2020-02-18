// JAVA PROJECT
// MAURICIO DIAS
// JESUS VASQUEZ 


package client;
import java.util.*;

import buss.*;
import data.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainBankApp {

	public static void main(String[] args) throws SQLException 
	{
/*		This was the first code used for testing*/
		
		
 /* Connection conn =null;
		String username = "bank";
		String password = "bank123";
		String service = "localhost";
		String url = "jdbc:oracle:thin:";
		
		try {
			conn = DriverManager.getConnection(url+username+"/"+password+"@"+service);
	    System.out.println("\nConnection established...\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myConnection = DriverManager.getConnection(url+username+"/"+password+"@"+service);
	    System.out.println("\nConnection established...\n"); */
		
		Connection conn = null;
		conn = ConnectionDB.conn();
		
		Scanner scan = new Scanner(System.in);
		int input,managermenuinput, customerMenuInput;
		char flag;
//		char flagcase;
		do
		{
		System.out.print("Choose an interface by number:\n1 - Customer Login\n"
				+ "2 - Mananger Login \n3 - Exit Application\nOption:");
		input = scan.nextInt();
			switch (input)
			{
			case 1:
				int checkCustLogin = 1;
				do {
					 Statement stmt = null;
					    String query = "select * from customer";
					    try 
					    {
					        stmt = conn.createStatement();
					        ResultSet rs = stmt.executeQuery(query);
					        while (rs.next()) 
					        {
					            String c_pass = rs.getString(5);
					            String c_userid = rs.getString(6);
					            System.out.println("Avaliable Customer Login: " + c_userid + "  Password: "+ c_pass );
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
					String idtemp = ""; 
				    String pwd = ""; 
				    System.out.print("Customer Login:");
					idtemp = scan.next();
					System.out.print("Customer Password:");
					pwd = scan.next();
					
				     
					 Statement Selectstmt = conn.createStatement(); 
		              
			            // SELECT query to verify Customer Login
			     String selectq1 = "select * from customer WHERE c_userid = '" + idtemp +  
			                 "' AND c_pass = '" + pwd + "'"; 
			     ResultSet rs = Selectstmt.executeQuery(selectq1); 
			     if (rs.next()) 
		            { 
			    	 System.out.print("\nCustomer User Identified:\n\n");
			    	 	System.out.println("Customer Number: " + rs.getString(1)+ " PIN: " + rs.getString(2));
					    System.out.println("First Name: " + rs.getString(4)+ " Last Name: " + rs.getString(3));
					    System.out.println("User-Id : " + rs.getString(5)+ " Password : " + rs.getString(6));
					    int customerPersistentID = Integer.parseInt(rs.getString(1));
					    int customerInterface = 1;
					    do {
					    System.out.print("\n\nChoose an interface by number:\n1 - Access Checking Account\n"
						+ "2 - Access Savings Account\n3 - Access Credit Account\n"
						+ "4 - Change PIN\n5 - View Balance\n6 - Leave Customer Interface\nOption:");
					    customerMenuInput = scan.nextInt();
							switch (customerMenuInput)
							{
							case 1:
								// Manage Checking //
								Checking checkAcc = new Checking();
								int checkingInterface = 1;
								do {
								checkAcc = CheckingTable.getCheckingAccount(conn, customerPersistentID);
								int checkingMenuInterface;
								System.out.print("\n\nChoose an interface by number:\n1 - Checking Deposit\n"
								+ "2 - Checking withdraw\n3 - Verify checking balance\n"
								+ "4 - Exit Checking Menu\nOption:");
								checkingMenuInterface = scan.nextInt();
								switch (checkingMenuInterface) 
								{
								case 1:
									CheckingTable.fundsAddorSubtractChecking(conn, checkAcc,1);
									break;
								case 2:
									CheckingTable.fundsAddorSubtractChecking(conn, checkAcc,2);
									break;
								case 3:
									CheckingTable.viewBalance(conn, customerPersistentID);
									break;
								case 4:
									checkingInterface = 0;
									break;
								default:
									System.out.println("Wrong number input");
									break;
								}
								} while (checkingInterface != 0);
								break;
							case 2:
								// Manage Saving //
									SavingTable.checkorCreateSaving(conn, customerPersistentID);
								Saving savUserAcc = new Saving();
								int savingInterface = 1;
								do {
								savUserAcc = SavingTable.getSavingAccount(conn, customerPersistentID);
								int savingMenuInterface;
								System.out.print("\n\nChoose an interface by number:\n1 - Saving Deposit\n"
								+ "2 - Saving withdraw\n3 - Verify Saving balance\n"
								+ "4 - Delete Saving Account" + "\n5 - Exit Saving Menu\nOption:");
								savingMenuInterface = scan.nextInt();
								switch (savingMenuInterface) 
								{
								case 1:
									SavingTable.fundsAddorSubtractSaving(conn, savUserAcc,1);
									break;
								case 2:
									SavingTable.fundsAddorSubtractSaving(conn, savUserAcc,2);
									break;
								case 3:
									SavingTable.viewBalance(conn, customerPersistentID);
									break;
								case 4:
									SavingTable.deleteCustSavingAcc(conn, customerPersistentID);
									savingInterface = 0;
									break;
								case 5:
									savingInterface = 0;
									break;
								default:
									System.out.println("Wrong number input");
									break;
								}
								} while (savingInterface != 0);
								break;
							case 3:
								// Manage Credit //
								CreditTable.checkorCreateCredit(conn, customerPersistentID);
								Credit credUserAcc = new Credit();
								int creditInterface = 1;
								do {
								credUserAcc = CreditTable.getCreditAccount(conn, customerPersistentID);
								int creditMenuInterface;
								System.out.print("\n\nChoose an interface by number:\n1 - Add Debt to your credit Account\n"
								+ "2 - Pay Credit Debt\n3 - Verify Credit balance\n"
								+ "4 - Delete Credit Acc" + "\n5 - Exit Credit Menu\nOption:");
								creditMenuInterface = scan.nextInt();
								switch (creditMenuInterface) 
								{
								case 1:
									CreditTable.fundsAddorSubtractCredit(conn, credUserAcc,1);
									break;
								case 2:
									CreditTable.fundsAddorSubtractCredit(conn, credUserAcc,2);
									break;
								case 3:
									CreditTable.viewBalance(conn, customerPersistentID);
									break;
								case 4:
									CreditTable.deleteCustSavingAcc(conn, customerPersistentID);
									creditInterface = 0;
									break;
								case 5:
									creditInterface = 0;
									break;
								default:
									System.out.println("Wrong number input");
									break;
								}
								} while (creditInterface != 0);
								break;
							case 4:
								// Change PIN //
								AccountTable.updateCustomerPIN(conn, customerPersistentID);
								break;
							case 5:
								// Check Total balance of the account //
								AccountTable.viewAllAccountsBalance(conn, customerPersistentID);
								break;
							case 6:
								// Exit //
								customerInterface = 0;
								checkCustLogin = 1;
								break;
							default:
								System.out.println("Wrong number input");
								break;
							}
					    } while (customerInterface == 1);
		            } 
		            else
		            { 
		                System.out.println("Wrong Customer Login or password\n\n"
		                + "Press (0) to try again or (1) to Leave\nOption:"); 
		                checkCustLogin = scan.nextInt();
		            } ;
				} while (checkCustLogin == 0);
				break;
			case 2:
				int check = 1;
				
				//Date First try
/*				Date dateNow = new Date();
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
				String datemydate = ft.format(dateNow);
				System.out.print(datemydate + "\n");*/
				do {
					 Statement stmt = null;
					    String query = "select * from manager";
				//	    String query = "select c_num, c_sin, c_lastn, " +
				//                   "c_firstn, c_pass, c_userid" +
				//                   "from customer";
					    try 
					    {
					        stmt = conn.createStatement();
					        ResultSet rs = stmt.executeQuery(query);
					        while (rs.next()) 
					        {
					            String m_pass = rs.getString(4);
					            String m_userid = rs.getString(5);
					            System.out.println("Avaliable Manager Login: " + m_userid + "  Password: "+ m_pass );
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
					String idtemp = ""; 
				    String pwd = ""; 
				    System.out.print("Manager Login:");
					idtemp = scan.next();
					System.out.print("Manager Password:");
					pwd = scan.next();
					
				     
					 Statement Selectstmt = conn.createStatement(); 
		              
			            // SELECT query to verify Manager Login
			     String selectq1 = "select * from manager WHERE m_id = '" + idtemp +  
			                 "' AND m_pass = '" + pwd + "'"; 
			     ResultSet rs = Selectstmt.executeQuery(selectq1); 
			     if (rs.next()) 
		            { 
			    	 System.out.print("\nManager User Identified:\n\n");
			    	 	System.out.println("Manager Number: " + rs.getString(1));
					    System.out.println("First Name: " + rs.getString(3)+ " Last Name: " + rs.getString(2));
//					    System.out.println("FIrst Name: " + rs.getString(3));
//					    System.out.println("Password : " + rs.getString(4));
					    System.out.println("User-Id : " + rs.getString(5)+ " Password : " + rs.getString(4));
					    
					    int manInterface = 1;
					    do {
					    System.out.print("\n\nChoose an interface by number:\n1 - Create a New Customer Account\n"
						+ "2 - View All Accounts\n3 - Update a Customer Account\n"
						+ "4 - Delete an account\n5 - log out of manager interface\nOption:");
					    managermenuinput = scan.nextInt();
							switch (managermenuinput)
							{
							case 1:
								CustomerTable.createCustomer(conn);
								break;
							case 2:
								CustomerTable.viewAllCustomers(conn);
								break;
							case 3:
								CustomerTable.updateCustomer(conn);
								break;
							case 4:
								CustomerTable.deleteCustomer(conn);
								break;
							case 5:
								manInterface = 0;
								check = 1;
								break;
							default:
								System.out.println("Wrong number input");
								break;
							}
					    } while (manInterface == 1);
		            } 
		            else
		            { 
		                System.out.println("Wrong Manager Login or password\n\n"
		                + "Press (0) to try again or (1) to Leave\nOption:"); 
		                check = scan.nextInt();
		            } ;
				} while (check == 0);
				break;
			case 3:
				break;
				default:
					System.out.println("Wrong number input, choose \n(1) for Customer Interface and "
							+ "\n(2) for Mananger Interface \n(3) to Exit the App");
			}
			System.out.print("\nDo you want to exit de app y or n ?\nOption: ");
			flag = scan.next().charAt(0);
		} while(flag!='y');
		System.out.println("Thank you for using MyBank App");

scan.close();
conn.close();
System.exit(0);
	}

}

package buss;

public class Account 
{
	private int accNum, pin;
	private double avalBal;
	private String accType, openDate;
	
	public int getAccNum() {return accNum;}
	public void setAccNum(int accNum) {this.accNum = accNum;}
	
	public int getPin() {return pin;}
	public void setPin(int pin) {this.pin = pin;}
	
	public double getAvalBal() {return avalBal;}
	public void setAvalBal(double avalBal) {this.avalBal = avalBal;}
	
	public String getAccType() {return accType;}
	public void setAccType(String accType) {this.accType = accType;}
	
	public String getOpenDate() {return openDate;}
	public void setOpenDate(String openDate) {this.openDate = openDate;}

//	 public String check()  method for early testing data
//	 {
//		String helo = (getAccNum() + " " + pin + " " + avalBal + " " + accType + " " + openDate); 
//		return helo;
//	 }

	 public String toString()
	 {
		return this.accNum + ", "+ this.pin + ", "+ this.avalBal 
				+ ", "+ this.accType + ", " + this.openDate; 
	 } 
	
}

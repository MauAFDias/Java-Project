package buss;

public class Customer 
{
	private int custNum, ssn;
	private String fn, ln, login, pass;
	
	public int getCustNum() {return custNum;}
	public void setCustNum(int custNum) {this.custNum = custNum;}
	
	public int getSsn() {return ssn;}
	public void setSsn(int ssn) {this.ssn = ssn;}
	
	public String getFn() {return fn;}
	public void setFn(String fn) {this.fn = fn;}
	
	public String getLn() {return ln;}
	public void setLn(String ln) {this.ln = ln;}
	
	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;}
	
	public String getPass() {return pass;}
	public void setPass(String pass) {this.pass = pass;}
	
	
	public String check() 
	{
		String helo = (getCustNum() + " " + ssn + " " + fn + " " + ln + " " + login + ", " + pass); 
		return helo;
	}
	
	public String toString()
	{
	return this.custNum + ", "+ this.ssn + ", "+ this.fn + ", "+ this.ln + ", " + this.login + ", " + this.pass + ", "; 
	}
	
	
}

package buss;

public class Manager 
{
 private int manNum;
 private String fn, ln, pass, id;
 
 public int getManNum() {return manNum;}
 public void setManNum(int manNum) {this.manNum = manNum;}
 
 public String getId() {return id;}
 public void setId(String id) {this.id = id;}
 
 public String getFn() {return fn;}
 public void setFn(String fn) {this.fn = fn;}
 
 public String getLn() {return ln;}
 public void setLn(String ln) {this.ln = ln;}
 
 public String getPass() {return pass;}
 public void setPass(String pass) {this.pass = pass;}

 
 public String check() 
 {
	String helo = (getId() + " " + manNum + " " + fn + " " + ln + " " + pass); 
	return helo;
 }

 public String toString()
 {
	return this.id + ", "+ this.manNum + ", "+ this.fn 
			+ ", "+ this.ln + ", " + this.pass; 
 } 


}

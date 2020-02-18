package buss;

public class Checking extends Account
{
	private int freeTL;
	private double extraFees;

	public int getFreeTL() {return freeTL;}
	public void setFreeTL(int freeTL) {this.freeTL = freeTL;}
	
	public double getExtraFees() {return extraFees;}
	public void setExtraFees(double extraFees) {this.extraFees = extraFees;}

	
	public String toString()
	{
	return super.toString() + this.freeTL + ", " + this.extraFees; 
	}
	
}

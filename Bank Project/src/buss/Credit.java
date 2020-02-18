package buss;

public class Credit extends Account
{
	private double extraCredFees;

	public double getExtraCredFees() {return extraCredFees;}
	public void setExtraCredFees(double extraCredFees) {this.extraCredFees = extraCredFees;}
	
	public Credit() 
	{
		super();
	}
	@Override
	public String toString() 
	{
		return super.toString() + this.extraCredFees;
	}
	public Credit(double credAvalBal, double extraCredFees) 
	{
		super();
		this.extraCredFees = extraCredFees;
	}
}

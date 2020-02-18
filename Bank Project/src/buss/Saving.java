package buss;

public class Saving extends Account
{
	
	private double annualIR, annualGain, extraSavFees;

	public double getAnnualIR() {return annualIR;}
	public void setAnnualIR(double annualIR) {this.annualIR = annualIR;}
	
	public double getAnnualGain() {return annualGain;}
	public void setAnnualGain(double annualGain) {this.annualGain = annualGain;}
	
	public double getExtraSavFees() {return extraSavFees;}
	public void setExtraSavFees(double extraSavFees) {this.extraSavFees = extraSavFees;}
	
	public Saving() {
		super();
	}
	public Saving(int annualIR, int annualGain, int extraSavFees) 
	{
		super();
		this.annualIR = annualIR;
		this.annualGain = annualGain;
		this.extraSavFees = extraSavFees;
	}
	
	public String toString()
	{
	return super.toString() + this.annualIR + ", " + this.annualGain + ", " + this.extraSavFees; 
	}
	
}

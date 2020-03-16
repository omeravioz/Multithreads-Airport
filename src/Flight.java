
public abstract class Flight implements Runnable{

	protected String flightCode;
	protected int passangerNumber;
	protected int LandTime;
	protected double HangTime; //time that flight spends at the airport 
	protected Airport myAP;
	
/**
 * parametric constructor
 * @param flightCode
 * @param passangerNumber
 * @param LandTime
 * @param myAP
 */
	public Flight (String flightCode, int passangerNumber, int LandTime,Airport myAP)
	{
		this.flightCode=flightCode;
		this.passangerNumber=passangerNumber;
		this.LandTime=LandTime;
		this.myAP = myAP;
		HangTime=0.0;
	}

}

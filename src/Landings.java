
public class Landings extends Flight{
	private int Cargo; //amount of cargo
	private double Cost=0; // cost of maintenance
	private String WhoSentYou;//the previous work station before entering to technical maintenance queue 
	private boolean isSecurityIssue=false;
	
	
	/**
	 * parametric constructor
	 * @param flightCode
	 * @param passangerNumber
	 * @param LandTime
	 * @param Cargo
	 * @param myAP
	 */
	public Landings(String flightCode,int passangerNumber,int LandTime, int Cargo,Airport myAP){
		super(flightCode,passangerNumber,LandTime,myAP);
		this.Cargo=Cargo;
	}
	
	/**
	 * run method, insert the object to management crew flight data
	 */
	public void run() {
	sleep();
	myAP.getFlightList().insert(this);
	}
	
	/**
	 * basic sleep method for thread
	 */
	public void sleep(){
		try        
		{
		    Thread.sleep(1000*LandTime);
		} 
		catch(InterruptedException e) 
		{
		    Thread.currentThread().interrupt();
		}	
	}
	/*basic tostring method*/
	public String toString(){
		return (flightCode+" "+" "+passangerNumber+" "+ LandTime+" "+ Cargo+" "+WhoSentYou);
	}
	
	/***********************************************setters and getters*******************************************************/
	public int getCargo(){
		return Cargo;
	}
	public double getCost(){
		return Cost;

	}
	public void setCost(double Cost){
		this.Cost+=Cost;
	}
	public String getWhoSentYou(){
		return WhoSentYou;
	}
	public void setWhoSentYou(String WSY){
	WhoSentYou=WSY;
	}


	public boolean getisSecurityIssue() {
		return isSecurityIssue;
	}


	public void setSecurityIssue(boolean isSecurityIssue) {
		this.isSecurityIssue = isSecurityIssue;
	}

}



public class FlightDetails {
	protected String flightCode;//flight code
	protected int Passengers; //amount of passengers 
	protected int Cargo; //number of cargo items 
	protected double Cost; //cost of technical maintenance 
	protected Boolean isSecurityIssue;
	protected double HangTime; //total time at airport
	protected String Destination;//flight destination
	protected String type;
	
	
	
	/*************************************************parametric constructors********************************************************************/
	public FlightDetails(){
		
	}
	public FlightDetails(String flightCode,int Passengers,int Cargo,double Cost,Boolean isSecurityIssue,double HangTime,String type){
		this.flightCode=flightCode;
		this.Passengers=Passengers;
		this.Cargo=Cargo;
		this.Cost=Cost;
		this.isSecurityIssue=isSecurityIssue;
		this.HangTime=HangTime;
		this.type=type;
		
	}
	public FlightDetails(String flightCode,int Passengers,String Destination,double HangTime,String type){
		this.flightCode=flightCode;
		this.Passengers=Passengers;
		this.Destination=Destination;
		this.HangTime=HangTime;
		this.type=type;
		
	}

}


public class Departures extends Flight{
	protected Airport myAp;
	protected String dest;
	
/**
 * parametric constructor
 * @param flightCode flight code
 * @param passangerNumber passengers amount on board
 * @param LandTime time for sleep
 * @param dest flight destination
 * @param myAP airport pointer 
 */
	public Departures(String flightCode,int passangerNumber,int LandTime, String dest, Airport myAP){
		super(flightCode,passangerNumber,LandTime,myAP);
		this.dest=dest;
		
	}
/**
 * run method for thread 
 * this method runs the departure and insert it to management crew data of flights
 */
	public void run() {
		sleep();	
		myAP.getFlightList().insert(this);
	}
	
	public void sleep(){ //thread basic sleep method
		try        
		{
		    Thread.sleep(1000*LandTime);
		} 
		catch(InterruptedException e) 
		{
		    Thread.currentThread().interrupt();
		}
		
	}
	//basic toString method
	public String toString(){
		return (flightCode+" "+" "+passangerNumber+" "+ LandTime+" "+ dest);
	}
}

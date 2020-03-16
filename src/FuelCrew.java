
public class FuelCrew extends Employee implements Runnable {

	private Airport myAp;
	private ManagementCrew mc;
	private String name;
	private int Capacity; //fuel tanker capacity
	private int CurrentCapacity; //current fuel tanker capacity 

	/**
	 * parametric constructor
	 * @param myAp airport pointer
	 * @param mc management crew pointer
	 * @param Capacity total capcity of tanker
	 */
	public FuelCrew(Airport myAp,ManagementCrew mc,int Capacity){
		this.myAp=myAp;
		this.mc=mc;
		this.Capacity=Capacity;
		CurrentCapacity=Capacity;
	}
	/**
	 * this method runs the fuel crew thread
	 */
	public void run() {
		while(this.mc.getEndOfDay()!=true){ //work day not over yet 
			Landings f=myAp.getFuelCrewList().extract();
			if(f!=null){
				if(CurrentCapacity>=1000){
					enoughFuel(f);	// fuel crew treatment in case of enough fuel			
				}
				else{
					fillFuel(f); //refuel process
				}	
			}
		}	
		
	}
	/*this method simulate refuel process */
	private void fillFuel(Landings f) {
		this.myAp.getFuelCrewList().insert(f); //flight returns to end of fuel queue
		sleep(5000,f); // time for refuel
		CurrentCapacity=Capacity; //full tank
	}
	/*this method simulate airplane fuel process */
	private void enoughFuel(Landings f) {
		CurrentCapacity-=1000;
		sleep(3000+(Math.random()*1000),f); //time for fuel airplane
		if(Math.random()<0.3){ //Probability to technical incident
			f.setWhoSentYou("FuelCrew");
			this.myAp.getTechnicalsList().insert(f);//Probability to technical incident
		}
		else{
			insertFlightDetails(f);//if technical incident not occurred than insert to management crew data base

		}

	}
	/*this method insert flight after fuel to management crew data base*/
	private  void insertFlightDetails(Landings f) {
		FlightDetails fc = new FlightDetails(f.flightCode,f.passangerNumber,f.getCargo(),f.getCost(),f.getisSecurityIssue(), f.HangTime, "Landings");
		
		myAp.getFlightInfo().insert(fc);


	}

}

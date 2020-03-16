
public class TechnicalCrew extends Employee implements  Runnable {
	private Airport myAp; 
	private ManagementCrew mc;
	private String name;
	
	/**
	 * parametric constructor
	 * @param myAp airport pointer
	 * @param mc management crew point
	 * @param name  technical crew name
	 */
	public TechnicalCrew(Airport myAp,ManagementCrew mc,String name){
		this.myAp=myAp;
		this.mc=mc;
		this.name=name;
	}

	/**
	 * this method runs technical crew thread
	 */
	public void run() {
		while(mc.getEndOfDay()!=true){ //work day not over 
			Landings f=myAp.getTechnicalsList().extract();	
			if(f!=null){
				f.setCost(500+(Math.random()*500)); //treatment random cost between 500-1000 
				sleep(3000+(Math.random()*2000),f); //sleep time about 3-5 sec 
				NextWorkStation(f);			
			}
		}
	}
	/**
	 * this method sent landing to next work station by definition
	 * @param f
	 */
	public void NextWorkStation(Landings f){
		if(f.getWhoSentYou().equals("RunwayDirector")) //came to technical treatment from runway station
			myAp.getLogisticalsList().insert(f); //move it to next station - logistics 
		else if(f.getWhoSentYou().equals("LogisticsCrew")){ //came to technical treatment from logistics station
			myAp.getSecurityManList().insert(f); //move it to next station - security 
		}			
		else{
			insertFlightDetails(f); //came from fuel crew and finished treatment - documented ad management crew 
			
		}
	}
	/**
	 * this method insert data to management crew database 
	 * @param f landing object
	 */
	private void insertFlightDetails(Landings f) {
		sleep(1000,f);
		FlightDetails fd=new FlightDetails(f.flightCode,f.passangerNumber,f.getCargo(),f.getCost(),f.getisSecurityIssue(), f.HangTime, "Landings");
	
		myAp.getFlightInfo().insert(fd); //insert to management  crew data base
		
	}
}

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class ManagementCrew extends Employee implements Runnable {
	private String name; 
	private Airport myAp;
	private boolean endOfDay; //boolean flag for end of work day
	private int counter;
	private Vector<FlightDetails> v=new Vector<FlightDetails>(); //flight details vector
	private DataBase db;

	/**
	 * parametric constructor
	 * @param name crews name
	 * @param myAp airport pointer
	 * @param endOfDay boolean flag
	 * @param counter counter of flights
	 */
	public ManagementCrew(String name,Airport myAp,boolean endOfDay,int counter){
		this.name=name;
		this.myAp=myAp;
		this.endOfDay=endOfDay;
		this.counter=counter;
		db=new DataBase();

	}
	/**
	 * this method runs management crew thread  : getting data of all flights, notifay to all threads that work day is over and create final report
	 */
	public void run() {

		
		while(counter!=0){ //there is flights to deal 
			FlightDetails f=myAp.getFlightInfo().extract();
			if(f!=null){
				inTreatment(f);
			}


		}
		System.out.println(v.size());

		wakeupall(); //wake up all threads to end their job
		Daysummary(); // daily report
		System.out.println("finish day");

	}

	private void inTreatment(FlightDetails f) {
		if(f.type.equals("Landings"))
			db.insertLandings(f, "Landings"); //in case of landing - insert to data base
		else
			db.insertTakeoff(f, "Takeoffs");//in case of departure - insert to data base
		setCounter();
		sleep(2000);
		System.out.println(f.flightCode+" "+ f.HangTime+" "+ f.Cost);
		v.addElement(f);
	}
	/**
	 * this method summarize work day 
	 */
	private void Daysummary() {
		int totalPassanger=0,totalCargo=0,totalCost=0,totalfuel=0,suspiciousObject=0,totalTruck=0;
		for(FlightDetails fd: v){
			totalPassanger+=fd.Passengers; //total  amount of passengers
			totalCargo+=fd.Cargo;//amount of total cargo
			totalCost+=fd.Cost; // amount of total cost expenses
			if(fd.type.equals("Landings")){
				totalfuel+=1000; //total amount of fuel
				if(fd.isSecurityIssue==true)
					suspiciousObject++; //total amount of security events
			}	
		}
		totalTruck=myAp.getTotalTruck();
		System.out.println("Day summary: ");
		System.out.println("total Passanger: "+ totalPassanger+"\n"+"total Cargo: "+totalCargo+
				"\n"+"total Cost: "+totalCost+"\n"+"total fuel: "+totalfuel+
				"\n"+"suspicious Object: "+ suspiciousObject+"\n"+"total Truck: "+ totalTruck+
				"\n"+"the most Popular Destination is: "+mostPopularDestination());

	}
	private String mostPopularDestination() {
		int maxValue=0;
		String dest="";
		Iterator<Map.Entry<String,Integer>>iter=myAp.getmap().entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<String,Integer> entry = iter.next();
			if(entry.getValue()>=maxValue){
				maxValue=entry.getValue();
				dest=entry.getKey();
			}
		}
		return dest;
	}
	/**
	 * this method notify to all thread that work day is over
	 */
	private void wakeupall() {
		this.myAp.getLogisticalsList().wakeUp();
		this.myAp.getSecurityManList().wakeUp();
		this.myAp.getTechnicalsList().wakeUp();
		this.myAp.getFuelCrewList().wakeUp();

	}

	/**
	 * sleep method
	 * @param goSleep time is sec
	 */
	public void sleep(double goSleep){
		try        
		{
			Thread.sleep((long) goSleep);

		} 
		catch(InterruptedException e) 
		{
			Thread.currentThread().interrupt();
		}

	}

	/**************************************************setters and getters**************************************************/
	public boolean getEndOfDay() {
		return endOfDay;
	}
	public  void setCounter(){
		counter--;

	}
	public int getcounter(){
		return counter;
	}


}

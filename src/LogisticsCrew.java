
public class LogisticsCrew extends Employee implements  Runnable {

	private ManagementCrew mc;
	private String name;
	private Airport myAp;
	private int capacity=0;

/**
 * parametric constructor
 * @param name logistical crew name
 * @param myAp airport pointer
 * @param capacity per each crew
 * @param mc management crew pointer
 */
	public LogisticsCrew(String name,Airport myAp,int capacity,ManagementCrew mc){
		this.name=name;
		this.myAp=myAp;
		this.capacity=capacity;
		this.mc=mc;
	}
	
	/**
	 * this method runs logistical crew thread
	 */
	public void run() {
		while(this.mc.getEndOfDay()!=true){ // work day not over yet
			Landings f=myAp.getLogisticalsList().extract();
			if(f!=null){
				inTreatment(f);		
			}
		}
	}
	
	private void inTreatment(Landings f) {
		if(f.getCargo()>capacity){
			sleep(2000,f); //in case that crew cant handle the cargo alone
			myAp.setTotalTruck(1);
		}
		sleep(f.getCargo()*100,f);//time to treat in cargo (10 crago items in 1 sec)
		double fail=Math.random();
		if(fail<0.10){ //Probability to technical incident
			f.setWhoSentYou("LogisticsCrew");
			this.myAp.getTechnicalsList().insert(f);//Probability to technical incident
		}
		else
			this.myAp.getSecurityManList().insert(f);//if technical incident not occurred than insert it security queue
	}
}

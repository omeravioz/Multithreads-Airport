
public class SecurityMan extends Employee implements Runnable {
	private Airport myAp;
	private ManagementCrew mc;
	private String rank;
	
	/**
	 * parametric constructor
	 * @param myAp airport pointer
	 * @param mc maintenance  crew
	 */
	public SecurityMan(Airport myAp,ManagementCrew mc){
		this.myAp=myAp;
		this.mc=mc;
	}
	/**
	 * this method runs security thread
	 */
	public void run() {
		while(mc.getEndOfDay()!=true){ //work day not over yet
			Landings f=myAp.getSecurityManList().extract();
			if(f!=null){
				LandingInTreatment(f); 
			}
		}
	
	}

	/**
	 * this method 
	 * @param f object of landing
	 */
	private void LandingInTreatment(Landings f) {
		sleep((this.myAp.getSwt()*1000),f);
		double fail=Math.random();
		if(fail<0.05){ //Probability  to security fail
			f.setSecurityIssue(true);//security issue
			sleep(2000,f);
		}
		this.myAp.getFuelCrewList().insert(f); //insert to bounded queue
		
	}

}

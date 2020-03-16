
public class RunwayDirector extends Employee implements Runnable {

	private Airport myAP;
	private ManagementCrew mc;

	public RunwayDirector(Airport myAP,ManagementCrew mc){
		this.myAP=myAP;
		this.mc=mc;
	}
	public void run() {
		while(myAP.getCounter()>0){
			Flight f = this.myAP.getFlightList().extract();		
			if(f!=null)
			{
				this.myAP.setCounter();
				double goSleep=(5+Math.random()*5)*1000;
				sleep(goSleep,f);
				if (f instanceof Landings){
					LandingsInTreatment(f);
				}
				else{
					DeparturesInTreatment(f);

				}	
			}
		}

	}
	private void DeparturesInTreatment(Flight f) {
		FlightDetails c = new FlightDetails(f.flightCode, f.passangerNumber,((Departures)f).dest,f.HangTime,"Departures");
		this.myAP.getFlightInfo().insert(c);

	}
	private void LandingsInTreatment(Flight f) {
		double fail=Math.random();
		if(fail<0.25){
			((Landings) f).setWhoSentYou("RunwayDirector");
			this.myAP.getTechnicalsList().insert((Landings)(f));
		}
		else
			this.myAP.getLogisticalsList().insert((Landings)(f));

	}

}


public class Employee {
	
	public Employee(){	
	}
	/**
	 * sleep method for all employees (count the sleep time per each flight thread)
	 * @param goSleep number in mili seconds
	 * @param f object of flight
	 */
	public void sleep(double goSleep,Flight f){
		try        
		{
			Thread.sleep((long) goSleep);
			double sleep=(goSleep/1000.0);
			f.HangTime+=sleep;
		} 
		catch(InterruptedException e) 
		{
			Thread.currentThread().interrupt();
		}

	}

}

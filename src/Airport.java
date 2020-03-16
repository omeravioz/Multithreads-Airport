import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;



public class Airport {
	private SortedQueue <Flight> FlightList;
	private Queue <Landings> TechnicalsList;
	private Queue <Landings> LogisticalsList;
	private Queue <FlightDetails> FlightInfo;
	private Bounded_Queue<Landings> FuelCrewList;
	private Queue<Landings> SecurityManList;
	private Vector<Thread> flightThreads = new Vector<Thread>();//vector of all flight threads
	private Vector<Thread> workersThreads = new Vector<Thread>();//vector of all employee threads
	private Map<String,Integer> map=new HashMap<String,Integer>();

	private int counter=0; //count number of flights
	private  int totalTruck=0;
	private int notc; //number of technical crews 
	private Double swt;// security work time 

	/**
	 * parametric constructor
	 * @param FileName -flight data file 
	 * @param numberOfTechnicalCrews - the number of technical crews given by the user
	 * @param securityWorkTime - the work time of security crews per flight, given by the user
	 */
	public Airport(String FileName, int numberOfTechnicalCrews, Double securityWorkTime){
		Read(FileName);
		this.notc=numberOfTechnicalCrews;
		this.swt=securityWorkTime;
		SWD();//start work day method
	}
	/**
	 * start work day method
	 * this method initate all program data and threads  
	 */
	public void SWD() 
	{
		ManagementCrew mc=new ManagementCrew("mc", this, false, getCounter()); 
		Thread t = new Thread(mc); //creating management thread
		/********************creating data base per each work station at airport**********************/
		TechnicalsList = new Queue <Landings>(this,mc);  
		LogisticalsList = new Queue <Landings>(this,mc);
		SecurityManList=new Queue<>(this, mc);
		FuelCrewList=new Bounded_Queue<>(this, mc, 8);
		FlightInfo = new Queue <FlightDetails>(this,mc);
		FlightList= new SortedQueue <Flight>(this);

		createRunway(mc);//creates runway directors threads
		createLogistic(mc); //creates logistical crews threads
		createTechnical(mc,notc);//creates technical crews threads
		createSecurity(mc);//creates security crews threads
		createFuelCrew(mc);//creates fuel crews threads
		startThreads();  //start all threads (flights + staff of employees ) 
		t.start();//start management director thread
	}

	/**
	 * this method start all airport threads : flights & employees
	 */
	private void startThreads() {
		for(Thread e: workersThreads){
			e.start();
		}
		for(Thread e: flightThreads){
			e.start();
		}
	}

	/**
	 * this methods creates all threads of technical crews
	 * @param mc management crew pointer
	 * @param numberOfTechnicalCrews given number from gui user
	 */
	private void createTechnical(ManagementCrew mc,int numberOfTechnicalCrews) {
		for(int i = 1;i<(numberOfTechnicalCrews+1);i++){
			TechnicalCrew tc = new TechnicalCrew(this,mc,"tc1");
			Thread t = new Thread(tc);
			this.workersThreads.add(t); //employees thread
		}
	}
	/**
	 *  this methods creates all threads of fuel crews
	 * @param mc management crew pointer
	 */
	private void createFuelCrew(ManagementCrew mc) {
		for(int i = 1;i<3;i++){
			FuelCrew fc = new FuelCrew(this, mc, 5000*i);
			Thread t = new Thread(fc);
			this.workersThreads.add(t);
		}

	}
	/**
	 * this methods creates all threads of security crews
	 * @param mc management crew pointer
	 */
	private void createSecurity(ManagementCrew mc) {
		for(int i = 0;i<2;i++){
			SecurityMan sm = new SecurityMan(this, mc);
			Thread t = new Thread(sm);
			this.workersThreads.add(t);
		}
	}

	public void startThread()
	{
		for(int i = 0;i<this.flightThreads.size();i++)
			this.flightThreads.get(i).start();
		for(int i = 0;i<this.workersThreads.size();i++)
			this.workersThreads.get(i).start();
	}

	/**
	 * this methods creates all threads of runway crews
	 * @param mc management crew pointer
	 */
	public void createRunway(ManagementCrew mc)
	{
		for(int i = 0;i<3;i++)
		{
			RunwayDirector rw = new RunwayDirector(this,mc);
			Thread t = new Thread(rw);
			this.workersThreads.add(t);
		}
	}
	/**
	 * this method creates all threads of logistical crews
	 * @param mc management crew pointer
	 */
	public void createLogistic(ManagementCrew mc){
		int num=20;
		for(int i = 0;i<3;i++)
		{
			LogisticsCrew lc = new LogisticsCrew("lc"+i,this,50+num*i,mc);
			Thread t = new Thread(lc);
			this.workersThreads.add(t);
		}
	}
	/**
	 * this method read from text file of flights
	 * @param type file name 
	 */
	public void Read(String type){	

		try{
			FileReader inputFile = new FileReader(type); //Create object of FileReader

			BufferedReader bufferReader = new BufferedReader(inputFile);  //Instantiate the BufferedReader Class

			//Variable to hold the one line data
			String line;
			// Read file line by line and print on the console

			while ((line = bufferReader.readLine()) != null)   {
				String[] splitStr = line.split("\t"); //space per each column
				if(!splitStr[1].equals("people")){ //not first row of the file
					checkLandingsOrDepartures(splitStr);
				}
			}
			bufferReader.close();
		}catch(Exception e){
			System.out.println("Error while reading file line by line:" + e.getMessage());                      
		}
	}
	/**
	 * this method checks if flight is departure or landing, and creates an object with that type of flight
	 * @param splitStr splited row of text files
	 */
	private void checkLandingsOrDepartures(String[] splitStr) {
		counter++;

		int num=(int)splitStr[3].charAt(0);

		if(num>=48&&num<=57){  // ascii value of  numbers 0-9. check if is landings
			creatLanding(splitStr);

		}
		else{ //departures
			creatDeparture(splitStr);

		}

	}
	/**
	 * this method creates thread of departure from flight list 
	 * @param splitStr splited row of text files
	 */
	private void creatDeparture(String[] splitStr) {
		Departures obj= new Departures((splitStr[0]), Integer.parseInt(splitStr[1])
				, Integer.parseInt(splitStr[2]), splitStr[3],this);
		mostPopularDest(obj);
		Thread t=new Thread(obj);
		this.flightThreads.add(t);
	}
	/**
	 * this method creates thread of landing from flight list 
	 * @param splitStr splited row of text files
	 */
	private void creatLanding(String[] splitStr) {
		Landings obj=new Landings((splitStr[0]), Integer.parseInt(splitStr[1])
				, Integer.parseInt(splitStr[2]), Integer.parseInt(splitStr[3]),this);
		Thread t=new Thread(obj);
		this.flightThreads.add(t);
	}
	private void mostPopularDest(Departures obj) {
		boolean flag=false;
		Iterator<Map.Entry<String,Integer>>iter=map.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<String,Integer> entry = iter.next();
			if(entry.getKey().equals(obj.dest)){
				flag=true;
				map.put(obj.dest, entry.getValue()+1);
			}
		}
		if(flag==false)
			map.put(obj.dest,1);
			
		
		

	}

	/***************************************setters and getters for fields***********************************************************************/
	public int getCounter()
	{
		return this.counter;
	}

	public void setCounter()
	{
		this.counter--;
	}

	public Queue <Landings> getTechnicalsList() {
		return TechnicalsList;
	}

	public Queue <Landings> getLogisticalsList() {
		return LogisticalsList;
	}

	public Queue <FlightDetails> getFlightInfo() {
		return FlightInfo;
	}

	public Queue <Landings> getSecurityManList() {
		return SecurityManList;
	}

	public SortedQueue<Flight> getFlightList() {
		return FlightList;
	}

	public Bounded_Queue<Landings> getFuelCrewList(){
		return FuelCrewList;
	}

	public int getTotalTruck() {
		return totalTruck;
	}

	public void setTotalTruck(int totalTruck) {
		this.totalTruck += totalTruck;
	}

	public int getNotc() {
		return notc;
	}

	public Double getSwt() {
		return swt;
	}
	public Map getmap(){
		return map;
	}
}

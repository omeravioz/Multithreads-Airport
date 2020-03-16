import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class DataBase
{
	private Connection con;

	public DataBase(){
		try {
			con=getConnection();
			createTables();

		} catch (Exception e){ 
			e.printStackTrace();
		}
	}

	public  void createTables()
	{ // create tables in sql

		createLandingTable("Landings",con);
		createTakeoffsTable("Takeoffs",con);


	}

	public  void createLandingTable(String tableName,Connection con)
	{
		String str = "CREATE TABLE " + tableName + "(ID varchar(10), Passengers int,"
				+ " Cargo int, Cost int, isSecurityIssue bool, timeInAirfield int)";
		try 
		{
			PreparedStatement drope = con.prepareStatement("Drop TABLE IF EXISTS "+ tableName); //delete the old table if exist
			drope.executeUpdate();
			PreparedStatement create = con.prepareStatement(str); //create new table 
			create.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}	
		finally {
			System.out.println("create Table completed.");
		}
	}
	public  void createTakeoffsTable(String tableName,Connection con)
	{

		String str = "CREATE TABLE " + tableName + "(ID varchar(10), Passengers int, Destination varchar(30), timeInAirfield int)";
		try 
		{

			PreparedStatement drope = con.prepareStatement("Drop TABLE IF EXISTS "+ tableName); //delete the old table if exist
			drope.executeUpdate();
			PreparedStatement create = con.prepareStatement(str); //create new table 
			create.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}	
		finally {
			System.out.println("create Table completed.");
		}
	}

	public  void insertLandings(FlightDetails l, String tableName)
	{//insert values into Landing table
		final String ID = l.flightCode;
		final int Passengers= l.Passengers;
		final int Cargo=l.Cargo;
		final int Cost = (int) l.Cost;
		int isSecurityIssue;
		if(l.isSecurityIssue) //if isSecurityIssue is true return 1, if false return 0
			isSecurityIssue=1;
		else
			isSecurityIssue=0;

		final int timeInAirfield=(int) l.HangTime;

		String str= "INSERT INTO " + tableName + "(ID, Passengers, Cargo, Cost, isSecurityIssue, timeInAirfield )"
				+ " VALUES('" + ID + "','" + Passengers + "','" + Cargo + "','" + Cost+ "','" + isSecurityIssue+"','" + timeInAirfield + "')";

		try
		{	

			PreparedStatement posted = con.prepareStatement(str); // insert values
			posted.executeUpdate();
		}
		catch (Exception e) {System.out.println(e);}

		finally
		{
			System.out.println("insert Completed.");
		}
	}
	public  void insertTakeoff(FlightDetails s, String tableName)
	{////insert values into Takeoff table
		final String ID = s.flightCode;
		final int Passengers= s.Passengers;
		final String Destination=s.Destination;
		final int timeInAirfield=(int) s.HangTime;

		String str= "INSERT INTO " + tableName + "(ID, Passengers, Destination, timeInAirfield )"
				+ " VALUES('" + ID + "','" + Passengers + "','" + Destination + "','" + timeInAirfield + "')";

		try
		{	
			PreparedStatement posted = con.prepareStatement(str); // Takeoff
			posted.executeUpdate();
		}
		catch (Exception e) {System.out.println(e);}

		finally
		{
			System.out.println("insert Completed.");
		}
	}

	public static Connection getConnection() throws Exception 
	{ // create connection
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/test";
			Connection conn=DriverManager.getConnection(url, "root", "root");



			return conn;

		}
		catch (Exception e) 
		{
			e.printStackTrace() ;
		}
		return null;	
	}
}
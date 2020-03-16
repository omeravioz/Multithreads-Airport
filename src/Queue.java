
import java.util.Vector;

public class Queue<T>
{
	protected ManagementCrew mc; 
	protected Airport myAp;
	protected Vector <T> t; //generic vector
	
	public Queue(){
		
	}
//parametric constructor
	public Queue(Airport myAp,ManagementCrew mc)
	{
		t=new Vector<T>();
		this.myAp = myAp;
		this.mc=mc;
	}

	//parametric constructor
	public Queue(Airport myAP) {
		this.myAp=myAP;
		
	}
/*insert object to the end of the queue*/
	public synchronized void insert(T item)
	{
		t.add(item);
		notifyAll();
	}
/*extracting first object in queue*/
	public synchronized T extract() 
	{ 
		while(t.isEmpty()&&this.mc.getEndOfDay()==false ) {//queue is empty and day not over yet
			try {
				wait();
			} catch (InterruptedException e) {}

		}
		if(t.isEmpty()&&this.mc.getEndOfDay()==true) //end of work day
			return null;
		
		return t.remove(0) ;    //else - extract object
	}

	//returns queues size
	public synchronized int size()
	{
		return t.size();
	}
	//wake up all threads
	public synchronized void wakeUp()
	{
		notifyAll();
	}









}
import java.util.Vector;

public class Bounded_Queue<T> extends Queue<T> {
	private int maxSize; //max size of bounded queue

	/**
	 * parametric constructor
	 * @param myAp airport object pointer
	 * @param mc management crew pointer
	 * @param maxSize
	 */
	public Bounded_Queue(Airport myAp,ManagementCrew mc,int maxSize){
		super(myAp,mc);
		t=new Vector<T>();
		this.maxSize=maxSize;	
	}
	/**
	 * this method insert object to the end of queue (if possible) 
	 */
	public synchronized void insert(T item) 
	{
		while(super.size() == maxSize) //queue is full
		{
			try
			{
				wait(); //let the threads wait until valid space in the queue
			}
			catch (InterruptedException e){}
		} 

		super.insert(item); // there is space to insert for queue
		notifyAll(); //wake up all threads 
	}
	/**
	 * this method extracts the first object in bounded queue
	 */
	public synchronized T extract() 
	{ 
		while(t.isEmpty()&&this.mc.getEndOfDay()==false ) { //queue is empty and the work day is not over yet
			try {
				wait();//let the threads wait 
			} catch (InterruptedException e) {}


		}
		notifyAll();//wake up all threads 
		if(t.isEmpty()&&this.mc.getEndOfDay()==true) // if work day is over than return null 
			return null;
		return t.remove(0) ;   //else - if queue is not empty and work day is not over - return object    
	}

}

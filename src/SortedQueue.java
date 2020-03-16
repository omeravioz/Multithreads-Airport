import java.util.Vector;

public class SortedQueue<T> extends Queue<T> {
	
	public SortedQueue(){//empty constructor	
	}

	/*parametric constructor */
	public SortedQueue(Airport myAP) {
		super(myAP);
		t=new Vector<T>();
	}
/*extract object from first place in queue*/
	public synchronized T extract() 
	{ 
		while(t.isEmpty()&&this.myAp.getCounter()>0) { //queue is empty and there us flight  left to deal with
			try {
				wait();
			} catch (InterruptedException e) {}

		}	
		notifyAll();
		T temp = containsLanding();
		if(temp == null && !t.isEmpty()) //departure object
			return t.remove(0);
		return temp; //landing object
	}
	/*this method checks for landing object(priority to extract first) in flight queue */
	public T containsLanding()
	{
		for(int i = 0; i<this.size();i++) //flight queue
			if(this.t.get(i) instanceof Landings)
				return this.t.remove(i); //return landing object
		return null;//departure object
	}
}

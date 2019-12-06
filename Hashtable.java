import java.lang.Math;
import java.util.ArrayList;

public class Hashtable{
	private class Node{
		String key;
		String data;
		Node next;

		public Node(String a, String b){
			key = a;
			data = b;
			next = null;
		}
	}

	ArrayList<Node> bucket; 
	int numBucket;
	int size;

	public Hashtable(){
		bucket = new ArrayList<Node>();
		numBucket = 10000;
		size = 0;

		for (int i = 0; i < numBucket; i++){
			bucket.add(null);
		}
	}

	private int getIndex(String key){
		return (Math.abs(key.hashCode()) % numBucket);
	}

	public boolean containsKey(String key){
		if (get(key) == null){
			return false;
		} else {
			return true;
		}
	}

	public String get(String key){
		int index = getIndex(key);
		Node head = bucket.get(index);

		while(head != null){
			if ((head.key).equals(key)){
				return head.data;
			}
			head = head.next;
		}
		return null;
	}

	public void put(String key, String value)
	{
		int index = getIndex(key);
		Node head = bucket.get(index);

		while (head != null)
		{
			if (head.key.equals(key))
			{
				head.data = value;
				return;
			}
			head = head.next;
		}

		size++;
		head = bucket.get(index);
		Node newNode = new Node(key, value);
		newNode.next=head;
		bucket.set(index, newNode);

		if ((1.0*size)/numBucket >= 0.75)
		{
			ArrayList<Node> temp = bucket;
			numBucket = 2 * numBucket; 
            bucket = new ArrayList<Node>(numBucket); 
            size = 0; 
            for (int i = 0; i < numBucket; i++) 
                bucket.add(null); 
  
            for (Node headNode : temp) 
            { 
                while (headNode != null) 
                { 
                    put(headNode.key, headNode.data); 
                    headNode = headNode.next; 
                } 
            } 
		}
	}//put

	public String remove(String key) throws Exception{
		int index = getIndex(key);
		Node head = bucket.get(index);

		Node previous = null;
		while (head != null && head.key.equals(key) == false){
			previous = head;
			head = head.next;
		}

		if (head == null){
			throw new Exception();
		}
		size--;

		if (previous != null){
			previous.next = head.next;
		} else {
			bucket.set(index, head.next);
		}
		return head.data;
	}
}
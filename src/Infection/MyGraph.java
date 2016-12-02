package Infection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;


public class MyGraph {
	private ArrayList<User> users;
	private ArrayList<User> aTestComponent;
	private ArrayList<User> bTestComponent;
	
	private ArrayList<User> aPoints;
	
	
	/*
	 * Initializes graph of users
	 */
	public MyGraph(){
		users = new ArrayList<User>();
		aTestComponent =  new ArrayList<User>();
		bTestComponent = new ArrayList<User>();
		aPoints = new ArrayList<User>();
	}
	
	/*
	 * Adds a user to the graph
	 * @param User u: adds a user to the graph
	 */
	public void addUser(User user){
		users.add(user);
	}
	
	public User getAnyUser(){
		return users.get(0);
	}
	
	/*
	 * Returns the users in the graph
	 * 
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public User getNode(String name){
		for(User u: users){
			String compare = u.getName();
			if(name.equals(compare)){
				return u;
			}
		}
		return null;
	}
	
	/*
	 * Infect Single infects one node
	 * @param User u: The node which should be infected
	 * @param int newVersion: The new version to be placed on the node
	 */
	
	public void infectSingle(User u, String newVersion){
		u.changeVersion(newVersion);
	}
	
	/*
	 * Sets the A component
	 * @param User user: The user that will become aTestComponent
	 */
	
	public void setAComponent(ArrayList<User> users){
		aTestComponent = users;
	}
	
	/*
	 * Sets the B component
	 * @param User user: The user that will become bTestComponent
	 */
	public void setBComponent(ArrayList<User> users){
		bTestComponent = users;
	}
	
	/*Total Infection infects the entire connected component starting with the inputed user
	 * @param User u: The user from which the infection starts
	 * @param int newVersion: The version that is being placed on each of the users
	 */
	public void totalInfection(User user, String newVersion){
		//A BFS search that infects the entire connected component
		HashSet<User> visited = new HashSet<User>();
		Queue<User> queue = new LinkedList<User>();
		
		queue.add(user);
		visited.add(user);
		while(queue.isEmpty() != true){
 			User peek = queue.remove();
			peek.changeVersion(newVersion);
			ArrayList<User> newEdges = peek.getEdges();
			for(User edge: newEdges){
				if(visited.contains(edge) == false){
					queue.add(edge);
					visited.add(edge);
				}
			}
			
		}
	 }
	
	/*Exact Infection checks if an exact number of people can be infected(whole components must be infected)
	 * @param User u: The user from which the infection starts
	 * @param int newVersion: The version that is being placed on each of the users
	 */
	public boolean exactInfection(User user, String newVersion, int numInfections){
		TreeMap<String,User> components = makeTreeMap(user);
		ArrayList<Integer> componentNumbers = new ArrayList<Integer>();
		
		for(Map.Entry<String,User> entry : components.descendingMap().entrySet()) {
			  String key = entry.getKey();
			  StringBuilder num = new StringBuilder();
	    	  for(int i = 0;i< key.length();i++){
	    		  if(key.charAt(i) >= 48 && key.charAt(i) <= 57){
	    			  num.append(key.charAt(i));
				  }else{
					  break;
				  }
			   }
		      int num1 = Integer.parseInt(num.toString());
			  componentNumbers.add(num1);
		}
		
		boolean[][] solution = new boolean[componentNumbers.size() + 1][numInfections + 1];
		
		for(int i=1;i<=numInfections;i++){
			solution[0][i]=false;
		}
		
		for(int i=0;i<=componentNumbers.size();i++){
			solution[i][0]=true;
		}
		
		for(int i=1;i<=componentNumbers.size();i++){
			for(int j=1;j<=numInfections;j++){				
				solution[i][j] = solution[i-1][j];
				
				if(solution[i][j]==false && j>=componentNumbers.get(i-1))
					solution[i][j] = solution[i][j] || solution[i-1][j-componentNumbers.get(i-1)];				
			}
		}		
		return solution[componentNumbers.size()][numInfections];
		
	}
	
	

	

	/*Limited Infection infects a certain number of users trying to keep as many coach student pairs with the same version
	 *@param User u: The user from which the infection starts 
	 *@param int newVersion: The version that is being placed on each of the users 
	 *@param int numInfections: The limit of infections
	 *
	 *My algorithm is to pick the biggest connected component that is less than or equal to the number. If none 
	 *exist pick the smallest component and infect as many as possible till the limit is hit
	 */
	
	public ArrayList<User> limitedInfection(User user, String newVersion, int numInfections){
		//first find the number of connected components and the size of each 

		
		TreeMap<String,User> components = makeTreeMap(user);
		
		//Now I have a treeMap mapping String(which tells me number of nodes in component) to 
		//User(The user with the highest degree in this component which should signify coach in this scenario)
		
		//Run through the TreeMap and pick the biggest component less than the limit. 
		//If there is no component less than the limit pick the smallest component 
	
		
		int numInfected = 0;
		User bfs = null; 
		for(Map.Entry<String,User> entry : components.descendingMap().entrySet()) {
			if(aPoints.contains(entry.getValue()) == false){
				String key = entry.getKey();
				  
				  int numKey = getNumKey(key);
				  if(numKey <= numInfections){
					  numInfected = numKey;
					  bfs = components.get(key);
					  break;
				  }
			}
			  

		}
		
		
		//means they are all bigger so pick smallest and keep going till u complete the component.This will overshoot
		//however it will make sure all coach student pairs have the same version. In real life there will always be 
		//good sized components that are near the number passed in. I didnt think it was necessary to count nodes and stop
		//exactly at the number of infections passed in. I thought it was best to fill the whole component.
		if(bfs == null){
			if(aPoints.size() == 0){
				bfs = components.get(components.firstKey());
			}else{
				int c = 0;
				for(Map.Entry<String,User> entry : components.descendingMap().entrySet()) {
					c++;
					if(c == 2){
						bfs = entry.getValue();
						break;
					}
				}
			}
				
			HashSet<User> visited = new HashSet<User>();
			Queue<User> queue = new LinkedList<User>();
			
			visited.add(bfs);
			queue.add(bfs);
			
			while(queue.isEmpty() != true){
				User peek = queue.remove();
				peek.changeVersion(newVersion);
				ArrayList<User> newEdges = peek.getEdges();
				for(User edge: newEdges){
					if(visited.contains(edge) == false){
						queue.add(edge);
						visited.add(edge);
					}
				}
				
			}
			ArrayList<User> answer = new ArrayList<User>();
			answer.add(bfs);
			if(newVersion.equals("A")){
				aPoints = answer;
			}
			return answer;
			
		}else{
			/*
			 * In this case it is possible to fill more than 2 components because there will
			 *  be extra space remaining after the first component. Iterate from biggest to smallest 
			 *  until you pass the infetionNumber.
			 */
//			System.out.println("Num infected: " + numInfected);
			ArrayList<User> points = new ArrayList<User>();
			points.add(bfs);
			int remaining = numInfections - numInfected;
			if(numInfected - numInfections > 0){
				for(Map.Entry<String,User> entry : components.entrySet()) {
					if(aPoints.contains(entry.getValue()) == false){
						String key = entry.getKey();
						 bfs = components.get(key);
						 if(points.contains(bfs) == false){
							  int numKey = getNumKey(key);
							  if(remaining - numKey <= 0){
								  points.add(bfs);
								  break;
							  }else{
								  remaining -= numKey;
								  points.add(bfs);
							  }
						 }
					}
					 
				}
			}
			
				
			infectPoints(points,newVersion);
			if(newVersion.equals("A")){
				aPoints = points;
			}
			return points;
		}
	}
	
	/*
	 * Helper function to parse my String that is the key for the TreeMap
	 * @param String key: the string to be parsed
	 */
	
	public int getNumKey(String key){
		  StringBuilder num = new StringBuilder();
		  for(int i = 0;i<key.length();i++){
			  if(key.charAt(i) >= 48 && key.charAt(i) <= 57){
				  num.append(key.charAt(i));
			  }else{
				  break;
			  }
		  }

		  return Integer.parseInt(num.toString());
	}
	
	/*
	 * Helper function to infect points
	 * @param ArrayList<User> points: The points to be infected
	 * @param newVersion: the new version to be placed on each node
	 */
	
	public void infectPoints(ArrayList<User> points,String newVersion){
		HashSet<User> visited = new HashSet<User>();
		Queue<User> queue = new LinkedList<User>();
		for(User user: points){
			queue.add(user);
			visited.add(user);
			while(queue.isEmpty() != true){
				User n = queue.remove();
				n.changeVersion(newVersion);
				ArrayList<User> newEdges = n.getEdges();
				for(User edge: newEdges){
					if(visited.contains(edge) == false){
						queue.add(edge);
						visited.add(edge);
					}
				}
				
			}
		}
		
	}
	

	
	/*
	 * My extra feature that find the 2 best components to infect for A/B testing
	 * This feature finds 2 components around 1/10 of the graph and infects with 2 different versions
	 * @param User u: The user to start the search at
	 */
	
	
	public ArrayList<User> abComponents(User u){
		//first find the number of connected components and the size of each 

		TreeMap<String,User> components = makeTreeMap(u);
		
		
		int numUsers = 0;
		for(Map.Entry<String,User> entry : components.entrySet()) {
			 String key = entry.getKey();
			 numUsers += getNumKey(key);
		}
		int componentNum = numUsers/10;
		
		
		User aTest = null;
		User bTest = null;
		
		for(Map.Entry<String,User> entry : components.entrySet()) {
			 String key = entry.getKey();
			 int numKey = getNumKey(key);
			 if(aTest != null){
				 bTest = components.get(key);
				 break;
			 }
			 if(numKey >= componentNum){
				 aTest = components.get(key);
			 }
			
			 
		}
		
		
		ArrayList<User> answer = new ArrayList<User>();
		answer.add(aTest);
		answer.add(bTest);		
		return answer;
	}
	
	/*
	 * Analyze the components and figure out where ratings were higher
	 * Fill in all other components with that higher rated version
	 */
	
	public boolean analyzeABTest(){
		double averageRatingA = getRatings(aTestComponent);
		double averageRatingB = getRatings(bTestComponent);
		
		System.out.println("The average for the A component is: " + averageRatingA);
		System.out.println("The average for the B component is: " + averageRatingB);

		if(averageRatingA >= averageRatingB){
			System.out.println("We will now infect everyone with A");
			repealVersion(bTestComponent, "A");
			infectEveryone("A",aTestComponent);
			return true;
		}else{
			System.out.println("We will now infect everyone with B");
			repealVersion(aTestComponent,"B");
			infectEveryone("B",bTestComponent);
			return false;
		}
		
		
	}
	
	/*
	 * Change the version from the worse rated one to the better rated one
	 */
	
	public void repealVersion(ArrayList<User> users, String version){
		for(User user: users){
			totalInfection(user,version);
		}
		
	}
	
	/*
	 * Infect everyone with a the better version. Make sure ratings are above 5 else redo the version and try again
	 * This is just randomly done
	 */
	
	public void infectEveryone(String version, ArrayList<User> users){
		TreeMap<String,User> components = makeTreeMap(users.get(0));
		for(Map.Entry<String,User> entry : components.entrySet()) {
			 User value = entry.getValue();
			 totalInfection(value,version);
			 ArrayList<User> values = new ArrayList<User>();
			 values.add(value);
			 double rating = getRatings(values);
			 while(rating < 5){
//				 System.out.println("Bad Rating. Update version. Try again"); 
				 totalInfection(value,version);
				 rating = getRatings(values);
			 }
			 
		}
		
		
	}
	
	/*
	 * Get the ratings for a certain connected component
	 */
	
	public double getRatings(ArrayList<User> users){
		HashSet<User> visited = new HashSet<User>();
		Queue<User> queue = new LinkedList<User>();
		
		
		for(User user: users){
			queue.add(user);
			visited.add(user);
		}
		
		
		double numUsers = 0;
		double count = 0;
		
		while(queue.isEmpty() != true){
 			User peek = queue.remove();
 			numUsers++;
 			count += peek.provideFeedback();
			ArrayList<User> newEdges = peek.getEdges();
			for(User edge: newEdges){
				if(visited.contains(edge) == false){
					queue.add(edge);					
					visited.add(edge);
					
				}
			}
			
		}
		
		return count/numUsers;
	}
	
	
	
	/*
	 * A Comparator for my TreeMap because each String is a number followed by a name(I do this to keep 
	 * each key unique). The number is the number of nodes in the component and the name is the
	 * node with the highest degree(probably the most important coach)
	 */
	class MyStringComp implements Comparator<String>{
		 
	    @Override
	    public int compare(String s1, String s2) { //gets two keys and compares them. This function is used to sort the TreeMap by keys
	    	int num1 = getNumKey(s1);
	    	int num2 = getNumKey(s2);
	    	
	        if(num1 < num2){          //compare the 2 numbers against each other to sort them in the TreeMap
	        	return -1;
	        }else if (num2 < num1){
	        	return 1;
	        }else{
	        	return 0;
	        }
	    }
	}
	

	
	/*
	 * This function analyzes the graph for components and helps build my TreeMap.
	 * Helper function to make treemap data structure
	 * @param User u: The node from which to start the search
	 */
	
	public TreeMap<String,User> makeTreeMap(User user){
		HashSet<User> visited = new HashSet<User>();
		Queue<User> queue = new LinkedList<User>();
		queue.add(user);
		visited.add(user);
		
		
		int connectedCount = 0;
		int highestDegree = -1;
		User highestCoach = null;
		while(queue.isEmpty() != true){
			User peek = queue.remove();
			connectedCount++;
			if(peek.getEdges().size() > highestDegree){
				highestDegree = peek.getEdges().size();
				highestCoach = peek;
			}
			ArrayList<User> newEdges = peek.getEdges();
			for(User edge: newEdges){
				if(visited.contains(edge) == false){
					queue.add(edge);
					visited.add(edge);
				}
			}
			
		}
		TreeMap<String,User> components = new TreeMap<String,User>(new MyStringComp());
		String name = Integer.toString(connectedCount) + highestCoach.getName();
		components.put(name,highestCoach);
				
		for(User edge2: users){
			connectedCount = 0;
			highestDegree = -1;
			highestCoach = null;
			if(visited.contains(edge2) == false){
				queue.add(edge2);
				visited.add(edge2);
				while(queue.isEmpty() != true){
					User n = queue.remove();
					connectedCount++;
					if(n.getEdges().size() > highestDegree){
						highestDegree = n.getEdges().size();
						highestCoach = n;
					}
					 
					ArrayList<User> newEdges = n.getEdges();
					for(User edge3: newEdges){
						if(visited.contains(edge3) == false){
							queue.add(edge3);
							visited.add(edge3);
						}
					}
				}
				name = Integer.toString(connectedCount) + highestCoach.getName();
				components.put(name, highestCoach);
			}
		}
		
		return components;
	}

	
}




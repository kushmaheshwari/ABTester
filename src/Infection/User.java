package Infection;
import java.util.ArrayList;
import java.util.Random;



public class User{
	private String siteVersion;
	private ArrayList<User> edges;
	private String myName;
	
	public User(String name){
		edges = new ArrayList<User>();
		siteVersion = "X";
		myName = name;
	}
	
	/*
	 * Adds a edge to the node
	 * @param User edge: the edge being added to the adjacency list
	 */
	
	public void addEdge(User edge){
		edges.add(edge);
		
		ArrayList<User> edges = edge.getEdges();
		edges.add(this);
		edge.setEdges(edges);
	}
	
	/*
	 * Provide feedback with a random integer. Cant really do real feedbck so i just do a random integer 
	 */
	
	public int provideFeedback(){  
		Random rn = new Random();
		int answer = rn.nextInt(10) + 1;
		return answer;
	}
	
	/*
	 * Sets edges list to new edges list passed in
	 * @param ArrayList<User> edges: a new list of edges for this user
	 */
	
	public void setEdges(ArrayList<User> edges){
		this.edges = edges;
	}

	
	public ArrayList<User> getEdges(){
		return edges;
	}
	
	public String getVersion(){
		return siteVersion;
	}
	
	/*
	 * @param String version: the new version for this user
	 */
	
	public void changeVersion(String version){
		siteVersion = version;
	}
	
	public String getName(){
		return myName;
	}
	
	@Override
	public String toString(){
		return "Name: " + myName;
	}
	
}
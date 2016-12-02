package Infection;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class abTester {
	
	public static MyGraph userBase = new MyGraph();
	public static Graph visualGraph = new SingleGraph("User Base");
	public static Scanner reader = new Scanner(System.in);
	
	public abTester(){
		userBase = new MyGraph();
	}
	
	/*
	 * This parseFile function reads through a file and makes a graph off of it
	 */
	public static void parseFile(String file){
		file = "/Users/kushmaheshwari/Documents/workspace/FinalProject/TestGraph";
		try {
		    BufferedReader in = new BufferedReader(new FileReader(file));
		    String str;
		    boolean nodes = false;
		    while ((str = in.readLine()) != null){
		    	if(str.equals("Nodes:")){
		    		nodes = true;
		    	}else if(str.equals("Edges:")){
		    		nodes = false;
		    	}else{
		    		if(nodes){
		    			processNode(str);
		    		}else{
		    			processEdges(str);
		    		}
		    	}
		    }
		        
		    in.close();
		} catch (IOException e) {
			
		}
		
	}
	
	/*
	 * Makes all the nodes and adds them to both graphs
	 */
	public static void processNode(String node){		
		userBase.addUser(new User(node));
		visualGraph.addNode(node);
		
		Node a = visualGraph.getNode(node);
		a.addAttribute("ui.label", "X");
		
	}
	
	/*
	 * Makes all the edges and adds them to both graphs
	 */
	public static void processEdges(String edge){
		String[] nodeSplit = edge.split(":");
		User node = userBase.getNode(nodeSplit[0]);
		String[] edges = nodeSplit[1].split(",");
		for(String e: edges){
			User u = userBase.getNode(e);
			node.addEdge(u);
			
			visualGraph.addEdge(nodeSplit[0] + e, nodeSplit[0], e);
		}
		
	}
	
	/*
	 * Infects the program AB way
	 */
	public static void infectABWay(){
		User u = userBase.getAnyUser();
		ArrayList<User> users = userBase.abComponents(u);
		
		ArrayList<User> aComps = new ArrayList<User>();
		aComps.add(users.get(0));
		
		ArrayList<User> bComps = new ArrayList<User>();
		bComps.add(users.get(1));
		
		Node A = visualGraph.getNode(users.get(0).getName());
		Node B = visualGraph.getNode(users.get(1).getName());
 
		A.addAttribute("ui.label", "A.s");
		B.addAttribute("ui.label", "B.s");
		
		System.out.println("Continue with infection?");
		String word = reader.next();
		Iterator<Node> it = A.getBreadthFirstIterator();
		while(it.hasNext()){
			Node x = it.next();
			x.addAttribute("ui.label", "A");
		}
		
		it = B.getBreadthFirstIterator();
		while(it.hasNext()){
			Node x = it.next();
			x.addAttribute("ui.label", "B");
		}
		

		
		User aComponent = users.get(0);
		User bComponent = users.get(1);
		
		userBase.setAComponent(aComps);
		userBase.setBComponent(bComps);
		
		userBase.totalInfection(aComponent, "A");
		userBase.totalInfection(bComponent, "B");
		
		boolean a = userBase.analyzeABTest();
		
		System.out.println("Continue with entire graph?");
		 word = reader.next();
		
		if(a){
			it = visualGraph.getNodeIterator();
			while(it.hasNext()){
				Node x = it.next();
				x.addAttribute("ui.label", "A");
			}
		}else{
			it = visualGraph.getNodeIterator();
			while(it.hasNext()){
				Node x = it.next();
				x.addAttribute("ui.label", "B");
			}
		}
		
		
	}
	
	/*
	 * Infects the upper limit way
	 */
	public static void infectUpperLimitWay(int limit){
		User u = userBase.getAnyUser();
		ArrayList<User> componentsA = userBase.limitedInfection(u, "A", limit );
		ArrayList<User> componentsB = userBase.limitedInfection(u, "B", limit );
		for(User user: componentsA){
			Node start = visualGraph.getNode(user.getName());
			Iterator<Node> it = start.getBreadthFirstIterator();
			while(it.hasNext()){
				Node x = it.next();
				x.addAttribute("ui.label", "A");
			}
			
		}
		
		for(User user: componentsB){
			Node start = visualGraph.getNode(user.getName());
			Iterator<Node> it = start.getBreadthFirstIterator();
			while(it.hasNext()){
				Node x = it.next();
				x.addAttribute("ui.label", "B");
			}
			
		}
	 	
		userBase.setAComponent(componentsA);
		userBase.setBComponent(componentsB);
		
		boolean a = userBase.analyzeABTest();
		

		System.out.println("Continue with infection?");
		String word = reader.next();
		
		Iterator<Node> it;

		if(a){
			it = visualGraph.getNodeIterator();
			while(it.hasNext()){
				Node x = it.next();
				x.addAttribute("ui.label", "A");
			}
		}else{
			it = visualGraph.getNodeIterator();
			while(it.hasNext()){
				Node x = it.next();
				x.addAttribute("ui.label", "B");
			}
		}
		
	}
	/*
	 * Infects the hard upper limit way
	 */
	
	public static boolean infectHardLimitWay(int limit){
		User u = userBase.getAnyUser();
		if(userBase.exactInfection(u, "A", limit)){
			ArrayList<User> componentsA = userBase.limitedInfection(u, "A", limit );
			ArrayList<User> componentsB = userBase.limitedInfection(u, "B", limit );
			return true;
		}else{
			ArrayList<User> componentsA = userBase.limitedInfection(u, "A", limit );
			ArrayList<User> componentsB = userBase.limitedInfection(u, "B", limit );
			System.out.println("Try a new numer:");
			return false;
		}
		
	}
	
	public static void main(String[] args){
		/*
		 * This starts the entire ab tester
		 */
		System.out.println("Welcome to the A/B Tester:");
		 
		/*
		 * Tests for the input. Here you put a file and it read the file
		 */
		System.out.println("Please input in your userbase as a graph (Type in the file): ");
		String file = reader.nextLine(); 
		parseFile(file);
		
		visualGraph.display();
		
		/*
		 * In here I will present the graph as an image for users to see. 
		 * Every node will have a version associated with it which will be version X
		 */
		System.out.println("All users in the graph have version X right now");
		System.out.println();
		System.out.println("Now we will try to A/B test this user base");
		System.out.println("Please pick from the 3 options:\n A: We pick the 2 components.\n B: You give a upper limit for your components.\n C: You give a hard limit.");
		String option = reader.nextLine(); 
		
		/*
		 * Now the user gets to pick which sort of option they want to use
		 */
		
		//A is for the program way, B is for the upper limit way,C is for the hard limit way
		if(option.equals("A")){
			System.out.println("We will now infect 2 components with version A and version B");
			infectABWay();
		}else if(option.equals("B")){
			//B is when they give an upper limit
			System.out.println("Pick a upper limit number:");
			int limit = reader.nextInt();
			System.out.println("We will now infect 2 components with version A and version B");
			infectUpperLimitWay(limit);
		}else{ 
			System.out.println("Pick a hard upper limit number:");
			int limit = reader.nextInt();
			if(infectHardLimitWay(limit)){
				infectUpperLimitWay(limit);
			}else{
				limit = reader.nextInt();
				while(infectHardLimitWay(limit) == false){
					limit = reader.nextInt();
				}
				infectUpperLimitWay(limit);
			}
		}
		
		
		System.out.println("Now the A/B tester has finished properly putting the best version all over you userbase");
		System.out.println("Overall we can now put new versions when the time is right");

		
	}
	
	

}

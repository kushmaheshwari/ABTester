package InfectionTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Infection.*;

public class GraphTests {
	
	
	/*
	 * Tests 1 single connected component graph
	 */
	
	@Test 
	public void testTotalInfection(){
		MyGraph g = new MyGraph();

		
		User coach1 = new User("coach1");
		User u1 = new User("u1");
		User u2 = new User("u2");
		User u3 = new User("u3");
		User u4 = new User("u4");
		User u5 = new User("u5");
		User u6 = new User("u6");
		User u7 = new User("u7");
		coach1.addEdge(u1);
		coach1.addEdge(u2);
		coach1.addEdge(u3);
		coach1.addEdge(u4);
		coach1.addEdge(u5);
		coach1.addEdge(u6);
		coach1.addEdge(u7);
		
		User coach2 = new User("coach2");
		User u8 = new User("u8");
		User u9 = new User("u9");
		User u10 = new User("u10");
		User u11 = new User("u11");
		coach2.addEdge(u8);
		coach2.addEdge(u9);
		coach2.addEdge(u10);
		coach2.addEdge(u11);
		
		User adminCoach = new User("adminCoach");
		adminCoach.addEdge(coach1);
		adminCoach.addEdge(coach2);
		
		g.addUser(u1);
		g.addUser(u2);
		g.addUser(u3);
		g.addUser(u4);
		g.addUser(u5);
		g.addUser(u6);
		g.addUser(u7);
		g.addUser(u8);
		g.addUser(u9);
		g.addUser(u10);
		g.addUser(u11);
		g.addUser(coach1);
		g.addUser(coach2);
		g.addUser(adminCoach);
		
		ArrayList<User> nodes = g.getUsers();
		for(User n: nodes){
			assertEquals(n.getVersion(),"X");
		}
		
		g.totalInfection(adminCoach, "A");
		
		
		nodes = g.getUsers();
		for(User n: nodes){
			assertEquals(n.getVersion(),"A");
		}



	}
	
	/*
	 * Tests a graph with 2 connected components
	 */
	
	@Test 
	public void testTotalInfection2(){
		MyGraph g = new MyGraph();

		
		User coach1 = new User("coach1");
		User u1 = new User("u1");
		User u2 = new User("u2");
		User u3 = new User("u3");
		User u4 = new User("u4");
		User u5 = new User("u5");
		User u6 = new User("u6");
		User u7 = new User("u7");
		coach1.addEdge(u1);
		coach1.addEdge(u2);
		coach1.addEdge(u3);
		coach1.addEdge(u4);
		coach1.addEdge(u5);
		coach1.addEdge(u6);
		coach1.addEdge(u7);
		
		User coach2 = new User("coach2");
		User u8 = new User("u8");
		User u9 = new User("u9");
		User u10 = new User("u10");
		User u11 = new User("u11");
		coach2.addEdge(u8);
		coach2.addEdge(u9);
		coach2.addEdge(u10);
		coach2.addEdge(u11);
		
		
		g.addUser(u1);
		g.addUser(u2);
		g.addUser(u3);
		g.addUser(u4);
		g.addUser(u5);
		g.addUser(u6);
		g.addUser(u7);
		g.addUser(u8);
		g.addUser(u9);
		g.addUser(u10);
		g.addUser(u11);
		g.addUser(coach1);
		g.addUser(coach2);
		
		ArrayList<User> nodes = g.getUsers();
		for(User n: nodes){
			assertEquals(n.getVersion(),"X");
		}
		
		g.totalInfection(coach1, "A");
		
		
		nodes = coach1.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"A");
		}
		
		nodes = coach2.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"X");
		}



	}
	
	/*
	 * Tests a graph with 4 components and passes in a number that is smaller than any of the components
	 */
	
	
	@Test
	public void testLimitedInfection(){
		MyGraph g = new MyGraph();

		
		User adminCoach1 = new User("adminCoach1");
		User coach1 = new User("coach1");
		User u1 = new User("u1");
		User u2 = new User("u2");
		User u3 = new User("u3");
		User u4 = new User("u4");
		
		adminCoach1.addEdge(coach1);
		coach1.addEdge(u1);
		coach1.addEdge(u2);
		coach1.addEdge(u3);
		coach1.addEdge(u4);
		
		
		User adminCoach2 = new User("adminCoach2");
		User coach2 = new User("coach2");
		User coach3 = new User("coach3");
		User coach4 = new User("coach4");
		adminCoach2.addEdge(coach2);
		adminCoach2.addEdge(coach3);
		adminCoach2.addEdge(coach4);
		
		
		User u5 = new User("u5");
		User u6 = new User("u6");
		User u7 = new User("u7");
		User u8 = new User("u8");
		coach2.addEdge(u5);
		coach2.addEdge(u6);
		coach2.addEdge(u7);
		coach2.addEdge(u8);
		
		User u9 = new User("u9");
		User u10 = new User("u10");
		User u11 = new User("u11");
		User u12 = new User("u12");
		coach3.addEdge(u9);
		coach3.addEdge(u10);
		coach3.addEdge(u11);
		coach3.addEdge(u12);
		
		User u13 = new User("u13");
		User u14 = new User("u14");
		User u15 = new User("u15");
		User u16 = new User("u16");
		coach4.addEdge(u13);
		coach4.addEdge(u14);
		coach4.addEdge(u15);
		coach4.addEdge(u16);

		User coach5 = new User("coach5");
		User u17 = new User("u17");
		User u18 = new User("u18");
		User u19 = new User("u19");
		User u20 = new User("u20");
		coach5.addEdge(u17);
		coach5.addEdge(u18);
		coach5.addEdge(u19);
		coach5.addEdge(u20);
		
		User coach6 = new User("coach6");
		User u21 = new User("u21");
		User u22 = new User("u22");
		User u23 = new User("u23");
		User u24 = new User("u24");
		User u25 = new User("u25");
		User u26 = new User("u26");
		User u27 = new User("u27");
		User u28 = new User("u28");
		coach6.addEdge(u21);
		coach6.addEdge(u22);
		coach6.addEdge(u23);
		coach6.addEdge(u24);
		coach6.addEdge(u25);
		coach6.addEdge(u26);
		coach6.addEdge(u27);
		coach6.addEdge(u28);
		
			
		
		g.addUser(u1);
		g.addUser(u2);
		g.addUser(u3);
		g.addUser(u4);
		g.addUser(u5);
		g.addUser(u6);
		g.addUser(u7);
		g.addUser(u8);
		g.addUser(u9);
		g.addUser(u10);
		g.addUser(u12);
		g.addUser(u13);
		g.addUser(u14);
		g.addUser(u15);
		g.addUser(u16);
		g.addUser(u17);
		g.addUser(u18);
		g.addUser(u19);
		g.addUser(u20);
		g.addUser(u21);
		g.addUser(u22);
		g.addUser(u23);
		g.addUser(u24);
		g.addUser(u25);
		g.addUser(u26);
		g.addUser(u27);
		g.addUser(u28);
		g.addUser(adminCoach1);
		g.addUser(adminCoach2);
		g.addUser(coach1);
		g.addUser(coach2);
		g.addUser(coach3);
		g.addUser(coach4);
		g.addUser(coach5);
		g.addUser(coach6);
		
		g.limitedInfection(adminCoach1, "A", 3);
		
		ArrayList<User> nodes = coach5.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"A");
		}
		
		ArrayList<User> graphNodes = g.getUsers();
		for(User n: graphNodes){
			if(nodes.contains(n) == false && n.getName() != "coach5")
				assertEquals(n.getVersion(),"X");
		}



		
	}
	
	/*
	 * Tests a graph with 4 components and passes in a number that is greater than any of the components
	 */
	
	
	@Test
	public void testLimitedInfection2(){
		MyGraph g = new MyGraph();

		
		User adminCoach1 = new User("adminCoach1");
		User coach1 = new User("coach1");
		User u1 = new User("u1");
		User u2 = new User("u2");
		User u3 = new User("u3");
		User u4 = new User("u4");
		
		adminCoach1.addEdge(coach1);
		coach1.addEdge(u1);
		coach1.addEdge(u2);
		coach1.addEdge(u3);
		coach1.addEdge(u4);
		
		
		User adminCoach2 = new User("adminCoach2");
		User coach2 = new User("coach2");
		User coach3 = new User("coach3");
		User coach4 = new User("coach4");
		adminCoach2.addEdge(coach2);
		adminCoach2.addEdge(coach3);
		adminCoach2.addEdge(coach4);
		
		
		User u5 = new User("u5");
		User u6 = new User("u6");
		User u7 = new User("u7");
		User u8 = new User("u8");
		coach2.addEdge(u5);
		coach2.addEdge(u6);
		coach2.addEdge(u7);
		coach2.addEdge(u8);
		
		User u9 = new User("u9");
		User u10 = new User("u10");
		User u11 = new User("u11");
		User u12 = new User("u12");
		coach3.addEdge(u9);
		coach3.addEdge(u10);
		coach3.addEdge(u11);
		coach3.addEdge(u12);
		
		User u13 = new User("u13");
		User u14 = new User("u14");
		User u15 = new User("u15");
		User u16 = new User("u16");
		coach4.addEdge(u13);
		coach4.addEdge(u14);
		coach4.addEdge(u15);
		coach4.addEdge(u16);

		User coach5 = new User("coach5");
		User u17 = new User("u17");
		User u18 = new User("u18");
		User u19 = new User("u19");
		User u20 = new User("u20");
		coach5.addEdge(u17);
		coach5.addEdge(u18);
		coach5.addEdge(u19);
		coach5.addEdge(u20);
		
		User coach6 = new User("coach6");
		User u21 = new User("u21");
		User u22 = new User("u22");
		User u23 = new User("u23");
		User u24 = new User("u24");
		User u25 = new User("u25");
		User u26 = new User("u26");
		User u27 = new User("u27");
		User u28 = new User("u28");
		coach6.addEdge(u21);
		coach6.addEdge(u22);
		coach6.addEdge(u23);
		coach6.addEdge(u24);
		coach6.addEdge(u25);
		coach6.addEdge(u26);
		coach6.addEdge(u27);
		coach6.addEdge(u28);
		
			
		
		g.addUser(u1);
		g.addUser(u2);
		g.addUser(u3);
		g.addUser(u4);
		g.addUser(u5);
		g.addUser(u6);
		g.addUser(u7);
		g.addUser(u8);
		g.addUser(u9);
		g.addUser(u10);
		g.addUser(u12);
		g.addUser(u13);
		g.addUser(u14);
		g.addUser(u15);
		g.addUser(u16);
		g.addUser(u17);
		g.addUser(u18);
		g.addUser(u19);
		g.addUser(u20);
		g.addUser(u21);
		g.addUser(u22);
		g.addUser(u23);
		g.addUser(u24);
		g.addUser(u25);
		g.addUser(u26);
		g.addUser(u27);
		g.addUser(u28);
		g.addUser(adminCoach1);
		g.addUser(adminCoach2);
		g.addUser(coach1);
		g.addUser(coach2);
		g.addUser(coach3);
		g.addUser(coach4);
		g.addUser(coach5);
		g.addUser(coach6);
		
		g.limitedInfection(adminCoach1, "A", 20);
		
		ArrayList<User> nodes = adminCoach2.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"A");
		}
		
		nodes = coach2.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"A");
		}
		
		nodes = coach3.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"A");
		}
		
		nodes = coach4.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"A");
		}
		
		nodes = coach6.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"A");
		}
		
		nodes = coach1.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"X");
		}
		
		nodes = coach5.getEdges();
		for(User n: nodes){
			assertEquals(n.getVersion(),"X");
		}
		
		

	}
	
	/*
	 * Tests a graph with 4 components and returns the 2 best components to A/B test
	 */
	
	
	@Test
	public void testABComponent(){
		MyGraph g = new MyGraph();

		
		User adminCoach1 = new User("adminCoach1");
		User coach1 = new User("coach1");
		User u1 = new User("u1");
		User u2 = new User("u2");
		User u3 = new User("u3");
		User u4 = new User("u4");
		User u30 = new User("u30");
		User u31 = new User("u31");
		
		adminCoach1.addEdge(coach1);
		coach1.addEdge(u1);
		coach1.addEdge(u2);
		coach1.addEdge(u3);
		coach1.addEdge(u4);
		coach1.addEdge(u30);
		coach1.addEdge(u31);
		
		
		User adminCoach2 = new User("adminCoach2");
		User coach2 = new User("coach2");
		User coach3 = new User("coach3");
		User coach4 = new User("coach4");
		adminCoach2.addEdge(coach2);
		adminCoach2.addEdge(coach3);
		adminCoach2.addEdge(coach4);
		
		
		User u5 = new User("u5");
		User u6 = new User("u6");
		User u7 = new User("u7");
		User u8 = new User("u8");
		coach2.addEdge(u5);
		coach2.addEdge(u6);
		coach2.addEdge(u7);
		coach2.addEdge(u8);
		
		User u9 = new User("u9");
		User u10 = new User("u10");
		User u11 = new User("u11");
		User u12 = new User("u12");
		coach3.addEdge(u9);
		coach3.addEdge(u10);
		coach3.addEdge(u11);
		coach3.addEdge(u12);
		
		User u13 = new User("u13");
		User u14 = new User("u14");
		User u15 = new User("u15");
		User u16 = new User("u16");
		coach4.addEdge(u13);
		coach4.addEdge(u14);
		coach4.addEdge(u15);
		coach4.addEdge(u16);

		User coach5 = new User("coach5");
		User u17 = new User("u17");
		User u18 = new User("u18");
		User u19 = new User("u19");
		User u20 = new User("u20");
		coach5.addEdge(u17);
		coach5.addEdge(u18);
		coach5.addEdge(u19);
		coach5.addEdge(u20);
		
		User coach6 = new User("coach6");
		User u21 = new User("u21");
		User u22 = new User("u22");
		User u23 = new User("u23");
		User u24 = new User("u24");
		User u25 = new User("u25");
		User u26 = new User("u26");
		User u27 = new User("u27");
		User u28 = new User("u28");
		coach6.addEdge(u21);
		coach6.addEdge(u22);
		coach6.addEdge(u23);
		coach6.addEdge(u24);
		coach6.addEdge(u25);
		coach6.addEdge(u26);
		coach6.addEdge(u27);
		coach6.addEdge(u28);
		
			
		
		g.addUser(u1);
		g.addUser(u2);
		g.addUser(u3);
		g.addUser(u4);
		g.addUser(u5);
		g.addUser(u6);
		g.addUser(u7);
		g.addUser(u8);
		g.addUser(u9);
		g.addUser(u10);
		g.addUser(u12);
		g.addUser(u13);
		g.addUser(u14);
		g.addUser(u15);
		g.addUser(u16);
		g.addUser(u17);
		g.addUser(u18);
		g.addUser(u19);
		g.addUser(u20);
		g.addUser(u21);
		g.addUser(u22);
		g.addUser(u23);
		g.addUser(u24);
		g.addUser(u25);
		g.addUser(u26);
		g.addUser(u27);
		g.addUser(u28);
		g.addUser(adminCoach1);
		g.addUser(adminCoach2);
		g.addUser(coach1);
		g.addUser(coach2);
		g.addUser(coach3);
		g.addUser(coach4);
		g.addUser(coach5);
		g.addUser(coach6);
		
		
		ArrayList<User> answer = g.abComponents(adminCoach1);
	
		assertEquals(answer.get(0).getName(),"coach5");
		assertEquals(answer.get(1).getName(),"coach1");
		
		
	}
	
	@Test
	public void testExactInfection(){
		MyGraph g = new MyGraph();

		
		User adminCoach1 = new User("adminCoach1");
		User coach1 = new User("coach1");
		User u1 = new User("u1");
		User u2 = new User("u2");
		User u3 = new User("u3");
		User u4 = new User("u4");
		
		adminCoach1.addEdge(coach1);
		coach1.addEdge(u1);
		coach1.addEdge(u2);
		coach1.addEdge(u3);
		coach1.addEdge(u4);
		
		
		User adminCoach2 = new User("adminCoach2");
		User coach2 = new User("coach2");
		User coach3 = new User("coach3");
		User coach4 = new User("coach4");
		adminCoach2.addEdge(coach2);
		adminCoach2.addEdge(coach3);
		adminCoach2.addEdge(coach4);
		
		
		User u5 = new User("u5");
		User u6 = new User("u6");
		User u7 = new User("u7");
		User u8 = new User("u8");
		coach2.addEdge(u5);
		coach2.addEdge(u6);
		coach2.addEdge(u7);
		coach2.addEdge(u8);
		
		User u9 = new User("u9");
		User u10 = new User("u10");
		User u11 = new User("u11");
		User u12 = new User("u12");
		coach3.addEdge(u9);
		coach3.addEdge(u10);
		coach3.addEdge(u11);
		coach3.addEdge(u12);
		
		User u13 = new User("u13");
		User u14 = new User("u14");
		User u15 = new User("u15");
		User u16 = new User("u16");
		coach4.addEdge(u13);
		coach4.addEdge(u14);
		coach4.addEdge(u15);
		coach4.addEdge(u16);

		User coach5 = new User("coach5");
		User u17 = new User("u17");
		User u18 = new User("u18");
		User u19 = new User("u19");
		User u20 = new User("u20");
		coach5.addEdge(u17);
		coach5.addEdge(u18);
		coach5.addEdge(u19);
		coach5.addEdge(u20);
		
		User coach6 = new User("coach6");
		User u21 = new User("u21");
		User u22 = new User("u22");
		User u23 = new User("u23");
		User u24 = new User("u24");
		User u25 = new User("u25");
		User u26 = new User("u26");
		User u27 = new User("u27");
		User u28 = new User("u28");
		coach6.addEdge(u21);
		coach6.addEdge(u22);
		coach6.addEdge(u23);
		coach6.addEdge(u24);
		coach6.addEdge(u25);
		coach6.addEdge(u26);
		coach6.addEdge(u27);
		coach6.addEdge(u28);
		
			
		
		g.addUser(u1);
		g.addUser(u2);
		g.addUser(u3);
		g.addUser(u4);
		g.addUser(u5);
		g.addUser(u6);
		g.addUser(u7);
		g.addUser(u8);
		g.addUser(u9);
		g.addUser(u10);
		g.addUser(u11);
		g.addUser(u12);
		g.addUser(u13);
		g.addUser(u14);
		g.addUser(u15);
		g.addUser(u16);
		g.addUser(u17);
		g.addUser(u18);
		g.addUser(u19);
		g.addUser(u20);
		g.addUser(u21);
		g.addUser(u22);
		g.addUser(u23);
		g.addUser(u24);
		g.addUser(u25);
		g.addUser(u26);
		g.addUser(u27);
		g.addUser(u28);
		g.addUser(adminCoach1);
		g.addUser(adminCoach2);
		g.addUser(coach1);
		g.addUser(coach2);
		g.addUser(coach3);
		g.addUser(coach4);
		g.addUser(coach5);
		g.addUser(coach6);
		
		boolean answer = g.exactInfection(u1, "A", 11);
		assertEquals(answer,true);
		
		answer = g.exactInfection(u1, "A", 2);
		assertEquals(answer,false);
		
		answer = g.exactInfection(u1, "A", 9);
		assertEquals(answer,true);
		
		answer = g.exactInfection(u1, "A", 36);
		assertEquals(answer,true);
		
	}
	
	@Test
	public void getAverageRatings(){
		
		MyGraph g = new MyGraph();

		
		User adminCoach1 = new User("adminCoach1");
		User coach1 = new User("coach1");
		User u1 = new User("u1");
		User u2 = new User("u2");
		User u3 = new User("u3");
		User u4 = new User("u4");
		
		adminCoach1.addEdge(coach1);
		coach1.addEdge(u1);
		coach1.addEdge(u2);
		coach1.addEdge(u3);
		coach1.addEdge(u4);
		
		g.addUser(u1);
		g.addUser(u2);
		g.addUser(u3);
		g.addUser(u4);
		g.addUser(coach1);
		g.addUser(adminCoach1);
		
		ArrayList<User> coaches = new ArrayList<User>();
		
		double averageRating = g.getRatings(coaches);
		int high = 10;
		int low = 1;
		assertTrue("Error, random is too high", high >= averageRating);
		assertTrue("Error, random is too low",  low  <= averageRating);
	}


}

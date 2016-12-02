package InfectionTests;

import static org.junit.Assert.*;

import org.junit.Test;

import Infection.*;

public class UserTests {
	
	
	/*
	 * Tests contructor of a User
	 */
	@Test
	public void testUser(){
		User a = new User("a");
		assertEquals(a.getVersion(),"X");
		
		a.changeVersion("A");
		assertEquals(a.getVersion(),"A");
	}
	
	@Test
	public void testAddEdge() {
		User a = new User("a");
		assertEquals(a.getEdges().size(),0);
		
		User b = new User("b");
		a.addEdge(b);
		assertEquals(a.getEdges().size(),1);

	}
	
	@Test
	public void testRandomFeedback(){
		User a = new User("a");
		int feedback = a.provideFeedback();
		int high = 10;
		int low = 1;
		assertTrue("Error, random is too high", high >= feedback);
		assertTrue("Error, random is too low",  low  <= feedback);
	}

	
}

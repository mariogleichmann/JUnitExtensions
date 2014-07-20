package gwt.demo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junitext.gwt.GivenWhenThenSequence.GIVEN;
import static org.junitext.gwt.GivenWhenThenSequence.THEN;
import static org.junitext.gwt.GivenWhenThenSequence.WHEN;
import static org.junitext.gwt.GivenWhenThenSequence.AND;
import static org.junitext.gwt.GivenWhenThenSequence.ALSO;

import java.util.Stack;

import org.junit.Rule;
import org.junit.Test;
import org.junitext.gwt.GivenWhenThenConsoleReporter;


public class StackTest {

	@Rule
	public GivenWhenThenConsoleReporter reporter = new GivenWhenThenConsoleReporter();
	
	
	@Test
	public void pushOnEmptyStack(){
		
		GIVEN( "an empty stack" );
		
		  Stack<Integer> stack = new Stack<Integer>();
		
		WHEN( "pushing an element onto the stack" );
		
		  stack.push( 42 );
		  
		THEN( "the stack shouldn't be empty anymore" );
		
			assertFalse( stack.isEmpty() );
	}
	
	
	@Test
	public void pushElementOStack(){
		
		GIVEN( "a stack" );
		
		  Stack<Integer> stack = new Stack<Integer>();
		  
		AND( "an element" );
		
		  Integer element = 42;
		
		WHEN( "pushing the given element onto the stack" );
		
		  stack.push( element );
		  
		THEN( "that element should be on top of the stack" );
		
			assertEquals( element, stack.peek() );
			
		ALSO( "that element should be the last pushed element" );
		
		  assertEquals( element, stack.lastElement() );
	}
}

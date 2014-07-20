package gwt.demo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junitext.gwt.GivenWhenThenSequence.GIVEN;
import static org.junitext.gwt.GivenWhenThenSequence.THEN;
import static org.junitext.gwt.GivenWhenThenSequence.WHEN;
import static org.junitext.gwt.GivenWhenThenSequence.AND;
import static org.junitext.gwt.GivenWhenThenSequence.ALSO;



import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junitext.gwt.GivenWhenThenConsoleReporter;


public class SetTest {

	@Rule
	public GivenWhenThenConsoleReporter reporter = new GivenWhenThenConsoleReporter();
	
	
	@Test
	public void addOnEmptySet(){
		
		GIVEN( "an empty set" );
		
		  Set<String> set = new HashSet<String>();
		
		WHEN( "adding an element to the set" );
		
		  set.add( "a" );
		  
		THEN( "the set should'nt be empty anymore" );
		
		  assertFalse( set.isEmpty() );
		
	}
}

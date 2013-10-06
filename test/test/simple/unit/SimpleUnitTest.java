package test.simple.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junitext.refsuits.categories.UnitTest;

@UnitTest
public class SimpleUnitTest {

	@Test
	public void test1(){
		
		assertEquals( 2 + 2, 4 );
	}
}

package test.complex.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junitext.refsuits.categories.UnitTest;

@UnitTest
public class ComplexUnitTest {

	@Test
	public void test1(){
		
		assertEquals( 2 + 2, 4 );
	}
}

package test.complex.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junitext.refsuits.categories.IntegrationTest;

@IntegrationTest
public abstract class AbstractWithTestMethod {

	@Test
	public void test1(){
		
		assertEquals( 2 + 2, 4 );
	}
}

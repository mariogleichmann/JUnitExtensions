package test.simple.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junitext.refsuits.categories.IntegrationTest;


//@Category( IntegrationTest.class )
@IntegrationTest
public class SimpleIntegrationTest {

	@Test
	public void test1(){
		
		assertEquals( 1 + 1, 2 );
	}
}

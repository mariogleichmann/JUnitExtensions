package suits;

import org.junit.runner.RunWith;
import org.junitext.refsuits.PackageRoot;
import org.junitext.refsuits.ReflectiveClasspathScanner;
import org.junitext.refsuits.categories.Categories;
import org.junitext.refsuits.categories.IntegrationTest;
import org.junitext.refsuits.categories.UnitTest;

@RunWith( ReflectiveClasspathScanner.class )
@Categories( { IntegrationTest.class, UnitTest.class } )
@PackageRoot( "test.complex" )
public class IntegrationTestSuite {}

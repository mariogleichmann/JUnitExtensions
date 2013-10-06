package suits;

import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junitext.dynsuits.Categories.UnitTest;
import org.junitext.dynsuits.ClasspathTestsProvider;

@RunWith( ClasspathTestsProvider.class )
@IncludeCategory( UnitTest.class )
public class AllUnitTests {}

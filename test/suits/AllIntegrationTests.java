package suits;

import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;

import org.junitext.dynsuits.Categories.IntegrationTest;
import org.junitext.dynsuits.ClasspathTestsProvider;

@RunWith( ClasspathTestsProvider.class )
@IncludeCategory( IntegrationTest.class )
public final class AllIntegrationTests {}

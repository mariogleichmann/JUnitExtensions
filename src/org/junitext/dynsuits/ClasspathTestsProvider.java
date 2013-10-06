package org.junitext.dynsuits;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.junit.experimental.categories.Categories.CategoryFilter;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.experimental.categories.Category;
import org.junit.runner.Description;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class ClasspathTestsProvider extends Suite
{
	private RunListenerExtension runListenerExtension = null;
	
	
	public ClasspathTestsProvider(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		
		super( builder, ClassFinder.getClasses( "test" ) );
		
		if( klass.isAnnotationPresent( RunListenerExtension.class ) ){
			this.runListenerExtension = klass.getAnnotation( RunListenerExtension.class );
		}
		
		try {
			filter( new CategoryFilter( getIncludedCategory( klass ), getExcludedCategory( klass ) ) );
		}
		catch ( NoTestsRemainException e ) {
			throw new InitializationError( e );
		}
		
		assertNoCategorizedDescendentsOfUncategorizeableParents( getDescription() );
	}
	
	
	@Override
	public void run( final RunNotifier notifier ){
		
		if( runListenerExtension != null ){
			
			notifier.addListener( new RunListenerExtensionDelegate( runListenerExtension ) );
		}
		
		super.run( notifier );
	}
	

	private Class<?> getIncludedCategory( Class<?> klass ) {
		IncludeCategory annotation = klass.getAnnotation( IncludeCategory.class );
		return annotation == null ? null : annotation.value();
	}

	private Class<?> getExcludedCategory( Class<?> klass ) {
		ExcludeCategory annotation = klass.getAnnotation( ExcludeCategory.class );
		return annotation == null ? null : annotation.value();
	}

	private void assertNoCategorizedDescendentsOfUncategorizeableParents( Description description )
			throws InitializationError {
		if ( !canHaveCategorizedChildren( description ) )
			assertNoDescendantsHaveCategoryAnnotations( description );
		for ( Description each : description.getChildren() )
			assertNoCategorizedDescendentsOfUncategorizeableParents( each );
	}

	private void assertNoDescendantsHaveCategoryAnnotations( Description description ) throws InitializationError {
		for ( Description each : description.getChildren() ) {
			if ( each.getAnnotation( Category.class ) != null )
				throw new InitializationError(
						"Category annotations on Parameterized classes are not supported on individual methods." );
			assertNoDescendantsHaveCategoryAnnotations( each );
		}
	}

	// If children have names like [0], our current magical category code can't determine their
	// parentage.
	private static boolean canHaveCategorizedChildren( Description description ) {
		for ( Description each : description.getChildren() )
			if ( each.getTestClass() == null )
				return false;
		return true;
	}

	// --

	private static final class ClassFinder
	{
		private static Class<?>[] getClasses( String packageName ) {
			try {
				String path = packageName.replace( '.', '/' );
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				Enumeration<URL> resources = classLoader.getResources( path );
				List<File> dirs = new ArrayList<File>();
				while ( resources.hasMoreElements() ) {
					dirs.add( new File( resources.nextElement().getFile() ) );
				}
				ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
				for ( File directory : dirs ) {
					classes.addAll( findClasses( directory, packageName ) );
				}
				return classes.toArray( new Class[classes.size()] );
			}
			catch ( Exception e ) {
				throw new RuntimeException( e );
			}
		}

		/**
		 * Recursive method used to find all classes in a given directory and subdirs.
		 * 
		 * @param directory The base directory
		 * @param packageName The package name for classes found inside the base directory
		 * @return The classes
		 * @throws ClassNotFoundException
		 */
		private static List<Class<?>> findClasses( File directory, String packageName ) throws ClassNotFoundException {

			List<Class<?>> classes = new ArrayList<Class<?>>();
			if ( !directory.exists() ) {
				return classes;
			}

			File[] files = directory.listFiles();
			for ( File file : files ) {
				if ( file.isDirectory() ) {
					classes.addAll( findClasses( file, packageName + "." + file.getName() ) );
				}
				else if ( file.getName().endsWith( "Test.class" ) ) {
					classes.add( Class.forName( packageName + '.'
							+ file.getName().substring( 0, file.getName().length() - 6 ) ) );
				}
			}
			return classes;
		}
	}
}



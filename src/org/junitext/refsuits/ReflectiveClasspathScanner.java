package org.junitext.refsuits;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junitext.refsuits.categories.Categories;
import org.reflections.Reflections;

public class ReflectiveClasspathScanner extends Suite {

	public ReflectiveClasspathScanner(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		
		super( builder, findClasses( klass ) );
	}

	private static Class<?>[] findClasses(Class<?> klass) {		
		
		Categories categories = klass.getAnnotation( Categories.class );
		
		Set<Class<?>> foundTestClasses = new HashSet<Class<?>>();
		
		for( Class<? extends Annotation> category : categories.value() ){
			
			foundTestClasses.addAll( testClassesAnnotatedWith( category, withinPackageDefinedAt( klass ) ) );
		}
		
		return foundTestClasses.toArray( new Class<?>[foundTestClasses.size()] );
	}

	private static String withinPackageDefinedAt( Class<?> klass ){
		
		return klass.isAnnotationPresent( PackageRoot.class ) ? klass.getAnnotation( PackageRoot.class ).value() : "";
	}
	
	private static Collection<? extends Class<?>> testClassesAnnotatedWith( Class<? extends Annotation> category, String packageRoot ) {
		
		Reflections reflections = new Reflections( packageRoot );
		
		return reflections.getTypesAnnotatedWith( category );
	}
}

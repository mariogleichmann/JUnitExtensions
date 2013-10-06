package org.junitext.dynsuits;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class RunListenerExtensionDelegate extends RunListener{
	
	private List<RunListener> runListeners = new ArrayList<RunListener>();
	
	public RunListenerExtensionDelegate(){
	}
	
	public RunListenerExtensionDelegate( RunListenerExtension annotation ){
		
		registerListenersDeclaredAt( annotation );
	}
	
	
	@Override
	public void testStarted( Description description ) throws Exception {
		
		Class<?> classUnderTest = description.getTestClass();
		
		if( classUnderTest.isAnnotationPresent( RunListenerExtension.class ) ){
			
			RunListenerExtension annotation = classUnderTest.getAnnotation( RunListenerExtension.class );
			
			registerListenersDeclaredAt( annotation );
		}
		
		fireTestStarted( description );
	}
	
	private void registerListenersDeclaredAt( RunListenerExtension annotation ){

		for( Class<?> listenerClass : annotation.value() ){
			
			runListeners.add( newInstanceFor( listenerClass ) );
		}
	}
	
	@Override
	public void testFailure(Failure failure) throws Exception {
		fireTestFailure( failure );
	}

	@Override
	public void testFinished(Description description) throws Exception {
		fireTestFinished( description );
	}
	
	@Override
	public void testRunFinished(Result result) throws Exception {
		fireTestRunFinished( result );
	}

	@Override
	public void testAssumptionFailure(Failure failure) {
		fireTestAssumptionFailure( failure );
	}
	
	@Override
	public void testIgnored(Description description) throws Exception {
		fireTestIgnored( description );
	}
	
	
	private void fireTestIgnored( Description description ) throws Exception {
		for( RunListener listener : runListeners ) listener.testIgnored( description );
	}

	private void fireTestAssumptionFailure( Failure failure ) {
		for( RunListener listener : runListeners ) listener.testAssumptionFailure( failure );        
    }

	private void fireTestRunFinished( Result result ) throws Exception {
		for( RunListener listener : runListeners ) listener.testRunFinished( result );
    }

	private void fireTestFinished( Description description ) throws Exception {
		for( RunListener listener : runListeners ) listener.testFinished( description );
    }

	private void fireTestFailure( Failure failure ) throws Exception {
		for( RunListener listener : runListeners ) listener.testFailure( failure );
    }

	private void fireTestStarted( Description description ) throws Exception{
		for( RunListener listener : runListeners ) listener.testStarted( description );
	}
	
	
	private RunListener newInstanceFor( Class<?> listenerClass ) {
		try {
            return (RunListener) listenerClass.newInstance();
        }
		catch ( Exception e ) {
			throw new RuntimeException( e );
        }
    }
}


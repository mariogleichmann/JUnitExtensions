package org.junitext.gwt;

import java.util.List;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junitext.gwt.GivenWhenThenSequence.Step;

public class GivenWhenThenConsoleReporter extends TestWatcher {

	@Override
	protected void failed(Throwable e, Description description) {
		System.out.println( asReport( GivenWhenThenSequence.sequence() ) );
		System.out.println( " => Test fehlgeschlagen" );
	}

	@Override
	protected void succeeded(Description description) {
		System.out.println( asReport( GivenWhenThenSequence.sequence() ) );
		System.out.println( " => Test erfolgreich" );
	}

	private String asReport(List<Step> sequence) {

		StringBuilder sb = new StringBuilder();
		for ( Step step : sequence ) {
			sb.append( "\n" );
			sb.append( step.getPhase() );
			sb.append( " - " );
			sb.append( step.getMessage() );
		}

		return sb.toString();
	}
}

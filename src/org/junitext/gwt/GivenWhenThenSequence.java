package org.junitext.gwt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public final class GivenWhenThenSequence {

	private static ThreadLocal<GivenWhenThenSequence> tl = new ThreadLocal<GivenWhenThenSequence>();

	private Phase currentPhase;
	private List<Step> sequence;

	private GivenWhenThenSequence(Step initialStep) {
		this.sequence = new ArrayList<Step>();
		this.sequence.add( initialStep );
		this.currentPhase = Phase.GIVEN;
	}

	public static void reset() {
		tl.set( null );
	}

	public static void GIVEN(String message) {
		tl.set( new GivenWhenThenSequence( new Step( Phase.GIVEN, message ) ) );
	}

	public static void AND(String message) {
		tl.get().addStep( new Step( Phase.AND, message ) );
	}

	public static void WHEN(String message) {
		tl.get().addStep( new Step( Phase.WHEN, message ) );
	}

	public static void THEN(String message) {
		tl.get().addStep( new Step( Phase.THEN, message ) );
	}

	public static void ALSO(String message) {
		tl.get().addStep( new Step( Phase.ALSO, message ) );
	}

	private void addStep(Step step) {
		this.currentPhase = currentPhase.toNextPhaseOrFail( step.getPhase() );
		this.sequence.add( step );
	}
	static List<Step> sequence() {
		return Collections.unmodifiableList( tl.get().sequence );
	}

	// --

	public static enum Phase {

		GIVEN {
			public Phase toNextPhaseOrFail(Phase nextPhase) {
				return toNextPhaseOrFail( nextPhase, AND, WHEN );
			}
		},
		AND {
			public Phase toNextPhaseOrFail(Phase nextPhase) {
				return toNextPhaseOrFail( nextPhase, AND, WHEN );
			}
		},
		WHEN {
			public Phase toNextPhaseOrFail(Phase nextPhase) {
				return toNextPhaseOrFail( nextPhase, THEN );
			}
		},
		THEN {
			public Phase toNextPhaseOrFail(Phase nextPhase) {
				return toNextPhaseOrFail( nextPhase, ALSO );
			}
		},
		ALSO {
			public Phase toNextPhaseOrFail(Phase nextPhase) {
				return toNextPhaseOrFail( nextPhase, ALSO );
			}
		};

		public abstract Phase toNextPhaseOrFail(Phase nextPhase);

		private static Phase toNextPhaseOrFail(Phase nextPhase, Phase permittedNextPhase, Phase... permittedNextPhases) {

			if ( EnumSet.of( permittedNextPhase, permittedNextPhases ).contains( nextPhase ) )
				return nextPhase;

			throw new IllegalStateException( "new phase=" + nextPhase + " is not permitted" );
		}
	}

	public static final class Step {

		private Phase phase;
		private String message;

		private Step(Phase phase, String message) {
			this.phase = phase;
			this.message = message;
		}

		public Phase getPhase() {
			return phase;
		}

		public String getMessage() {
			return message;
		}
	}
}

package org.junitext.refsuits;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention( RUNTIME )
public @interface PackageRoot {

	String value();
}

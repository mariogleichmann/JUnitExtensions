package org.junitext.refsuits.categories;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;

@Retention( RUNTIME )
public @interface Categories {

	Class<? extends Annotation>[] value();
}

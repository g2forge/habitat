package com.g2forge.habitat.metadata.v2.examples;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retained("Meta")
@Retention(RetentionPolicy.RUNTIME)
public @interface Retained {
	public String value();
}
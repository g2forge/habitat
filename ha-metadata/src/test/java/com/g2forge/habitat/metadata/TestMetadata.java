package com.g2forge.habitat.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.trace.HTrace;

public class TestMetadata {
	@Retention(RetentionPolicy.RUNTIME)
	public @interface MyAnnotation {
		public String value();
	}

	@MyAnnotation("expected")
	@Test
	public void test() {
		HAssert.assertEquals("expected", HMetadata.getStandard().of(HTrace.getCaller()).get(MyAnnotation.class).value());
	}
}

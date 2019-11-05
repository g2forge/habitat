package com.g2forge.habitat.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public class TestValueMetadata {
	@A("A")
	@Retention(RetentionPolicy.RUNTIME)
	public @interface A {
		public String value();
	}

	@A("B")
	public static class B implements C {}

	@A("C")
	public interface C {}

	public static class D implements C {}

	public interface E {}

	@A("F")
	public static class F implements E {}

	@Test
	public void element() {
		final ISubject subject = Metadata.getStandard().of(C.class, new D());
		final A a = subject.get(A.class);
		HAssert.assertEquals("C", a.value());
	}

	@Test
	public void meta() {
		final IMetadata metadata = Metadata.getStandard();
		final A aOnB = metadata.of(B.class).get(A.class);
		HAssert.assertEquals("B", aOnB.value());
		final A aOnA = metadata.of(aOnB).get(A.class);
		HAssert.assertEquals("A", aOnA.value());
	}

	@Test
	public void override() {
		final ISubject subject = Metadata.getStandard().of(C.class, new B());
		final A a = subject.get(A.class);
		HAssert.assertEquals("B", a.value());
	}

	@Test
	public void valuetype() {
		final ISubject subject = Metadata.getStandard().of(E.class, new F());
		final A a = subject.get(A.class);
		HAssert.assertEquals("F", a.value());
	}
}

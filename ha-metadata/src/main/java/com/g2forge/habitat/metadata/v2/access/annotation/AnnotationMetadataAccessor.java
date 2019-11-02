package com.g2forge.habitat.metadata.v2.access.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Method;

import com.g2forge.habitat.metadata.v2.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.v2.type.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.v2.value.subject.ElementSubject;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

public class AnnotationMetadataAccessor implements IMetadataAccessor {
	protected static Class<? extends Annotation> getRepeatable(Class<? extends Annotation> container) {
		final Method[] methods = container.getDeclaredMethods();
		if ((methods.length == 1) && (methods[0].getName().equals("value"))) {
			final Class<?> returnType = methods[0].getReturnType();
			if (returnType.isArray()) {
				final Class<?> componentType = returnType.getComponentType();
				if (componentType.isAnnotation()) {
					final Repeatable repeatable = componentType.getAnnotation(Repeatable.class);
					if (repeatable.value().equals(container)) {
						@SuppressWarnings({ "rawtypes", "unchecked" })
						final Class<? extends Annotation> retVal = (Class) componentType;
						return retVal;
					}
				}
			}
		}
		return null;
	}

	@Override
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
		if (!(subject instanceof ElementSubject)) throw new IllegalArgumentException(String.format("%1$s is not an element subject!", subject));
		AnnotationMetadataRegistry.check(predicateType);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final IPredicate<T> retVal = new AnnotationPredicate((ElementSubject) subject, (AnnotationPredicateType) predicateType);
		return retVal;
	}
}
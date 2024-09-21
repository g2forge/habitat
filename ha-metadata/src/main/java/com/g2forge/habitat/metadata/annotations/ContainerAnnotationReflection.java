package com.g2forge.habitat.metadata.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.g2forge.alexandria.collection.collector.ICollectionBuilder;
import com.g2forge.alexandria.collection.strategy.ICollectionStrategy;
import com.g2forge.alexandria.java.core.helpers.HCollection;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class ContainerAnnotationReflection<T extends Annotation, U extends Annotation> {
	public static <T extends Annotation, U extends Annotation> ContainerAnnotationReflection<T, U> createIfContainer(final Class<T> container) {
		try {
			return new ContainerAnnotationReflection<>(container);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Get the repeatable annotation that <code>container</code> contains, or return <code>null</code> if <code>container</code> isn't a repeatable annotation
	 * container.
	 * 
	 * @param container The type of an annotation which might be a repeatable container.
	 * @return The type of the repeatable annotation, or <code>null</code> if <code>container</code> isn't actually a container.
	 */
	public static Class<? extends Annotation> getRepeatable(Class<? extends Annotation> container) {
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

	protected final Class<T> container;

	protected final Class<U> repeatable;

	@Getter(AccessLevel.PROTECTED)
	protected final Method method;

	public ContainerAnnotationReflection(final Class<T> container) {
		this.container = container;

		final Method[] methods = container.getDeclaredMethods();
		if ((methods.length != 1) || !methods[0].getName().equals("value")) throw new IllegalArgumentException(String.format("%1$s is not a repeatable annotation container!", container));
		final Class<?> returnType = methods[0].getReturnType();
		if (!returnType.isArray()) throw new IllegalArgumentException(String.format("%1$s is not a repeatable annotation container!", container));
		final Class<?> componentType = returnType.getComponentType();
		if (!componentType.isAnnotation()) throw new IllegalArgumentException(String.format("%1$s is not a repeatable annotation container!", container));
		final Repeatable repeatable = componentType.getAnnotation(Repeatable.class);
		if ((repeatable == null) || !repeatable.value().equals(container)) throw new IllegalArgumentException(String.format("%1$s is not a repeatable annotation container!", container));
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final Class<U> repeatableClass = (Class) componentType;
		this.repeatable = repeatableClass;
		this.method = methods[0];
	}

	public ICollectionStrategy<T, U> getCollectionStrategy() {
		return new ICollectionStrategy<T, U>() {
			@Override
			public ICollectionBuilder<T, U> builder() {
				return new ICollectionBuilder<T, U>() {
					protected final List<U> list = new ArrayList<>();

					@Override
					public ICollectionBuilder<T, U> add(U value) {
						list.add(value);
						return this;
					}

					@Override
					public T get() {
						@SuppressWarnings("unchecked")
						final U[] array = (U[]) Array.newInstance(getRepeatable(), list.size());
						list.toArray(array);

						@SuppressWarnings({ "rawtypes", "unchecked" })
						final T retValDynamic = (T) new DynamicAnnotationBuilder<Annotation>((Class) getContainer()).add("value", array).build();
						return retValDynamic;
					}
				};
			}

			@Override
			public Iterable<U> iterable(T collection) {
				try {
					@SuppressWarnings("unchecked")
					final U[] array = (U[]) getMethod().invoke(collection);
					return HCollection.asList(array);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException("Failed to get repeatable values from container!", e);
				}
			}
		};
	}
}

package com.g2forge.habitat.metadata.value.implementations;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.java.reflect.annotations.ElementJavaAnnotations;
import com.g2forge.alexandria.java.reflect.annotations.IJavaAnnotations;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.IElementSubject;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class ElementSubject implements IElementSubject {
	protected static List<IElementSubject> getParents(IElementSubject _this, AnnotatedElement element) {
		if (element instanceof Class) {
			@SuppressWarnings("rawtypes")
			final Class<?> cast = (Class) element;

			final List<IElementSubject> parents = new ArrayList<>();
			if (cast.getSuperclass() != null) parents.add(new ElementSubject(_this.getContext(), cast.getSuperclass()));
			for (Class<?> parent : cast.getInterfaces()) {
				parents.add(new ElementSubject(_this.getContext(), parent));
			}
			return parents;
		}
		return HCollection.emptyList();
	}

	@ToString.Exclude
	protected final IMetadataValueContext context;

	protected final AnnotatedElement element;

	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final ISubjectType type = getContext().getTypeContext().subject(getElement().getClass(), null);

	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final IJavaAnnotations annotations = new ElementJavaAnnotations(getElement());

	@Override
	public List<IElementSubject> getParents() {
		return getParents(this, getElement());
	}
}

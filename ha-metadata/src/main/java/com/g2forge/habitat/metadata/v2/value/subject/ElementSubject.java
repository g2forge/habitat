package com.g2forge.habitat.metadata.v2.value.subject;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ElementSubjectType;
import com.g2forge.habitat.metadata.v2.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.v2.value.predicate.IPredicate;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ElementSubject implements ISubject {
	protected final IMetadataValueContext context;

	protected final AnnotatedElement element;

	@Getter(lazy = true)
	private final ElementSubjectType type = ElementSubjectType.valueOf(getElement().getClass());

	@Override
	public <T> IPredicate<T> bind(IPredicateType<T> predicateType) {
		return getContext().find(getType(), predicateType).bind(this, predicateType);
	}
}

package com.g2forge.habitat.metadata.access.computed.mixin;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.type.IMetadataPredicateTypeFactory;
import com.g2forge.habitat.metadata.type.IMetadataSubjectTypeFactory;
import com.g2forge.habitat.metadata.type.implementations.MetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.implementations.AMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class TestContext implements IMetadataPredicateTypeFactory<IPredicateType<?>>, IMetadataSubjectFactory<ISubject>, IMetadataSubjectTypeFactory<ISubjectType> {
	protected static class MetadataSubjectFactory extends AMetadataSubjectFactory {
		@Override
		protected IMetadataValueContext getValueContext() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	protected final IMetadataRegistry.IFindContext context;

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final IMetadataSubjectFactory<ISubject> metadataSubjectFactory = new MetadataSubjectFactory();

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		return getMetadataSubjectFactory().of(element, value);
	}

	@Override
	public <T> IPredicateType<?> predicate(Class<T> type) {
		return new MetadataTypeContext(getMetadataSubjectFactory()).predicate(type);
	}

	@Override
	public ISubjectType subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType) {
		return new MetadataTypeContext(getMetadataSubjectFactory()).subject(elementType, valueType);
	}
}

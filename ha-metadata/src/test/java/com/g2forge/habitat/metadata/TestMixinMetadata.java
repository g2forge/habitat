package com.g2forge.habitat.metadata;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.access.computed.MixinMetadataRegistry;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.ConstantPredicate;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IElementSubject;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class TestMixinMetadata {
	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	public static class Element {
		protected final Class<?> type;
	}

	public static class ElementMetadataAccessor implements ITypedMetadataAccessor<Element, IElementSubject, IPredicateType<Element>> {
		@Override
		public IPredicate<Element> bindTyped(IElementSubject subject, IPredicateType<Element> predicateType) {
			return new ConstantPredicate<>(subject, predicateType, new Element((Class<?>) subject.getElement()), true);
		}
	}

	@Test
	public void unmixed() {
		HAssert.assertException(NoAccessorFoundException.class, () -> Metadata.getStandard().of(getClass()).bind(Element.class));
	}

	@Test
	public void mixed() {
		final IPredicate<Element> predicate = new Metadata() {
			@Override
			protected IMetadataRegistry getMixinRegistry() {
				return new MixinMetadataRegistry().add(new ElementMetadataAccessor());
			}
		}.of(getClass()).bind(Element.class);
		HAssert.assertEquals(getClass(), predicate.get0().getType());
	}
}

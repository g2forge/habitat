package com.g2forge.habitat.metadata;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
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
	public void mixed() {
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.accessorTyped(new ElementMetadataAccessor()).build()).build();
		final IPredicate<Element> predicate = metadata.of(getClass()).bind(Element.class);
		HAssert.assertEquals(getClass(), predicate.get0().getType());
	}

	@Test
	public void unmixed() {
		HAssert.assertException(NoAccessorFoundException.class, () -> Metadata.getStandard().of(getClass()).bind(Element.class));
	}
}

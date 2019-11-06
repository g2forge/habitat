package com.g2forge.habitat.metadata;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.indirect.IndirectMetadata;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IElementSubject;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class TestIndirectMetadata {
	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	@IndirectMetadata(IndirectMetadataAccessor.class)
	public static class Indirect {
		protected final Class<?> type;
	}

	public static class IndirectMetadataAccessor implements IMetadataAccessor {
		@Override
		public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
			return new IPredicate<T>() {
				@SuppressWarnings("unchecked")
				@Override
				public T get0() {
					return (T) new Indirect((Class<?>) ((IElementSubject) subject).getElement());
				}

				@Override
				public ISubject getSubject() {
					return subject;
				}

				@Override
				public IPredicateType<T> getType() {
					return predicateType;
				}

				@Override
				public boolean isPresent() {
					return true;
				}
			};
		}
	}

	@Test
	public void test() {
		final IMetadata metadata = Metadata.getStandard();
		final IPredicateType<Indirect> predicateType = metadata.getContext().getTypeContext().predicate(Indirect.class);
		final IPredicate<Indirect> predicate = metadata.of(getClass()).bind(predicateType);
		HAssert.assertEquals(getClass(), predicate.get0().getType());
	}
}

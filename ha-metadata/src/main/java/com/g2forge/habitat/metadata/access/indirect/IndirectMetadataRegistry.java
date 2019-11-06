package com.g2forge.habitat.metadata.access.indirect;

import com.g2forge.alexandria.java.core.marker.ISingleton;
import com.g2forge.alexandria.java.function.type.ITypeFunction1;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.predicate.IIndirectPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class IndirectMetadataRegistry implements IMetadataRegistry {
	protected static class NewInstance implements ITypeFunction1<Object>, ISingleton {
		protected static final NewInstance instance = new NewInstance();

		public static NewInstance create() {
			return instance;
		}

		protected NewInstance() {}

		@Override
		public <_T> _T apply(Class<_T> type) {
			try {
				return type.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(String.format("Failed to instantiate %1$s using zero arguments new instance!", type), e);
			}
		}
	}

	protected final ITypeFunction1<? super IMetadataAccessor> instantiator;

	public IndirectMetadataRegistry() {
		this(NewInstance.create());
	}

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		if (context == null) throw new NullPointerException("The find context must be provided!");
		if (!(predicateType instanceof IIndirectPredicateType)) throw new NoAccessorFoundException(String.format("%1$s is not an indirect predicate type!", predicateType));

		final Class<? extends IMetadataAccessor> type = ((IIndirectPredicateType<?>) predicateType).getIndirectMetadata().value();
		return getInstantiator().apply(type);
	}
}

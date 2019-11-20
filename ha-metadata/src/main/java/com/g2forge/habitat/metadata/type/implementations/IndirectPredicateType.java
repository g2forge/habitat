package com.g2forge.habitat.metadata.type.implementations;

import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.habitat.metadata.access.indirect.IndirectMetadata;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IIndirectPredicateType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class IndirectPredicateType<T> implements IIndirectPredicateType<T> {
	@ToString.Exclude
	protected final IMetadataTypeContext context;

	protected final Class<T> type;

	protected final IndirectMetadata indirectMetadata;

	@Override
	public ITypeRef<T> getObjectType() {
		return ITypeRef.of(getType());
	}

	@Override
	public Class<?> getPredicateID() {
		return getType();
	}
}

package com.g2forge.habitat.metadata;

import com.g2forge.alexandria.java.core.marker.Helpers;
import com.g2forge.habitat.metadata.accessor.AnnotationMetadataAccessor;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
@Helpers
public class HMetadata {
	@Getter(lazy = true)
	private static final IMetadataContext standard = new MetadataContext(AnnotationMetadataAccessor.create());
}

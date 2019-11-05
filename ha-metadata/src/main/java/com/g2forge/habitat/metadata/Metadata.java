package com.g2forge.habitat.metadata;

import com.g2forge.habitat.metadata.access.annotation.AnnotationMetadataRegistry;
import com.g2forge.habitat.metadata.access.chained.ChainedMetadataRegistry;
import com.g2forge.habitat.metadata.access.merged.MergedMetadataRegistry;
import com.g2forge.habitat.metadata.type.implementations.MetadataTypeContext;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.implementations.MetadataValueContext;

import lombok.Getter;

public class Metadata implements IMetadata {
	@Getter(lazy = true)
	private static final IMetadata standard = new Metadata();

	@Getter(lazy = true)
	private final IMetadataValueContext context = new MetadataValueContext(new MetadataTypeContext(), new ChainedMetadataRegistry(new MergedMetadataRegistry(), new AnnotationMetadataRegistry()));
}
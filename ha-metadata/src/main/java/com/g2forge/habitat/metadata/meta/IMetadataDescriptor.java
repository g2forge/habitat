package com.g2forge.habitat.metadata.meta;

public interface IMetadataDescriptor<Described, Descriptor> {
	public boolean isMatch(Described described);
	
	public Descriptor reduce();
}

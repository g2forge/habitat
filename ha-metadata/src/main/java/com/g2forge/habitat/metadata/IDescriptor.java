package com.g2forge.habitat.metadata;

public interface IDescriptor<Described, Descriptor> {
	public boolean isMatch(Described described);
	
	public Descriptor reduce();
}

package com.g2forge.habitat.trace.executable;

import java.lang.reflect.Executable;
import java.lang.reflect.Member;

import com.g2forge.alexandria.java.adt.name.IStringNamed;

public interface IExecutable extends Member, IStringNamed {
	public Executable getExecutable();
}

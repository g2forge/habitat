package com.g2forge.habitat.trace;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.g2forge.alexandria.java.core.marker.Helpers;

import lombok.experimental.UtilityClass;

@UtilityClass
@Helpers
public class HTraceInternal {
	protected static final String INITIALIZER = "<init>";

	protected static String getDescriptor(final Class<?>[] parameterTypes, final Class<?> returnType) {
		final StringBuilder retVal = new StringBuilder();
		retVal.append('(');
		for (int i = 0; i < parameterTypes.length; i++) {
			retVal.append(getName(parameterTypes[i]));
		}
		retVal.append(')');
		retVal.append(getName(returnType));
		return retVal.toString();
	}

	protected static String getDescriptor(Constructor<?> constructor) {
		return getDescriptor(constructor.getParameterTypes(), Void.TYPE);
	}

	protected static String getDescriptor(Method method) {
		return getDescriptor(method.getParameterTypes(), method.getReturnType());
	}

	protected static Map<Integer, String> getLine2FieldMap(InputStream classData, String className) throws IOException {
		final Map<Integer, String> retVal = new HashMap<>();
		final ClassReader reader = new ClassReader(classData);
		reader.accept(new ClassVisitor(Opcodes.ASM7) {
			@Override
			public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
				if (!INITIALIZER.equals(name)) return null;
				return new MethodVisitor(Opcodes.ASM7) {
					protected int line = -1;

					@Override
					public void visitFieldInsn(final int opcode, final String owner, final String name, final String descriptor) {
						if (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
							if (className.equals(owner)) {
								retVal.put(line, name);
							}
						}
					}

					@Override
					public void visitLineNumber(int line, Label start) {
						this.line = line;
					}
				};
			}
		}, 0);
		return retVal;
	}

	/**
	 * Get a map from source line numbers to method descriptors for all methods with the specified name. In short this lets one retrieve the descriptor for a
	 * method and line number, which in turn can be used to get reflective access to that method.
	 * 
	 * @param classData An input stream for the class file.
	 * @param methodName The name of the methods to map.
	 * @return A map from line numbers to the method descriptor.
	 * @throws IOException There was a problem reading the class file.
	 */
	protected static Map<Integer, String> getLine2MethodMap(InputStream classData, String methodName) throws IOException {
		final Map<Integer, String> retVal = new HashMap<>();
		final ClassReader reader = new ClassReader(classData);
		reader.accept(new ClassVisitor(Opcodes.ASM7) {
			@Override
			public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
				if ((methodName != null) && !methodName.equals(name)) return null;
				return new MethodVisitor(Opcodes.ASM7) {
					@Override
					public void visitLineNumber(int line, Label start) {
						retVal.put(line, descriptor);
					}
				};
			}
		}, 0);
		return retVal;
	}

	protected static String getName(Class<?> type) {
		if (Void.TYPE.equals(type)) return "V";
		if (type.isPrimitive()) {
			if (Integer.TYPE.equals(type)) return "I";
			if (Long.TYPE.equals(type)) return "J";
			if (Boolean.TYPE.equals(type)) return "Z";
			if (Byte.TYPE.equals(type)) return "B";
			if (Short.TYPE.equals(type)) return "S";
			if (Character.TYPE.equals(type)) return "C";
			if (Double.TYPE.equals(type)) return "D";
			if (Float.TYPE.equals(type)) return "F";
			throw new IllegalArgumentException();
		}
		if (type.isArray()) return "[" + getName(type.getComponentType());
		return "L" + type.getName().replace('.', '/') + ";";
	}
}

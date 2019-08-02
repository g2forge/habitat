package com.g2forge.habitat.trace;

import java.io.IOException;
import java.io.InputStream;
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
public class HTrace {
	public static Method getCaller() {
		return new SmartStackTraceElement(new Throwable().getStackTrace()[1]).getMethod();
	}

	protected static String getDescriptor(Method method) {
		final StringBuilder retVal = new StringBuilder();
		retVal.append('(');
		final Class<?>[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			retVal.append(getName(parameterTypes[i]));
		}
		retVal.append(')');
		retVal.append(getName(method.getReturnType()));
		return retVal.toString();
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

	protected static Map<Integer, String> getLineMap(InputStream classData, String methodName) throws IOException {
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
}

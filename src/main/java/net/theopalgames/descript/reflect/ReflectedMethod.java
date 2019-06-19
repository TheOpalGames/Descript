package net.theopalgames.descript.reflect;

import java.lang.reflect.Method;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor(access=AccessLevel.PACKAGE)
public final class ReflectedMethod<T> {
	private final Method method;
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public T call(Object instance, Object... args) {
		return (T) method.invoke(instance, args);
	}
}

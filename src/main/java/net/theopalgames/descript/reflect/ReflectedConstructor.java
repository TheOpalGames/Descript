package net.theopalgames.descript.reflect;

import java.lang.reflect.Constructor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public final class ReflectedConstructor<T> {
	private final Constructor<T> constructor;
	
	@SneakyThrows
	public T newInstance(Object... args) {
		return constructor.newInstance(args);
	}
}

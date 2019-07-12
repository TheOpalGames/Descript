package net.theopalgames.descript.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReflectUtil {
	public <T> T readField(Class<?> owner, String name) {
		return readField(owner, name, null);
	}
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public <T> T readField(Class<?> owner, String name, @Nullable Object instance) {
		Field field = getDeclaredField(owner, name);
		return (T) field.get(instance);
	}
	
	public void writeField(Class<?> owner, String name, Object value) {
		writeField(owner, name, null, value);
	}
	
	@SneakyThrows
	public void writeField(Class<?> owner, String name, @Nullable Object instance, Object value) {
		Field field = getDeclaredField(owner, name);
		field.set(instance, value);
	}
	
	private Field getDeclaredField(Class<?> owner, String name) throws Exception {
		Field field = owner.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}
	
	@SneakyThrows
	public <T> ReflectedMethod<T> findMethod(Class<?> owner, String name, Class<?>... params) {
		Method method = owner.getDeclaredMethod(name, params);
		method.setAccessible(true);
		return new ReflectedMethod<>(method);
	}
	
	@SneakyThrows
	public <T> ReflectedConstructor<T> findConstructor(Class<T> type, Class<?>... params) {
		Constructor<T> constructor = type.getDeclaredConstructor(params);
		constructor.setAccessible(true);
		return new ReflectedConstructor<>(constructor);
	}
}

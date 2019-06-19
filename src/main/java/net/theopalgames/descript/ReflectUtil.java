package net.theopalgames.descript;

import java.lang.reflect.Field;

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
		Field field = owner.getDeclaredField(name);
		field.setAccessible(true);
		return (T) field.get(instance);
	}
}

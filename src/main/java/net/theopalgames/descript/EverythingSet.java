package net.theopalgames.descript;

import java.util.AbstractSet;
import java.util.Iterator;

import lombok.NoArgsConstructor;

import lombok.AccessLevel;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public final class EverythingSet<T> extends AbstractSet<T> {
	private static final EverythingSet<?> INSTANCE = new EverythingSet<>();
	
	@SuppressWarnings("unchecked")
	public static <T> EverythingSet<T> create() {
		return (EverythingSet<T>) INSTANCE;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return true;
			}
			
			@Override
			public T next() {
				return null;
			}
			
			@Override
			public void remove() {
				// NOOP
			}
		};
	}
	
	@Override
	public int size() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean contains(Object o) {
		return true;
	}
}

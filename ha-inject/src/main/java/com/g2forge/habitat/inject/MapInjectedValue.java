package com.g2forge.habitat.inject;

import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class MapInjectedValue<T> implements IInjectedValue<T> {
	protected final InjectedValueDescriptor<T> descriptor;

	public T get(Map<?, ?> map) {
		if ((map != null) && map.containsKey(getDescriptor().getId())) return getDescriptor().getType().cast(map.get(getDescriptor().getId()));
		return getDescriptor().getFallback();
	}

	public Map<? super String, ? super T> inject(Map<? super String, ? super T> map, T value) {
		map.put(getDescriptor().getId(), value);
		return map;
	}
}

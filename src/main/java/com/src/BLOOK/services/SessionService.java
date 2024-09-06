package com.src.BLOOK.services;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SessionService {
	private final Map<String, Object> sessionData = new LinkedHashMap<>();

    public void set(String key, Object value) {
        sessionData.put(key, value);
    }

    public Object get(String key) {
        return sessionData.get(key);
    }

    public void remove(String key) {
        sessionData.remove(key);
    }
}

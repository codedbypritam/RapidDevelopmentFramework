package com.codedbypritam.rdf.gui.click;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StringDataContainer {
    private final Map<String, String> data = new ConcurrentHashMap<>();

    public @Nullable String getData(@NonNull String identifier) {
        return data.get(identifier);
    }

    public void setData(@NonNull String identifier, @NonNull String data) {
        this.data.put(identifier, data);
    }
}

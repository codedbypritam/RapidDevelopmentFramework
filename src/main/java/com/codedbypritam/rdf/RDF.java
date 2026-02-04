package com.codedbypritam.rdf;

import com.codedbypritam.rdf.features.RDFFeatureFlag;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class RDF {
    private static final HashMap<RDFFeatureFlag, Boolean> features = new HashMap<>();

    public static void feature(@NonNull RDFFeatureFlag feature, boolean enabled) {
        features.put(feature, enabled);
    }

    public static boolean isFeatureEnabled(@NonNull RDFFeatureFlag feature) {
        return features.getOrDefault(feature, false);
    }

    public static void signalEnable(@NonNull JavaPlugin plugin) {

    }
}

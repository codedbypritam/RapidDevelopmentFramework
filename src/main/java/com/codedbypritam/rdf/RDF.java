package com.codedbypritam.rdf;

import com.codedbypritam.rdf.features.RDFFeatureFlag;
import com.codedbypritam.rdf.listeners.GUIListener;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class RDF {
    @Getter
    private static JavaPlugin plugin;

    private static final HashMap<RDFFeatureFlag, Boolean> features = new HashMap<>();

    public static void feature(@NonNull RDFFeatureFlag feature, boolean enabled) {
        features.put(feature, enabled);
    }
    public static boolean isFeatureEnabled(@NonNull RDFFeatureFlag feature) {
        return features.getOrDefault(feature, false);
    }

    public static void signalEnable(@NonNull JavaPlugin plugin) {
        RDF.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new GUIListener(), plugin);
    }
}

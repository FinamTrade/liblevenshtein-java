package com.github.liblevenshtein.collection;

import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FastUtils {
    private static final String USE_FAST_MAPS = "com.github.liblevenshtein.use_fast_maps";

    private static final boolean shouldUseFastMaps;

    static {
        String s = System.getProperty(USE_FAST_MAPS);
        shouldUseFastMaps = s == null || (s = s.trim()).isEmpty() || Boolean.valueOf(s);
    }

    public static <T> Map<Character,T> newChar2ObjectRBTreeMap() {
        return shouldUseFastMaps ? new Char2ObjectRBTreeMap<>() : new TreeMap<>();
    }

    public static <T> Map<T,Integer> newObject2IntHashMap() {
        return shouldUseFastMaps ? new Object2IntOpenHashMap<>() : new HashMap<>();
    }
}

package com.codedbypritam.rdf.executables;

public class Executable {
    public static <T1,T2> BiConsumer<T1,T2> bi(T1 param1, T2 param2) {
        return new BiConsumer<>(false);
    }
}

package com.codedbypritam.rdf.executables;

@FunctionalInterface
public interface BiConsumer<T1,T2> extends java.util.function.BiConsumer<T1,T2> {
    @Override
    void accept(T1 t1, T2 t2);
    default void accept()

    default boolean async() {
        return false;
    }
}

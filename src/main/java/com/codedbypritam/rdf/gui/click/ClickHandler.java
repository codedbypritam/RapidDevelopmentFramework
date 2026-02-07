package com.codedbypritam.rdf.gui.click;

import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ClickHandler {
    private Predicate<ClickContext> test = ctx -> true;

    private Function<ClickContext, CompletableFuture<Void>> handler =
            ctx -> CompletableFuture.completedFuture(null);

    public static ClickHandler newHandler() { return new ClickHandler(); }

    public ClickHandler clickTest(@NonNull Predicate<ClickContext> test) {
        this.test = test;
        return this;
    }

    public ClickHandler onClick(@NonNull Function<ClickContext, CompletableFuture<Void>> onClick) {
        this.handler = onClick;
        return this;
    }

    public ClickHandler onClickSync(@NonNull Consumer<ClickContext> onClick) {
        this.handler = ctx -> {
            onClick.accept(ctx);
            return CompletableFuture.completedFuture(null);
        };
        return this;
    }

    public CompletableFuture<Void> execute(@NonNull ClickContext ctx) {
        if (!test.test(ctx)) return CompletableFuture.completedFuture(null);
        return handler.apply(ctx);
    }
}
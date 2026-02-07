package com.codedbypritam.rdf.gui.rtn;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public record ReturnData(@NonNull ReturnType type, @Nullable String noPermNode) {}
package com.boyninja1555.markbin.lexer;

import org.jetbrains.annotations.NotNull;

public class MkbToken {
    private @NotNull MkbTokenType type;
    private @NotNull String value;

    public @NotNull MkbTokenType type() {
        return type;
    }

    public @NotNull String value() {
        return value;
    }

    public void type(@NotNull MkbTokenType type) {
        this.type = type;
    }

    public void value(@NotNull String value) {
        this.value = value;
    }

    public MkbToken(@NotNull MkbTokenType type, @NotNull String value) {
        type(type);
        value(value);
    }

    public static @NotNull MkbToken create(@NotNull MkbTokenType type, @NotNull String value) {
        return new MkbToken(type, value);
    }
}

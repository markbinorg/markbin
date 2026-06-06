package com.boyninja1555.markbin.lib;

import java.util.Arrays;

public class MkbException extends Exception {
    public MkbException(Object... message) {
        String[] messageParts = Arrays.<Object>stream(message)
                .<String>map(Object::toString)
                .toList()
                .toArray(new String[0]);

        super(String.join(" ", messageParts));
    }
}

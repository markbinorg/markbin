package com.boyninja1555.markbin.lib;

import java.util.Arrays;

public class WarningLogger {
    public static void log(Object... message) {
        String[] messageParts = Arrays.<Object>stream(message)
                .<String>map(Object::toString)
                .toList()
                .toArray(new String[0]);

        System.err.println("Warning! " + String.join(" ", messageParts));
    }
}

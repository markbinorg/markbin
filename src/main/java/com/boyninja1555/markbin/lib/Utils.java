package com.boyninja1555.markbin.lib;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Utils {

    public static @NotNull String cleanupSource(@NotNull String source) {
        return source.replace("\r", "").replace("\t", "    ") + "\n";
    }

    public static byte[] byteListAsPrimitive(@NotNull List<Byte> byteList) {
        byte[] result = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) result[i] = byteList.get(i);
        return result;
    }

    public static boolean isDivisibleBy(int i, int by) {
        return i % by == 0;
    }
}

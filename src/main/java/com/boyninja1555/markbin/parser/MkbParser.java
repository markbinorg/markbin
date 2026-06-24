package com.boyninja1555.markbin.parser;

import com.boyninja1555.markbin.lexer.MkbToken;
import com.boyninja1555.markbin.lexer.MkbTokenType;
import com.boyninja1555.markbin.lib.Utils;
import com.boyninja1555.markbin.lib.WarningLogger;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MkbParser {
    private enum LiteralParseMode {
        BITS {
            @Override
            public @NotNull String encode(@NotNull String literal) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < literal.length(); i++) {
                    char c = literal.charAt(i);
                    if (c == '0' || c == '1') {
                        sb.append(c);
                        continue;
                    }

                    WarningLogger.log("Located invalid value inside a <bits> block, skipping it.");
                }

                return sb.toString();
            }
        },

        HEX {
            @Override
            public @NotNull String encode(@NotNull String literal) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < literal.length(); i++) {
                    char c = literal.charAt(i);
                    int value = Character.digit(c, 16);
                    if (value == -1) {
                        WarningLogger.log("Located invalid value inside a <hex> block, skipping it.");
                        continue;
                    }

                    sb.append(String.format("%4s", Integer.toBinaryString(value)).replace(' ', '0'));
                }

                return sb.toString();
            }
        },

        UTF8 {
            @Override
            public @NotNull String encode(@NotNull String literal) {
                return encodeCharset(literal, StandardCharsets.UTF_8);
            }
        },

        UTF16 {
            @Override
            public @NotNull String encode(@NotNull String literal) {
                return encodeCharset(literal, StandardCharsets.UTF_16);
            }
        };

        public abstract @NotNull String encode(@NotNull String literal);

        protected static @NotNull String encodeCharset(@NotNull String literal, @NotNull Charset charset) {
            StringBuilder bits = new StringBuilder();
            byte[] encoded = literal.getBytes(charset);
            for (byte b : encoded)
                for (int i = 7; i >= 0; i--)
                    bits.append(((b >>> i) & 1));

            return bits.toString();
        }
    }

    private final @NotNull List<MkbToken> tokens;
    private LiteralParseMode lpMode;
    private int position;

    public MkbParser(@NotNull List<MkbToken> tokens) {
        this.tokens = tokens;
        position = 0;
    }

    private void handleFlag(@NotNull String flag) {
        lpMode = switch (flag) {
            case "bits" -> LiteralParseMode.BITS;
            case "hex" -> LiteralParseMode.HEX;
            case "utf8" -> LiteralParseMode.UTF8;
            case "utf16" -> LiteralParseMode.UTF16;
            default -> lpMode;
        };
    }

    public byte[] parse() throws MkbParseException {
        lpMode = LiteralParseMode.BITS;
        StringBuilder bits = new StringBuilder();
        while (position < tokens.size()) {
            MkbToken token = tokens.get(position);
            if (token.type().equals(MkbTokenType.LITERAL)) bits.append(lpMode.encode(token.value()));
            if (token.type().equals(MkbTokenType.FLAG)) handleFlag(token.value());
            position++;
        }

        if (!Utils.isDivisibleBy(bits.length(), 8))
            throw new MkbParseException("Bit count must be divisible by 8!", bits.length(), "total bits found.");

        List<Byte> bytes = new ArrayList<>();
        for (int i = 0; i < bits.length(); i += 8) {
            String chunk = bits.substring(i, i + 8);
            bytes.add((byte) Integer.parseInt(chunk, 2));
        }

        return Utils.byteListAsPrimitive(bytes);
    }
}

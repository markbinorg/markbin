package com.boyninja1555.markbin.lexer;

import com.boyninja1555.markbin.lib.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MkbLexer {
    private final @NotNull String source;
    private final @NotNull List<MkbToken> tokens;
    private int position;

    public @NotNull String source() {
        return source;
    }

    public @NotNull List<MkbToken> tokens() {
        return new ArrayList<>(tokens);
    }

    public int position() {
        return position;
    }

    public MkbLexer(@NotNull String source) {
        this.source = Utils.cleanupSource(source);
        tokens = new ArrayList<>();
        position = 0;
    }

    private char consume() {
        char c = source.charAt(position);
        position++;
        return c;
    }

    private String consumeUntil(char until) {
        StringBuilder sb = new StringBuilder();
        while (position < source.length()) {
            char c = consume();
            if (c == until) break;
            sb.append(c);
        }

        return sb.toString();
    }

    public void tokenize() {
        StringBuilder temp = new StringBuilder();
        boolean inString = false;
        while (position < source.length()) {
            char c = consume();
            if (c == '"') {
                inString = !inString;
                if (!inString) {
                    tokens.add(MkbToken.create(MkbTokenType.LITERAL, temp.toString()));
                    temp.setLength(0);
                }

                continue;
            }

            if (inString) {
                if (c == '\\' && position < source.length()) {
                    char next = consume();
                    temp.append(switch (next) {
                        case 'n' -> '\n';
                        case 't' -> '\t';
                        case '"' -> '"';
                        case '\\' -> '\\';
                        default -> next;
                    });
                } else temp.append(c);
                continue;
            }

            if (c == ' ' || c == '\n') continue;
            if (c == ';') {
                consumeUntil('\n');
                continue;
            }

            if (c == '<') {
                if (!temp.isEmpty()) {
                    tokens.add(MkbToken.create(MkbTokenType.LITERAL, temp.toString()));
                    temp.setLength(0);
                }

                tokens.add(MkbToken.create(MkbTokenType.FLAG, consumeUntil('>')));
                continue;
            }

            temp.append(c);
        }

        if (!temp.isEmpty()) {
            tokens.add(MkbToken.create(MkbTokenType.LITERAL, temp.toString()));
            temp.setLength(0);
        }
    }
}

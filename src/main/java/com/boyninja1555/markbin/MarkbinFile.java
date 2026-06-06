package com.boyninja1555.markbin;

import com.boyninja1555.markbin.lexer.MkbLexer;
import com.boyninja1555.markbin.lexer.MkbToken;
import com.boyninja1555.markbin.parser.MkbParseException;
import com.boyninja1555.markbin.parser.MkbParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MarkbinFile {
    private final @NotNull File file;
    private MkbLexer lexer;

    public MarkbinFile(@NotNull String path) {
        file = new File(path);
    }

    public boolean load() {
        try {
            String source = Files.readString(file.toPath());
            lexer = new MkbLexer(source);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public byte[] asBytes() throws MkbParseException {
        lexer.tokenize();
        for (MkbToken token : lexer.tokens()) {
            System.out.println("Token:\n\tType: " + token.type() + "\n\tValue: " + token.value());
        }

        MkbParser parser = new MkbParser(lexer.tokens());
        return parser.parse();
    }
}

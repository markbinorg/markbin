package com.boyninja1555.markbin;

import com.boyninja1555.markbin.parser.MkbParseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: mkb <file.mkb> <output>");
            System.exit(1);
            return;
        }

        String filepath = args[0];
        Path outpath = Path.of(args[1]);
        MarkbinFile file = new MarkbinFile(filepath);
        if (!file.load()) {
            System.err.println("File does not exist! " + filepath);
            System.exit(1);
            return;
        }

        try {
            if (!Files.exists(outpath)) Files.createFile(outpath);
            try (var out = new FileOutputStream(outpath.toFile())) {
                out.write(file.asBytes());
            } catch (MkbParseException | IOException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}

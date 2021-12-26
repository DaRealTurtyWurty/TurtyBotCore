package io.github.darealturtywurty.turtybotcore.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private static final Path CORE = Path.of("config/core.json");

    private static void createCore() {
        try {
            if (!Files.exists(CORE)) {
                Files.createDirectories(CORE.getParent());
                Files.createFile(CORE);
            }
        } catch (final IOException exception) {
            throw new IllegalStateException(exception);
        }
    }
}

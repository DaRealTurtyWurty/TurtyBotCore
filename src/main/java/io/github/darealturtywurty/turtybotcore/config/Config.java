package io.github.darealturtywurty.turtybotcore.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import io.github.darealturtywurty.turtybotcore.utils.Constants;

public class Config {
    private static final String CONFIG_PATH = "config/%s.json";
    
    public final Map<String, Object> objectMap;
    private final Path path;

    protected Config(ConfigBuilder config) {
        this.objectMap = ImmutableMap.copyOf(config.objectMap);
        this.path = Path.of(String.format(CONFIG_PATH, config.fileName));
    }

    public void create() {
        if (!Files.exists(this.path)) {
            this.path.toFile().mkdirs();
        }

        putDefaults();
    }

    protected JsonObject getJsonObject() {
        try {
            return Constants.GSON.fromJson(Files.readString(this.path, StandardCharsets.UTF_8), JsonObject.class);
        } catch (JsonSyntaxException | IOException exception) {
            throw new IllegalStateException("There was an error reading the core config JSON!", exception);
        }
    }

    protected boolean hasDefaults() {
        final JsonObject config = getJsonObject();
        final var foundAll = new AtomicBoolean(true);
        this.objectMap.forEach((key, value) -> {
            if (!config.has(key)) {
                foundAll.set(false);
            }
        });
        return foundAll.get();
    }
    
    protected void putDefaults() {
        if (!hasDefaults()) {
            final var json = new JsonObject();
            this.objectMap.forEach((key, value) -> {
                if (value instanceof final Number number) {
                    json.addProperty(key, number);
                } else if (value instanceof final Character cha) {
                    json.addProperty(key, cha);
                } else if (value instanceof final String str) {
                    json.addProperty(key, str);
                } else if (value instanceof final Boolean bool) {
                    json.addProperty(key, bool);
                } else if (((Object[]) value)[0] instanceof Number) {
                    final var array = (Number[]) value;
                    final var jsonArray = new JsonArray();
                    for (final Number element : array) {
                        jsonArray.add(element);
                    }

                    json.add(key, jsonArray);
                } else if (((Object[]) value)[0] instanceof Character) {
                    final var array = (Character[]) value;
                    final var jsonArray = new JsonArray();
                    for (final Character element : array) {
                        jsonArray.add(element);
                    }

                    json.add(key, jsonArray);
                } else if (((Object[]) value)[0] instanceof String) {
                    final var array = (String[]) value;
                    final var jsonArray = new JsonArray();
                    for (final String element : array) {
                        jsonArray.add(element);
                    }

                    json.add(key, jsonArray);
                } else if (((Object[]) value)[0] instanceof Boolean) {
                    final var array = (Boolean[]) value;
                    final var jsonArray = new JsonArray();
                    for (final Boolean element : array) {
                        jsonArray.add(element);
                    }

                    json.add(key, jsonArray);
                }
            });
            
            try {
                Files.writeString(this.path, Constants.GSON.toJson(json), StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE);
            } catch (final IOException exception) {
                throw new IllegalStateException("There was an error constructing the core config JSON!", exception);
            }
        }
    }
    
    public static class ConfigBuilder {
        private final Map<String, Object> objectMap = new HashMap<>();
        private final Set<SerializableJsonObject> customJsonObjects = new HashSet<>();
        private final String fileName;

        public ConfigBuilder(String fileName) {
            this.fileName = fileName;
        }
        
        public ConfigBuilder add(String key, Boolean... value) {
            if (value.length >= 1) {
                this.objectMap.put(key, value);
            }
            return this;
        }

        public ConfigBuilder add(String key, Boolean value) {
            this.objectMap.put(key, value);
            return this;
        }

        public ConfigBuilder add(String key, Character value) {
            this.objectMap.put(key, value);
            return this;
        }

        public ConfigBuilder add(String key, Character... value) {
            if (value.length >= 1) {
                this.objectMap.put(key, value);
            }
            return this;
        }
        
        public ConfigBuilder add(String key, Number value) {
            this.objectMap.put(key, value);
            return this;
        }
        
        public ConfigBuilder add(String key, Number... value) {
            if (value.length >= 1) {
                this.objectMap.put(key, value);
            }
            return this;
        }
        
        public ConfigBuilder add(String key, SerializableJsonObject object) {
            addType(object);
            
            this.objectMap.put(key, object);
            return this;
        }

        public ConfigBuilder add(String key, String value) {
            this.objectMap.put(key, value);
            return this;
        }
        
        public ConfigBuilder add(String key, String... value) {
            if (value.length >= 1) {
                this.objectMap.put(key, value);
            }
            return this;
        }
        
        public ConfigBuilder addType(SerializableJsonObject object) {
            if (!this.customJsonObjects.contains(object)) {
                this.customJsonObjects.add(object);
            }
            return this;
        }
        
        public Config build() {
            return new Config(this);
        }
    }
}

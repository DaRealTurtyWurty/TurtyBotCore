package io.github.darealturtywurty.turtybotcore.config;

import java.util.function.Function;

import com.google.gson.JsonElement;

public class SerializableJsonObject<Type extends Object> {
    public final Function<Type, JsonElement> serializer;
    public final Function<JsonElement, Type> deserializer;

    public SerializableJsonObject(Function<Type, JsonElement> serializer, Function<JsonElement, Type> deserializer) {
        this.serializer = serializer;
        this.deserializer = deserializer;
    }
}

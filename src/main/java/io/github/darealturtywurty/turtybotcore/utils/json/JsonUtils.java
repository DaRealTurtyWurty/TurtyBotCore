package io.github.darealturtywurty.turtybotcore.utils.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.github.darealturtywurty.turtybotcore.utils.Constants;

public final class JsonUtils {
    private JsonUtils() {
        throw new IllegalAccessError("Unable to construct utility class: '" + this.getClass().getSimpleName() + "'!");
    }
    
    @SuppressWarnings("serial")
    public static <Key, Value> Map<Key, Value> getAsMap(JsonObject json, String key, Class<Key> keyType,
            Class<Value> valueType) {
        if (!json.has(key))
            return Map.of();
        
        return Constants.GSON.fromJson(json.get(key), new TypeToken<Map<Key, Value>>() {
        }.getType());
    }

    public static BigDecimal getBigDecimalFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return new BigDecimal(-1);
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isNumber())
            return elem.getAsBigDecimal();
        if (elem.getAsJsonPrimitive().isString())
            return new BigDecimal(elem.getAsString());
        
        return new BigDecimal(-1);
    }
    
    public static BigInteger getBigIntFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return new BigInteger(new byte[] { -1 });
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isNumber())
            return elem.getAsBigInteger();
        if (elem.getAsJsonPrimitive().isString())
            return new BigInteger(elem.getAsString());
        
        return new BigInteger(new byte[] { -1 });
    }
    
    @Nullable
    public static Boolean getBoolFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return null;
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isBoolean())
            return elem.getAsBoolean();
        if (elem.getAsJsonPrimitive().isString())
            return Boolean.parseBoolean(elem.getAsString());
        
        return null;
    }
    
    public static byte getByteFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return (byte) -1;
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isNumber())
            return elem.getAsByte();
        if (elem.getAsJsonPrimitive().isString())
            return Byte.parseByte(elem.getAsString());
        
        return (byte) -1;
    }
    
    @SuppressWarnings("deprecation")
    @Nullable
    public static Character getCharFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return null;
        return json.get(key).getAsCharacter();
    }
    
    public static double getDoubleFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return -1D;
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isNumber())
            return elem.getAsDouble();
        if (elem.getAsJsonPrimitive().isString())
            return Double.parseDouble(elem.getAsString());
        
        return -1D;
    }
    
    public static float getFloatFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return -1F;
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isNumber())
            return elem.getAsFloat();
        if (elem.getAsJsonPrimitive().isString())
            return Float.parseFloat(elem.getAsString());
        
        return -1F;
    }
    
    public static int getIntFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return -1;
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isNumber())
            return elem.getAsInt();
        if (elem.getAsJsonPrimitive().isString())
            return Integer.parseInt(elem.getAsString());
        
        return -1;
    }
    
    public static long getLongFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return -1L;
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isNumber())
            return elem.getAsLong();
        if (elem.getAsJsonPrimitive().isString())
            return Long.parseLong(elem.getAsString());
        
        return -1L;
    }
    
    public static short getShortFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return (short) -1;
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isNumber())
            return elem.getAsShort();
        if (elem.getAsJsonPrimitive().isString())
            return Short.parseShort(elem.getAsString());
        
        return (short) -1;
    }

    @Nullable
    public static String getStringFromJson(JsonObject json, String key) {
        if (!json.has(key) || !json.get(key).isJsonPrimitive())
            return null;
        final JsonElement elem = json.get(key);
        if (elem.getAsJsonPrimitive().isString())
            return elem.getAsString();
        final JsonPrimitive primitive = elem.getAsJsonPrimitive();
        if (primitive.isBoolean())
            return String.valueOf(primitive.getAsBoolean());
        if (primitive.isNumber())
            return String.valueOf(primitive.getAsNumber());

        return null;
    }

    public static boolean isNull(JsonObject json, String key) {
        return json.has(key) && json.isJsonNull();
    }
}

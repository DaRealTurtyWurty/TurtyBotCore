package io.github.darealturtywurty.turtybotcore.utils.json;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StringDateMapAdapter
        implements JsonDeserializer<Map<String, Date>>, JsonSerializer<Map<String, Date>> {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
    
    @Override
    public Map<String, Date> deserialize(JsonElement elem, Type type,
            JsonDeserializationContext jsonDeserializationContext) {
        return elem.getAsJsonObject().entrySet().stream().filter(e -> e.getValue().isJsonPrimitive())
                .filter(e -> e.getValue().getAsJsonPrimitive().isString())
                .collect(Collectors.toMap(Map.Entry::getKey, e -> formatDate(e.getValue())));
    }
    
    @Override
    public JsonElement serialize(Map<String, Date> src, Type typeOfSrc, JsonSerializationContext context) {
        final var obj = new JsonObject();
        src.forEach((key, date) -> {
            final String strDate = this.format.format(date);
            obj.addProperty(key, strDate);
        });

        return obj;
    }

    private Date formatDate(JsonElement value) {
        try {
            return this.format.parse(value.getAsString());
        } catch (final ParseException exception) {
            throw new JsonParseException(exception);
        }
    }
}

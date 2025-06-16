package backend.JDA;

import backend.JDA.modelo.Sabor;
import com.nimbusds.jose.shaded.gson.JsonDeserializationContext;
import com.nimbusds.jose.shaded.gson.JsonDeserializer;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.*;

public class SaborListDeserializer implements JsonDeserializer<List<Sabor>> {

    @Override
    public List<Sabor> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        List<Sabor> result = new ArrayList<>();
        for (JsonElement el : json.getAsJsonArray()) {
            String raw = el.getAsString();
            try {
                result.add(Sabor.fromString(raw));
            } catch (IllegalArgumentException e) {
                System.err.println("Atributo desconocido: " + raw);
            }
        }
        return result;
    }
}

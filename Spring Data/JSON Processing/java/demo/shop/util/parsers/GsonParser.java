package demo.shop.util.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GsonParser implements Parser {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public <T> T fromJSon(String json, Class<T> clazz) {
        return this.gson.fromJson(json, clazz);
    }

    @Override
    public <T> String toJson(Object entity) {
        return this.gson.toJson(entity);
    }
}
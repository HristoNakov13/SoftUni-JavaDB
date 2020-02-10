package demo.shop.util.parsers;

public interface Parser {
    <T> T fromJSon(String json, Class<T> clazz);
}

package softuni.exam.util.parser;

import java.util.List;

public interface XmlParser {
    <T> T fromXmlToObject(String path, Class<T> clazz);
}

package softuni.exam.util.parser.xmlparser;

public interface XmlParser {

    <T> T parseXml(String xml, Class<T> clazz);
}

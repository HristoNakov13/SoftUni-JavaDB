package hiberspring.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserImpl implements XmlParser {

    @Override
    public <O> O parseXml(Class<O> objectClass, String filePath) throws JAXBException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(objectClass).createUnmarshaller();

        return (O) unmarshaller.unmarshal(new File(filePath));
    }
}

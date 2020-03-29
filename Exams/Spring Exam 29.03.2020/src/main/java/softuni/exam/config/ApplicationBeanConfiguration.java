package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.util.parser.dateparser.DateParser;
import softuni.exam.util.parser.dateparser.DateParserImpl;
import softuni.exam.util.parser.xmlparser.XmlParser;
import softuni.exam.util.parser.xmlparser.XmlParserImpl;
import softuni.exam.util.reader.FileUtil;
import softuni.exam.util.reader.FileUtilImpl;
import softuni.exam.util.validator.ValidationUtil;
import softuni.exam.util.validator.ValidationUtilImpl;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public DateParser dateParser() {
        return new DateParserImpl();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}
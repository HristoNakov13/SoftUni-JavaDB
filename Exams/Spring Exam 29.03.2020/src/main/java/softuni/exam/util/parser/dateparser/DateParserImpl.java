package softuni.exam.util.parser.dateparser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParserImpl implements DateParser{


    @Override
    public LocalDate fromStringToLocalDate(String date, String format) {
        try {
           return LocalDate.parse(date, this.getFormatter(format));
        }catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public LocalDateTime fromStringToLocalDateTime(String date, String format) {
        try {
            return LocalDateTime.parse(date, this.getFormatter(format));
        }catch (DateTimeParseException e) {
            return null;
        }
    }

    private DateTimeFormatter getFormatter(String format) {
        return DateTimeFormatter.ofPattern(format);
    }
}

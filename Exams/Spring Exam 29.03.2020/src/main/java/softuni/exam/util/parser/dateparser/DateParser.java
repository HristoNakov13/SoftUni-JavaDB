package softuni.exam.util.parser.dateparser;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateParser {

    LocalDate fromStringToLocalDate(String date, String format);

    LocalDateTime fromStringToLocalDateTime(String date, String format);
}

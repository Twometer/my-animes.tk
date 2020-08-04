package tk.myanimes.text;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class Parser {

    public static Instant parseDate(String yyyymmdd) {
        return LocalDate.parse(yyyymmdd).atStartOfDay().toInstant(ZoneOffset.UTC);
    }

}

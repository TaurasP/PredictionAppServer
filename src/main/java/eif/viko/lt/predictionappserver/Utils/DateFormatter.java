package eif.viko.lt.predictionappserver.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static String formatDateTime(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }

        // Define the formatter for "yyyy-MM-dd HH:mm" pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Format the LocalDateTime and return the resulting string
        return date.format(formatter);
    }
}

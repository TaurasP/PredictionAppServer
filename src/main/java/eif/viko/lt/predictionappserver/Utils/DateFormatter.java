package eif.viko.lt.predictionappserver.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static String formatDateTimeToString(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }

        // Define the formatter for "yyyy-MM-dd HH:mm" pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Format the LocalDateTime and return the resulting string
        return date.format(formatter);
    }

    public static LocalDateTime formatDateTime(LocalDateTime dateTime) {
        // Define the formatter for "yyyy-MM-dd HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Format the original LocalDateTime into a string
        String formattedDateTimeString = dateTime.format(formatter);

        // Parse the string back into a LocalDateTime
        return LocalDateTime.parse(formattedDateTimeString, formatter);
    }
}

package main.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * The `DateLabelFormatter` class is a custom formatter used for formatting and parsing dates
 * in JDatePicker components. It handles the conversion between a `String` representation
 * and a `Date` object based on a specified date pattern.
 */
public class DateLabelFormatter extends AbstractFormatter {

    private final String datePattern = "dd-MM-yyyy"; // Date pattern to use for formatting and parsing
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Converts a `String` representation of a date into a `Date` object.
     *
     * @param text the string representation of the date
     * @return the corresponding `Date` object
     * @throws ParseException if the text cannot be parsed into a valid date
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parse(text);
    }

    /**
     * Converts a `Calendar` object into a formatted `String` representation.
     *
     * @param value the `Calendar` object to format
     * @return a string representation of the date, or an empty string if the value is null
     * @throws ParseException if the value cannot be converted to a string
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}

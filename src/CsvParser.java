import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    /**
     * Parses a record from a CSV file into fields.
     *
     * <p>
     * This method handles escaped quotes correctly ({@code ""}) and throws an exception if:</p>
     * <ul>
     *   <li>a quote is detected inside an unquoted field</li>
     *   <li>the record is not terminated with a closing quote.</li>
     * </ul>
     * <p>
     * This method parses a CSV file line by line and therefore does not fully comply with
     * RFC&nbsp;4180, which allows fields to contain line breaks.
     * </p>
     * <p>Written by Philip Mees for CMPT&nbsp;305</p>
     *
     * @param record a record from a CSV file
     * @return the fields in the record
     * @throws IllegalArgumentException if double quotes are misplaced
     */
    public static String[] parseCSVLine(String record) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < record.length(); i++) {
            char c = record.charAt(i);

            if (c == '"') {
                if (inQuotes) {
                    if (i + 1 < record.length() && record.charAt(i + 1) == '"') {
                        // Escaped quote ("")
                        current.append('"');
                        i++; // Skip the second quote
                    } else {
                        // Closing quote
                        inQuotes = false;
                    }
                } else {
                    // Opening quote (only valid at start of field)
                    if (!current.isEmpty()) {
                        throw new IllegalArgumentException("Quote in unquoted field");
                    }
                    inQuotes = true;
                }
            } else if (c == ',' && !inQuotes) {
                // End of field
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        if (inQuotes) {
            // Missing closing quote
            throw new IllegalArgumentException("Unterminated quoted field");
        }

        // Add last field
        fields.add(current.toString());

        // Convert ArrayList to String array.
        return fields.toArray(new String[0]);
    }
}

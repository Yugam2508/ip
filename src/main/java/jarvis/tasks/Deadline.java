package jarvis.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected String by;
    protected LocalDate date;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        try {
            // Level-8: Try to parse the date string (expecting yyyy-MM-dd)
            this.date = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            // If parsing fails (e.g., user entered "Sunday"), keep it as null
            this.date = null;
        }
    }

    @Override
    public String toString() {
        if (date != null) {
            // Level-8: Print nicely as "MMM dd yyyy" (e.g., Oct 15 2019)
            return "[D]" + super.toString() + " (by: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
        } else {
            // Fallback for non-standard dates (e.g., "Sunday")
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    @Override
    public String toFileFormat() {
        // Save it in the original format (by) so loading works correctly
        return "D | " + super.toFileFormat() + " | " + by;
    }
}
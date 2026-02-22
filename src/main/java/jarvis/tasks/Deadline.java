package jarvis.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task.
 */
public class Deadline extends Task {

    private final LocalDate by;

    public Deadline(String description, String date) {
        super(description);
        this.by = LocalDate.parse(date);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM d yyyy");
        return "[D]" + super.toString()
                + " (by: " + by.format(formatter) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat() + " | " + by;
    }
}
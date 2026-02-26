package jarvis.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    protected LocalDate by;
    protected String byString;

    /**
     * Constructs a Deadline task with the given description and deadline.
     *
     * @param description The description of the task.
     * @param by The deadline in string format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.byString = by;
        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            this.by = null;
        }
    }

    /**
     * Returns the string representation of the deadline.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        String dateStr = (by != null)
                ? by.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                : byString;
        return "[D]" + super.toString() + " (by: " + dateStr + ")";
    }

    /**
     * Returns the file format representation of the deadline.
     *
     * @return The file format string.
     */
    @Override
    public String toFileFormat() {
        return "D" + super.toFileFormat() + " | " + byString;
    }
}
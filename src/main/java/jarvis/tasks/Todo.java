package jarvis.tasks;

/**
 * Represents a todo task.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The description of the todo.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the file format representation of the todo.
     *
     * @return The file format string.
     */
    @Override
    public String toFileFormat() {
        return "T" + super.toFileFormat();
    }
}
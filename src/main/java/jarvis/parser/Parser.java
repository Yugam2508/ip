package jarvis.parser;
import jarvis.tasks.JarvisException;

/**
 * Parses user input commands and extracts relevant information.
 */
public class Parser {

    /**
     * Parses the user's command string and returns the corresponding CommandType.
     *
     * @param fullCommand The full command string entered by the user.
     * @return The CommandType corresponding to the command.
     */
    public static CommandType parseCommand(String fullCommand) {
        String command = fullCommand.split(" ")[0].toLowerCase();
        switch (command) {
        case "bye":
            return CommandType.BYE;
        case "list":
            return CommandType.LIST;
        case "mark":
            return CommandType.MARK;
        case "unmark":
            return CommandType.UNMARK;
        case "delete":
            return CommandType.DELETE;
        case "todo":
            return CommandType.TODO;
        case "deadline":
            return CommandType.DEADLINE;
        case "event":
            return CommandType.EVENT;
        case "find":
            return CommandType.FIND;
        case "cheer":
            return CommandType.CHEER;
        default:
            return CommandType.UNKNOWN;
        }
    }

    /**
     * Extracts the task index from a command string.
     *
     * @param fullCommand The full command string containing the index.
     * @return The zero-based index of the task.
     */
    /**
     * Extracts the task index from a command string.
     *
     * @param fullCommand The full command string containing the index.
     * @return The zero-based index of the task.
     * @throws JarvisException If index is missing or invalid.
     */
    public static int parseIndex(String fullCommand) throws JarvisException {
        assert fullCommand != null && !fullCommand.isEmpty() : "Command cannot be null or empty";
        String[] parts = fullCommand.split(" ");

        if (parts.length < 2) {
            throw new JarvisException("Please specify which task number to modify, sir.");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0) {
                throw new JarvisException("Task number must be positive, sir.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new JarvisException("Invalid task number, sir. Please use a number.");
        }
    }
}
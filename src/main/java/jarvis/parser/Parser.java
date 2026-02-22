package jarvis.parser;

/**
 * Parses user input into commands and parameters.
 */
public class Parser {

    public static CommandType parseCommand(String input) {
        String commandWord = input.split(" ")[0].toUpperCase();
        try {
            return CommandType.valueOf(commandWord);
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

    public static int parseIndex(String input) {
        return Integer.parseInt(input.split(" ")[1]) - 1;
    }

    public static String parseDescription(String input) {
        return input.substring(input.indexOf(" ") + 1).trim();
    }

    public static String[] parseDeadline(String input) {
        String[] parts = input.substring(9).split(" /by ");
        return parts;
    }

    public static String[] parseEvent(String input) {
        String[] parts = input.substring(6).split(" /from | /to ");
        return parts;
    }

    public static String parseFindKeyword(String input) {
        return input.split(" ", 2)[1];
    }
}
public class Parser {
    public static CommandType parseCommand(String userInput) {
        String commandWord = userInput.split(" ")[0].toUpperCase();
        try {
            return CommandType.valueOf(commandWord);
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

    public static int parseIndex(String userInput) {
        return Integer.parseInt(userInput.split(" ")[1]) - 1;
    }
}
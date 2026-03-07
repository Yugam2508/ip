package jarvis.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import jarvis.tasks.JarvisException;

/**
 * JUnit test class for Parser functionality.
 */
public class ParserTest {

    /**
     * Tests that todo command is parsed correctly.
     */
    @Test
    public void testParseTodoCommand() {
        CommandType result = Parser.parseCommand("todo read book");
        assertEquals(CommandType.TODO, result);
    }

    /**
     * Tests that deadline command is parsed correctly.
     */
    @Test
    public void testParseDeadlineCommand() {
        CommandType result = Parser.parseCommand("deadline submit /by tomorrow");
        assertEquals(CommandType.DEADLINE, result);
    }

    /**
     * Tests that event command is parsed correctly.
     */
    @Test
    public void testParseEventCommand() {
        CommandType result = Parser.parseCommand("event meeting /from 2pm /to 4pm");
        assertEquals(CommandType.EVENT, result);
    }

    /**
     * Tests that list command is parsed correctly.
     */
    @Test
    public void testParseListCommand() {
        CommandType result = Parser.parseCommand("list");
        assertEquals(CommandType.LIST, result);
    }

    /**
     * Tests that unknown command returns UNKNOWN.
     */
    @Test
    public void testParseUnknownCommand() {
        CommandType result = Parser.parseCommand("invalid");
        assertEquals(CommandType.UNKNOWN, result);
    }

    /**
     * Tests that parseIndex extracts correct index.
     */
    @Test
    public void testParseIndex() throws JarvisException{
        int result = Parser.parseIndex("mark 3");
        assertEquals(2, result);
    }

    /**
     * Tests that parseIndex works with delete command.
     */
    @Test
    public void testParseIndexDelete() throws JarvisException{
        int result = Parser.parseIndex("delete 5");
        assertEquals(4, result);
    }
}
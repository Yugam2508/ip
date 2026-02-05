package jarvis;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void testParseCommand_bye() {
        assertEquals(CommandType.BYE, Parser.parseCommand("bye"));
    }

    @Test
    public void testParseCommand_todo() {
        assertEquals(CommandType.TODO, Parser.parseCommand("todo read book"));
    }

    @Test
    public void testParseCommand_unknown() {
        assertEquals(CommandType.UNKNOWN, Parser.parseCommand("blah blah"));
    }
}
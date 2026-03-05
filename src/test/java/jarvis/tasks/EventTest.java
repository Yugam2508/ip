package jarvis.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void testEventCreation() {
        Event e = new Event("project meeting", "Mon 2pm", "Mon 4pm");
        assertEquals("[E][ ] project meeting (from: Mon 2pm to: Mon 4pm)", e.toString());
    }

    @Test
    public void testEventFileFormat() {
        Event e = new Event("team meeting", "2pm", "4pm");
        assertEquals("E | 0 | team meeting | 2pm | 4pm", e.toFileFormat());
    }
}
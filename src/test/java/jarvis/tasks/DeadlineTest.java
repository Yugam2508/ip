package jarvis.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void testDeadlineCreation() {
        Deadline d = new Deadline("return book", "2024-12-25");
        assertEquals("[D][ ] return book (by: Dec 25 2024)", d.toString());
    }

    @Test
    public void testDeadlineMarking() {
        Deadline d = new Deadline("submit report", "2024-11-30");
        d.markAsDone();
        assertEquals("[D][X] submit report (by: Nov 30 2024)", d.toString());
    }
}
package jarvis;

/**
 * Represents a custom exception thrown by the Jarvis application
 * when user input is invalid or a command cannot be processed.
 */
public class JarvisException extends Exception {

    /**
     * Creates a JarvisException with the specified message.
     *
     * @param message Error message.
     */
    public JarvisException(String message) {
        super(message);
    }
}
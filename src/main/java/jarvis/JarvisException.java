package jarvis.tasks;

/**
 * Represents exceptions specific to the Jarvis application.
 */
public class JarvisException extends Exception {

    /**
     * Constructs a JarvisException with the specified message.
     *
     * @param message The error message.
     */
    public JarvisException(String message) {
        super(message);
    }
}
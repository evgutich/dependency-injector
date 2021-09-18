package by.task.exception;

public class BeanCreationException extends RuntimeException {
    public BeanCreationException(Throwable cause) {
        super("can't create new instance", cause);
    }
}

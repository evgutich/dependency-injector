package by.task.exception;

public class TooManyConstructorsException extends RuntimeException {
    public TooManyConstructorsException(Class<?> clazz) {
        super("Too many constructors with @Inject for " + clazz.getName());
    }
}

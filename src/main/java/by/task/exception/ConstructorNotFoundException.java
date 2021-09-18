package by.task.exception;

public class ConstructorNotFoundException extends RuntimeException {
    public ConstructorNotFoundException(Class<?> clazz) {
        super("No default and @Inject constructors for " + clazz.getName());
    }
}

package by.task.exception;

public class BindingNotFoundException extends RuntimeException {
    public BindingNotFoundException(Class<?> clazz) {
        super("binding for " + clazz.getName() + " not found");
    }
}

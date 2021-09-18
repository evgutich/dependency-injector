package by.task.config;

import java.lang.reflect.Constructor;

public class BeanConfig {
    private final Constructor<?> constructor;
    private final boolean isSingleton;

    public BeanConfig(Constructor<?> constructor, boolean isSingleton) {
        this.constructor = constructor;
        this.isSingleton = isSingleton;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public boolean isSingleton() {
        return isSingleton;
    }
}

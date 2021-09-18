package by.task.config;

import by.task.provider.Provider;

import java.lang.reflect.Constructor;

public class BeanConfig<T> {
    private final Constructor<? extends T> constructor;
    private final boolean isSingleton;
    private Provider<T> provider;

    public BeanConfig(Constructor<? extends T> constructor, boolean isSingleton) {
        this.constructor = constructor;
        this.isSingleton = isSingleton;
    }

    public Constructor<? extends T> getConstructor() {
        return constructor;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public Provider<T> getProvider() {
        return provider;
    }

    public void setProvider(Provider<T> provider) {
        this.provider = provider;
    }
}

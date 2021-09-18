package by.task.provider;

import by.task.config.BeanConfig;
import by.task.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ProviderImpl<T> implements Provider<T> {

    private final Constructor<? extends T> constructor;
//    private final BeanConfig config;
    private final List<Provider<?>> argsProviders;

    public ProviderImpl(Constructor<? extends T> constructor, List<Provider<?>> argsProviders) {
        this.constructor = constructor;
        this.argsProviders = argsProviders;
    }

    @Override
    public T getInstance() {
        Object[] args = argsProviders.stream().map(Provider::getInstance).toArray();
        try {
            return constructor.newInstance(args);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new BeanCreationException(e);
        }
    }
}


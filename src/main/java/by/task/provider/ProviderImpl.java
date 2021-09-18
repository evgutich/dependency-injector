package by.task.provider;

import by.task.config.BeanConfig;
import by.task.exception.BeanCreationException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ProviderImpl<T> implements Provider<T> {

    private final BeanConfig<? extends T> config;
    private final List<Provider<?>> argsProviders;
    private T instance;

    public ProviderImpl(BeanConfig<? extends T> config, List<Provider<?>> argsProviders) {
        this.config = config;
        this.argsProviders = argsProviders;
    }

    @Override
    public T getInstance() {
        if (config.isSingleton()) {
            if (instance == null) {
                instance = createInstance();
            }
            return instance;
        }
        return createInstance();
    }

    private T createInstance() {
        Object[] args = argsProviders.stream().map(Provider::getInstance).toArray();
        try {
            return config.getConstructor().newInstance(args);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new BeanCreationException(e);
        }
    }
}


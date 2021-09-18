package by.task.injector;

import by.task.annotation.Inject;
import by.task.config.BeanConfig;
import by.task.exception.BindingNotFoundException;
import by.task.exception.ConstructorNotFoundException;
import by.task.exception.TooManyConstructorsException;
import by.task.provider.Provider;
import by.task.provider.ProviderImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class InjectorImpl implements Injector {

    Map<Class<?>, BeanConfig> bindingMap = new HashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        Objects.requireNonNull(type);
        if (bindingMap.get(type) == null) {
            return null;
        }
        Constructor<? extends T> constructor = (Constructor<? extends T>) bindingMap.get(type).getConstructor();
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Provider<?>> providers = Arrays.stream(parameterTypes).map(this::getProviderWithMandatoryBinding).collect(Collectors.toList());
        return new ProviderImpl<T>(constructor, providers);
    }

    private <R> Provider<R> getProviderWithMandatoryBinding(Class<R> type) {
        if (bindingMap.containsKey(type)) {
            return getProvider(type);
        } else {
            throw new BindingNotFoundException(type);
        }
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        validateInput(intf, impl);
        bindingMap.put(intf, new BeanConfig(getImplConstructor(impl), false));
    }

    private <T> Constructor<? extends T> getImplConstructor(Class<? extends T> impl) {
        List<Constructor<?>> constructorsWithInject = Arrays.stream(impl.getConstructors())
                .filter(a -> a.isAnnotationPresent(Inject.class))
                .collect(Collectors.toList());
        if (constructorsWithInject.size() > 1) {
            throw new TooManyConstructorsException(impl);
        }
        if (constructorsWithInject.size() == 0) {
            try {
                return impl.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new ConstructorNotFoundException(impl);
            }
        }
        return (Constructor<? extends T>) constructorsWithInject.get(0);
    }

    private <T> void validateInput(Class<T> intf, Class<? extends T> impl) {
        Objects.requireNonNull(intf);
        Objects.requireNonNull(impl);
        if (Modifier.isAbstract(impl.getModifiers()) || impl.isInterface()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        validateInput(intf, impl);
        bindingMap.put(intf, new BeanConfig(getImplConstructor(impl), true));
    }

}

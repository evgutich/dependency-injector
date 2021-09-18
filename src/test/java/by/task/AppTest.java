package by.task;

import by.task.dao.EventDAO;
import by.task.dao.InMemoryEventDAOImpl;
import by.task.exception.BindingNotFoundException;
import by.task.exception.ConstructorNotFoundException;
import by.task.exception.TooManyConstructorsException;
import by.task.injector.Injector;
import by.task.injector.InjectorImpl;
import by.task.provider.Provider;
import by.task.service.EventService;
import by.task.service.EventServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    void testExistingBinding() {
        Injector injector = new InjectorImpl(); //создаем имплементацию инжектора
        injector.bind(EventDAO.class, InMemoryEventDAOImpl.class); //добавляем в инжектор реализацию интерфейса
        Provider<EventDAO> daoProvider = injector.getProvider(EventDAO.class); //получаем инстанс класса из инжектора
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(InMemoryEventDAOImpl.class, daoProvider.getInstance().getClass());
    }

    @Test
    void testBindingWithInjection() {
        Injector injector = new InjectorImpl();
        injector.bind(EventDAO.class, InMemoryEventDAOImpl.class);
        injector.bind(EventService.class, EventServiceImpl.class);
        Provider<EventService> serviceProvider = injector.getProvider(EventService.class);
        assertNotNull(serviceProvider);
        assertNotNull(serviceProvider.getInstance());
        assertSame(EventServiceImpl.class, serviceProvider.getInstance().getClass());
        assertSame(InMemoryEventDAOImpl.class, serviceProvider.getInstance().getDAO().getClass());
    }

    @Test
    void testProviderIsNullWhenNoBinding() {
        Injector injector = new InjectorImpl();
        assertNull(injector.getProvider(EventService.class));
    }



    @Test
    void testProviderWithoutBindingForDependency() {
        Injector injector = new InjectorImpl();
        injector.bind(EventService.class, EventServiceImpl.class);
        assertThrows(BindingNotFoundException.class, () -> injector.getProvider(EventService.class));
    }

    @Test
    void testConstructorNotFound() {
        Injector injector = new InjectorImpl();
        assertThrows(ConstructorNotFoundException.class, () -> injector.bind(EventDAO.class, ClassWithoutConstructors.class));
    }

    @Test
    void testTooManyConstructors() {
        Injector injector = new InjectorImpl();
        assertThrows(TooManyConstructorsException.class, () -> injector.bind(EventDAO.class, ClassWithTooManyConstructors.class));
    }

    @Test
    void testValidateInputWithFirstParameterNull() {
        Injector injector = new InjectorImpl();
        assertThrows(NullPointerException.class, () -> injector.bind(null, InMemoryEventDAOImpl.class));
    }

    @Test
    void testValidateInputWithSecondParameterNull() {
        Injector injector = new InjectorImpl();
        assertThrows(NullPointerException.class, () -> injector.bind(EventDAO.class, null));
    }

    @Test
    void testValidateInputWithSecondParameterAbstract() {
        Injector injector = new InjectorImpl();
        assertThrows(IllegalArgumentException.class, () -> injector.bind(List.class, AbstractList.class));
    }

    @Test
    void testValidateInputWithSecondParameterInterface() {
        Injector injector = new InjectorImpl();
        assertThrows(IllegalArgumentException.class, () -> injector.bind(Set.class, SortedSet.class));
    }

}


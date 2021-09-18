package by.task;

import by.task.annotation.Inject;
import by.task.dao.EventDAO;

public class ClassWithTooManyConstructors implements EventDAO {
    @Inject
    public ClassWithTooManyConstructors() {
    }

    @Inject
    public ClassWithTooManyConstructors(String text) {
    }
}

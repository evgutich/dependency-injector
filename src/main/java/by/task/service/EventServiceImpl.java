package by.task.service;

import by.task.annotation.Inject;
import by.task.dao.EventDAO;

public class EventServiceImpl implements EventService {

    EventDAO eventDAO;

    @Inject
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public EventDAO getDAO() {
        return eventDAO;
    }
}

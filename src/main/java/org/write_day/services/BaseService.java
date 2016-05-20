package org.write_day.services;

import org.springframework.scheduling.annotation.Async;
import org.write_day.components.enums.CommandStatus;
import org.write_day.dao.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BaseService {

    @Autowired
    private BaseDAO baseDAO;

    public static final int max = 30;

    /** Поиск по ID */
    public <T> T findById(Class<T> type, final UUID id) {
        return baseDAO.findById(type, id);
    }

    public <T> List<T> getAll(Class<T> type) {
        return baseDAO.getAll(type);
    }

    public CommandStatus persist(Object object) {
        return baseDAO.persist(object);
    }

    @Async
    public void asyncPersist(Object object) {
        baseDAO.persist(object);
    }

    public UUID save(Object object) {
        return baseDAO.save(object);
    }

    public CommandStatus update(Object object) {
        return baseDAO.update(object);
    }

    public CommandStatus merge(Object object) {
        return baseDAO.merge(object);
    }

    public CommandStatus delete(Object object) {
        return baseDAO.delete(object);
    }

    public <T> List<T> findByCriteria(Class<T> type, String param, Object value, int first) {
        return baseDAO.findByCriteria(type, param, value, first, max);
    }

    public <T> List<T> findByCriteria(Class<T> type, String param, Object value, String param2, Object value2, int first) {
        return baseDAO.findByCriteria(type, param, value, param2, value2, first, max);
    }

    public <T> List<T> findByCriteria(Class<T> type, String param, Object value) {
        return baseDAO.findByCriteria(type, param, value);
    }

    public <T> List<T> findByCriteria(Class<T> type, String param, Object value, String param2, Object value2) {
        return baseDAO.findByCriteria(type, param, value, param2, value2);
    }

}

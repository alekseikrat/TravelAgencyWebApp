package ua.edu.znu.travelagencyweb.service;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Interface with universal methods that working with all entities
 * of TravelAgency database.
 */
public interface TravelAgencyDao<T> {
    void setClazz(Class<T> clazzToSet);
    T findById(final long id);
    List<T> findAll();
    T getSingleResult(TypedQuery<T> query);
    List<T> getResultList(TypedQuery<T> query);
    void create(final T entity);
    void update(final T entity);
    void delete(final T entity);
}
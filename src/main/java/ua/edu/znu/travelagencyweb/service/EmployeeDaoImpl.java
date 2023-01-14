package ua.edu.znu.travelagencyweb.service;

import ua.edu.znu.travelagencyweb.model.Client;
import ua.edu.znu.travelagencyweb.model.Tour;
import ua.edu.znu.travelagencyweb.model.Employee;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Specific Employee methods.
 */
public class EmployeeDaoImpl extends TravelAgencyDaoImpl<Employee> {
    public EmployeeDaoImpl() {
        setClazz(Employee.class);
    }
    public Employee findBySurname(final String surname) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Employee> query = entityManager
                .createQuery("from Employee e where e.surname=:surname", Employee.class)
                .setParameter("surname", surname);
        return getSingleResult(query);
    }
    public List<Employee> findByTour(final Tour tour) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Employee> query = entityManager
                .createQuery("from Employee e where :tour member of e.tours",
                        Employee.class).setParameter("tour", tour);
        return getResultList(query);
    }
}
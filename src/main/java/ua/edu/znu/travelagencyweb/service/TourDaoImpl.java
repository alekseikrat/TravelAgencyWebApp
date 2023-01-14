package ua.edu.znu.travelagencyweb.service;

import ua.edu.znu.travelagencyweb.model.Client;
import ua.edu.znu.travelagencyweb.model.Tour;
import ua.edu.znu.travelagencyweb.model.Employee;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

/**
 * Specific Tour methods.
 */
public class TourDaoImpl extends TravelAgencyDaoImpl<Tour> {
    public TourDaoImpl() {
        setClazz(Tour.class);
    }
    public Tour findByArrival(final String arrival) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Tour> query = entityManager
                .createQuery("from Tour t where t.arrival=:arrival", Tour.class)
                .setParameter("arrival", arrival);
        return getSingleResult(query);
    }
    public List<Tour> findByEmployee(final Employee employee) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Tour> query = entityManager
                .createQuery("from Tour t where :employee member of t.employees",
                        Tour.class).setParameter("employee", employee);
        return getResultList(query);
    }
    public void removeEmployeeFromTour(Long tourId, Employee employee) {
        try {
            Tour tour = findById(tourId);
            Set<Employee> employees = tour.getEmployees();
            employees.remove(employee);
            update(tour);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
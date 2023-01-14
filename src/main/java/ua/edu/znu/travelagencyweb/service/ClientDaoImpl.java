package ua.edu.znu.travelagencyweb.service;

import ua.edu.znu.travelagencyweb.model.Employee;
import ua.edu.znu.travelagencyweb.model.Tour;
import ua.edu.znu.travelagencyweb.model.Client;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

/**
 * Specific Client methods.
 */
public class ClientDaoImpl extends TravelAgencyDaoImpl<Client> {
    public ClientDaoImpl() {
        setClazz(Client.class);
    }
    public List<Client> findByTour(final Tour tour) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Client> query = entityManager
                .createQuery("from Client c where :tour member of c.tours",
                        Client.class).setParameter("tour", tour);
        return getResultList(query);
    }
    public Client findBySurname(final String surname) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Client> query = entityManager
                .createQuery("from Client c where c.surname=:surname", Client.class)
                .setParameter("surname", surname);
        return getSingleResult(query);
    }
    public void removeTourFromClient(Long clientId, Tour tour) {
        try {
            Client client = findById(clientId);
            Set<Tour> tours = client.getTours();
            tours.remove(tour);
            update(client);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
package ua.edu.znu.travelagencyweb.service;

import ua.edu.znu.travelagencyweb.model.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Specific User methods.
 */
public class UserDaoImpl extends TravelAgencyDaoImpl<User> {
    public UserDaoImpl() {
        setClazz(User.class);
    }
    public User findByUsername(final String username) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<User> query = entityManager
                .createQuery("from User u where u.username=:username",
                        User.class)
                .setParameter("username", username);
        return getSingleResult(query);
    }
    public boolean isAuthenticated(final String username, final String password)
    {
        User user = findByUsername(username);
        if(user == null){
            throw new NoResultException();
        }
        return password.equals(user.getPassword());
    }
}
package com.daitangroup;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    public User getByName(String name) {
        return (User) entityManager.createQuery("from User where name = :name")
                .setParameter("name", name)
                .getSingleResult();
    }

    public User getById(Integer id) {
        return entityManager.find(User.class, id);
    }

    public List getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Transactional
    public void delete(User user) {
        if (entityManager.contains(user)) {
            entityManager.remove(user);
        } else {
            entityManager.remove(entityManager.merge(user));
        }
    }

    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }
}

package com.example.Plowithme.Repository;

import com.example.Plowithme.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public user findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<user> findAll() {
        return em.createQuery("select m from user m", user.class)
                .getResultList();
    }

    public List<user> findByName(String name) {
        return em.createQuery("select m from user m where m.name = :name", user.class)
                .setParameter("name", name)
                .getResultList();
    }
}

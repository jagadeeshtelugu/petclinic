/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Login;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Users;
import org.springframework.samples.petclinic.repository.UsersRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sambitc
 */
@Repository
public class JpaUsersRepository implements UsersRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Users findLoginUser(Login login) throws DataAccessException {
        Query query = this.em.createQuery("SELECT users FROM Users users "
                + "WHERE users.username =:uname AND users.password =:pwd");
        query.setParameter("uname", login.getUsername());
        query.setParameter("pwd", login.getPassword());
        List results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        } else {
            return (Users) results.get(0);
        }
    }

    @Override
    public Users findUserByUsername(String userName) {
        Query query = this.em.createQuery("SELECT users FROM Users users WHERE users.username = :uName");
        query.setParameter("uName", userName);
        List results = query.getResultList();

        if (!results.isEmpty()) {
            return (Users) query.getResultList().get(0);
        } else {
            return null;
        }
    }

    @Override
    public void save(Users user) {
        if (user.getUserID() == 0) {
            this.em.persist(user);
        } else {
            this.em.merge(user);
        }
    }

}

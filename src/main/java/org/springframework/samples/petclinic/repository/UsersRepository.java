/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Login;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Users;

/**
 *
 * @author sambitc
 */
public interface UsersRepository {

    public Users findLoginUser(Login login) throws DataAccessException;

    public Users findUserByUsername(String userName);

    public void save(Users user);
}

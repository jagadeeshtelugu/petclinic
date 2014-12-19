/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Login;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerImage;
import org.springframework.samples.petclinic.model.OwnerLW;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Users;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;

/**
 * Mostly used as a facade for all Petclinic controllers
 *
 * @author Michael Isvy
 */
public interface ClinicService {

	Collection<PetType> findPetTypes() throws DataAccessException;

	Owner findOwnerById(int id) throws DataAccessException;

	Pet findPetById(int id) throws DataAccessException;

	Visit findVisitById(int id) throws DataAccessException;

	void savePet(Pet pet) throws DataAccessException;

	void saveVisit(Visit visit) throws DataAccessException;

	Collection<Vet> findVets() throws DataAccessException;

	void saveOwner(Owner owner) throws DataAccessException;

	Collection<Owner> findOwnerByLastName(String lastName)
			throws DataAccessException;

	// Collection<OwnerLW> findOwnerAll() throws DataAccessException;

	Collection<Owner> findOwnerAll() throws DataAccessException;

	public Users findUser(Login login);

	public Users findUserByUsername(String userName);

	public void saveUser(Users user);

	public void saveOwnerImage(OwnerImage oimage);

	public List<Owner> findByCriteriaQuery(Owner owner);

	public List<Specialty> findSpecialities();

	public Set<Specialty> findSpecialityById(String specialityID);

	public void saveVet(Vet vet);

	public Vet findVetByID(int id);

	public void deleteVet(int id);

	public void deleteVetAllSpecialty(int id);

	public void deleteVetSpecialtyById(int vetId, int specialtyId);

	public List<Specialty> findAllSpecialtyDetails();

}

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.SpecialitiesRepository;
import org.springframework.samples.petclinic.repository.UsersRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder for
 *
 * @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicServiceImpl implements ClinicService {

    private PetRepository petRepository;
    private VetRepository vetRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;
    private UsersRepository userRepository;
    private SpecialitiesRepository specialitiesRepository;

    @Autowired
    public ClinicServiceImpl(PetRepository petRepository, VetRepository vetRepository,
            OwnerRepository ownerRepository, VisitRepository visitRepository,
            UsersRepository userRepository, SpecialitiesRepository specialitiesRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
        this.specialitiesRepository = specialitiesRepository;

    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PetType> findPetTypes() throws DataAccessException {
        return petRepository.findPetTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) throws DataAccessException {
        return ownerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        return ownerRepository.findByLastName(lastName);
    }

//    public Collection<OwnerLW> findOwnerAll() throws DataAccessException {
//        return ownerRepository.findAll();
//    }
    @Override
    public Collection<Owner> findOwnerAll() throws DataAccessException {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional
    public void saveOwner(Owner owner) throws DataAccessException {
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        visitRepository.save(visit);
    }

    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(int id) throws DataAccessException {
        return petRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Visit findVisitById(int id) throws DataAccessException {
        return visitRepository.findById(id);
    }

    @Override
    @Transactional
    public void savePet(Pet pet) throws DataAccessException {
        petRepository.save(pet);
    }

    @Override
    @Transactional(readOnly = true)
    //@Cacheable(value = "vets")
    public Collection<Vet> findVets() throws DataAccessException {
        return vetRepository.findAll();
    }

    @Override
    @Transactional
    public Users findUser(Login login) {
        return userRepository.findLoginUser(login);
    }

    @Override
    @Transactional
    public Users findUserByUsername(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    @Override
    @Transactional
    public void saveUser(Users user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveOwnerImage(OwnerImage oimage) {
        ownerRepository.saveImage(oimage);
    }

	@Override
	@Transactional
	public List<Owner> findByCriteriaQuery(Owner owner) {
		
		return ownerRepository.findByCriteriaQuery(owner);
	}

	@Override
	@Transactional
	public List<Specialty> findSpecialities() {
		return specialitiesRepository.findAllSpecialities();
	}

	@Override
	@Transactional
	public Set<Specialty> findSpecialityById(String specialityID) {
		return specialitiesRepository.findAllSpecialityById(specialityID);
	}

	@Override
	@Transactional
	public void saveVet(Vet vet) {
		vetRepository.saveVet(vet);
	}

	@Override
	@Transactional
	public Vet findVetByID(int id) {
		
		return vetRepository.findByID(id);
	}

	@Override
	@Transactional
	public void deleteVet(int id) {
		
		vetRepository.deleteVet(id);
	}

	@Override
	@Transactional
	public void deleteVetSpecialtyReln(int id) {
		vetRepository.deleteVetSpecialtyReln(id);
	}

}

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
package org.springframework.samples.petclinic.repository.jpa;

import java.util.Collection;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the {@link VetRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @since 22.4.2006
 */
@Repository
public class JpaVetRepositoryImpl implements VetRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @SuppressWarnings("unchecked")
    public Collection<Vet> findAll() {
        return this.em.createQuery("SELECT distinct vet FROM Vet vet left join fetch vet.specialties ORDER BY vet.lastName, vet.firstName").getResultList();
    }


	@Override
	public void saveVet(Vet vet) {		
		
		if(vet.isNew()){
			this.em.persist(vet);
		}else{
			
			Vet vet1 = em.find(Vet.class, vet.getId());
			vet1.setFirstName(vet.getFirstName());
			vet1.setLastName(vet.getLastName());
			vet1.setSpecialties(vet.getSpecialties());
			
//			this.em.merge(vet);
		}
	}


	@Override
	public Vet findByID(int id) {
		return (Vet)this.em.find(Vet.class, id);
	
	}


	@Override
	public void deleteVetAllSpecialty(int id) {
		
		Vet vet1 = findByID(id);
		vet1.setSpecialties(null);
		
		// Remove child
		this.em.merge(vet1);
		
	}


	@Override
	public void deleteVet(int id) {
		
		Vet vet = em.find(Vet.class, id);
		this.em.remove(vet);
		
	}


	@Override
	public void deleteVetSpecialtyById(int vetId, int specialtyId) {	
		// NOt used.
	}
}

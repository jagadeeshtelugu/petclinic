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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerImage;
import org.springframework.samples.petclinic.model.OwnerLW;
import org.springframework.samples.petclinic.model.Owner_;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Pet_;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the {@link OwnerRepository} interface.
 *
 * @author Mike Keith
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @since 22.4.2006
 */
@Repository
public class JpaOwnerRepositoryImpl implements OwnerRepository {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Important: in the current version of this method, we load Owners with all
	 * their Pets and Visits while we do not need Visits at all and we only need
	 * one property from the Pet objects (the 'name' property). There are some
	 * ways to improve it such as: - creating a Ligtweight class (example here:
	 * https://community.jboss.org/wiki/LightweightClass) - Turning on
	 * lazy-loading and using {@link OpenSessionInViewFilter}
	 */
	@SuppressWarnings("unchecked")
	public Collection<Owner> findByLastName(String lastName) {
		// using 'join fetch' because a single query should load both owners and
		// pets
		// using 'left join fetch' because it might happen that an owner does
		// not have pets yet
		Query query = this.em
				.createQuery("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName");
		query.setParameter("lastName", lastName + "%");
		return query.getResultList();
	}

	// @SuppressWarnings("unchecked")
	// public Collection<OwnerLW> findAll() {
	// // using 'join fetch' because a single query should load both owners and
	// pets
	// // using 'left join fetch' because it might happen that an owner does not
	// have pets yet
	// Query query =
	// this.em.createQuery("SELECT DISTINCT ownerLW FROM OwnerLW ownerLW left join fetch ownerLW.petsLW ");
	// return query.getResultList();
	// }

	@Override
	public Collection<Owner> findAll() {
		// using 'join fetch' because a single query should load both owners and
		// pets
		// using 'left join fetch' because it might happen that an owner does
		// not have pets yet
		Query query = this.em
				.createQuery("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets left join"
						+ " fetch owner.ownerImage ");
		return query.getResultList();
	}

	@Override
	public Owner findById(int id) {
		// using 'join fetch' because a single query should load both owners and
		// pets
		// using 'left join fetch' because it might happen that an owner does
		// not have pets yet
		Query query = this.em
				.createQuery("SELECT owner FROM Owner owner left join fetch owner.pets left join fetch owner.ownerImage WHERE owner.id =:id");
		query.setParameter("id", id);
		return (Owner) query.getSingleResult();
	}

	@Override
	public void save(Owner owner) {
		if (owner.getId() == null) {
			this.em.persist(owner);
		} else {
			this.em.merge(owner);
		}

	}

	@Override
	public void saveImage(OwnerImage oimage) {

		if (oimage.getId() == null) {
			this.em.persist(oimage);
		} else {
			this.em.merge(oimage);
		}
	}

	@Override
	public List<Owner> findByCriteriaQuery(Owner owner) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Owner> criteriaQuery = cb.createQuery(Owner.class);

		Root<Owner> ownerRoot = criteriaQuery.from(Owner.class);

		// Pet
		ownerRoot.fetch(Owner_.pets, JoinType.LEFT);
		Join<Owner, Pet> petJoin = ownerRoot.join(Owner_.pets, JoinType.LEFT);

		// Owner image.
		ownerRoot.fetch(Owner_.ownerImage, JoinType.LEFT);

		criteriaQuery.select(ownerRoot).distinct(true);

		Predicate criteria = cb.conjunction();

		// First Name
		String firstName = owner.getFirstName();
		if (firstName != null && !firstName.isEmpty()) {
			Predicate p = cb.equal(ownerRoot.get(Owner_.firstName), firstName);
			criteria = cb.and(criteria, p);
		}

		// Last Name
		String lastName = owner.getLastName();
		if (lastName != null && !lastName.isEmpty()) {
			Predicate p = cb.equal(ownerRoot.get(Owner_.lastName), lastName);
			criteria = cb.and(criteria, p);
		}

		if (owner.getPets() != null) {
			Pet pet = owner.getPets().get(0);
			String petName = pet.getName();
			DateTime fromBirthDate = pet.getFromBirthDate();
			DateTime toBirthDate = pet.getToBirthDate();

			// Pet name
			if (petName != null && !petName.isEmpty()) {
				Predicate p = cb.equal(petJoin.get(Pet_.name), petName);
				criteria = cb.and(criteria, p);
			}

			// From birth date and to birth date.
			if (fromBirthDate != null && toBirthDate != null) {				
				Predicate p = cb.between(petJoin.get(Pet_.birthDate), fromBirthDate, toBirthDate);
				criteria = cb.and(criteria, p);
			}
		}

		criteriaQuery.where(criteria);

		List<Owner> result = new ArrayList<Owner>();
		result = em.createQuery(criteriaQuery).getResultList();
		return result;
	}

}

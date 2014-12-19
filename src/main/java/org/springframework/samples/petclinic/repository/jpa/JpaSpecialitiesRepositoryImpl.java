package org.springframework.samples.petclinic.repository.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.repository.SpecialitiesRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaSpecialitiesRepositoryImpl implements SpecialitiesRepository {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Specialty> findAllSpecialities() {
		return this.em.createQuery(
				"SELECT specialty FROM Specialty specialty ")
				.getResultList();
	}

	@Override
	public Set<Specialty> findAllSpecialityById(String specialityID) {
		String[] idS = specialityID.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		
		for (String id : idS){
			idList.add(Integer.parseInt(id));
		}
		
		Query query = null;
		try{
		query = this.em
				.createQuery("SELECT specialty FROM Specialty specialty WHERE specialty.id IN :id");
		query.setParameter("id", idList);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return  new HashSet<Specialty>(query.getResultList());
	}

	@Override
	public List<Specialty> findAllSpecialtyDetails() {
		Query query = em.createQuery("SELECT specialty FROM Specialty specialty");
		return query.getResultList();
	}

}

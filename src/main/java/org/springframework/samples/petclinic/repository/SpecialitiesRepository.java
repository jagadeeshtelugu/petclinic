package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.samples.petclinic.model.Specialty;

public interface SpecialitiesRepository {

	List<Specialty> findAllSpecialities();

	Set<Specialty> findAllSpecialityById(String specialityID);

}

/*
 * 
 */
package org.springframework.samples.petclinic.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.ClinicService;

/**
 * @sambitc
 */
public class PetTypeConverter implements Converter<String, PetType> {

	private final ClinicService clinicService;

	@Autowired
	public PetTypeConverter(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@Override
	public PetType convert(String text) {

		Collection<PetType> findPetTypes = this.clinicService.findPetTypes();
		for (PetType type : findPetTypes) {
			if (type.getName().equals(text)) {
				return type;
			}
		}
		return null;
	}
}

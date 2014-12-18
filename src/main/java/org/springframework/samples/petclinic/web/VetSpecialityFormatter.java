package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.ClinicService;

public class VetSpecialityFormatter implements Formatter<Set<Specialty>> {

	private final ClinicService clinicService;

	@Autowired
	public VetSpecialityFormatter(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@Override
	public String print(Set<Specialty> object, Locale locale) {
		// TODO Auto-generated method stub
		return "=========inside pet formatter";
	}

	@Override
	public Set<Specialty> parse(String specialityIDsArr, Locale locale)
			throws ParseException {
		Set<Specialty> specialities = new HashSet<Specialty>();
		
		if(specialityIDsArr != null && !specialityIDsArr.isEmpty()){
			String[] specialityIDs = specialityIDsArr.split(",");
			
			specialities = clinicService.findSpecialityById(specialityIDsArr);
		}

		System.out.println("-----------------" + specialityIDsArr);
		return specialities;
	}

}

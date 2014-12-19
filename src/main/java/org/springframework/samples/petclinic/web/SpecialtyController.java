package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/specialty")
public class SpecialtyController {
	
	@Autowired
	ClinicService clinicService;
	
	@RequestMapping(value="/findAll", method=RequestMethod.GET)
	public String findAllSpecialty(Map<String, Object> model){
		
		List<Specialty> allSpecialty = clinicService.findAllSpecialtyDetails();
		
		if(allSpecialty != null && allSpecialty.size() >0){
			model.put("allSpecialty", allSpecialty);
		}
		
		return "specialty/specialtyList";
	}
}

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
package org.springframework.samples.petclinic.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private final ClinicService clinicService;

	@Autowired
	public VetController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@ModelAttribute(value="specialities")
	public List<Specialty> getSpeciality(){
		return this.clinicService.findSpecialities();
	}

	@RequestMapping("/vets")
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a
		// collection of Vet objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		
		Collection<Vet> listVets = new ArrayList<Vet>();
		listVets = this.clinicService.findVets();
		
		vets.getVetList().addAll(listVets);
		model.put("vets", vets);
		return "vets/vetList";
	}

	@RequestMapping(value = "/vets/new", method = RequestMethod.GET)
	public String vetForm(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);

		return "vets/createOrUpdateVetForm";
	}
	
	@RequestMapping(value = "/vets/new", method = RequestMethod.POST)
	public String vetSave(@Valid Vet vet, BindingResult result, SessionStatus status) {
		
		if(result.hasErrors()){
			return "vets/createOrUpdateVetForm";
		}
		clinicService.saveVet(vet);
		status.setComplete();
		return "redirect:/vets.html";
	}
	
    @RequestMapping(value = "/vet/{id}/delete", method = {RequestMethod.GET})
    public String deleteVet(@PathVariable("id") int id, SessionStatus status) {
    	
    	clinicService.deleteVetAllSpecialty(id);

    	clinicService.deleteVet(id);
    	
    	status.setComplete();
    	
    	return "redirect:/vets.html";
    }
    
    @RequestMapping(value = "/vet/{id}/edit", method = {RequestMethod.GET})
    public String editVetForm(@PathVariable("id") int id, Map<String, Object> model) {
    	
    	Vet vet = clinicService.findVetByID(id);
    	model.put("vet", vet);
    	
    	return "vets/createOrUpdateVetForm";
    }
    
	@RequestMapping(value = "/vet/{id}/edit", method = RequestMethod.POST)
	public String editVet(@Valid Vet vet, BindingResult result, SessionStatus status) {
		
		if(result.hasErrors()){
			return "vets/createOrUpdateVetForm";
		}
		clinicService.saveVet(vet);
		status.setComplete();
		return "redirect:/vets.html";
	}
	
    @RequestMapping(value = "/vet/specialty/{vetId}/{specialtyId}/delete", method = {RequestMethod.GET})
    public String deleteVetSpecialtyByID(@PathVariable("vetId") int vetId,
    		@PathVariable("specialtyId") int specialtyId, SessionStatus status) {
    	
    	Vet vet = clinicService.findVetByID(vetId);
    	Set<Specialty> specialties = vet.getSpecialties();
    	
    	for(Specialty specialty : specialties){
    		if(specialtyId == specialty.getId()){
    			specialties.remove(specialty);
    		}
    	}
    	
    	vet.setSpecialties(specialties);
    	clinicService.saveVet(vet);
    	
    	status.setComplete();
    	
    	return "redirect:/vets.html";
    }

}

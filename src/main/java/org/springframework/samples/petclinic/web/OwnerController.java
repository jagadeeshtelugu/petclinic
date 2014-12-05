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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerImage;
import org.springframework.samples.petclinic.model.OwnerLW;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.UploadImage;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes(types = Owner.class)
public class OwnerController {

	private final ClinicService clinicService;
	private final UploadUtil uploadUtil;

	@Autowired
	public OwnerController(ClinicService clinicService, UploadUtil uploadUtil) {
		this.clinicService = clinicService;
		this.uploadUtil = uploadUtil;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/owners/new", method = RequestMethod.GET)
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return "owners/createOrUpdateOwnerForm";
	}

	@RequestMapping(value = "/owners/new", method = RequestMethod.POST)
	public String processCreationForm(@Valid Owner owner, BindingResult result,
			SessionStatus status, HttpServletRequest request)
			throws IOException {

		String imgRoot = request.getSession().getServletContext()
				.getRealPath("");
		imgRoot = imgRoot + File.separator + "resources" + File.separator
				+ "ownerImages" + File.separator;
		System.out.println("root-------" + imgRoot);

		String imageName = null;

		if (result.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {
			// this.clinicService.saveOwner(owner);

			if (owner.getImage() != null && owner.getImage().getBytes() != null
					&& owner.getImage().getBytes().length != 0) {
				this.uploadUtil.uploadImage(owner.getImage().getBytes(),
						imgRoot + owner.getImage().getOriginalFilename());
				imageName = owner.getImage().getOriginalFilename();
			} else {
				imageName = "default.png";
			}

			OwnerImage oimage = new OwnerImage();
			oimage.setImageName(imageName);
			oimage.setOwner(owner);

			// this.clinicService.saveOwnerImage(oimage);

			// add image another way.
			owner.addOwnerImage(oimage);
			this.clinicService.saveOwner(owner);

			status.setComplete();
			return "redirect:/owners/" + owner.getId();
		}
	}

	@RequestMapping(value = "/owners/find", method = RequestMethod.GET)
	public String initFindForm(Map<String, Object> model) {
		Owner owner = new Owner();
		owner.addPet(new Pet());
		model.put("owner", owner);
		return "owners/findOwners";
	}

	@RequestMapping(value = "/owners", method = RequestMethod.GET)
	public String processFindForm(Owner owner, BindingResult result,
			Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible
									// search
		}

		// find owners by last name
		Collection<Owner> results = this.clinicService.findOwnerAll();
		if (results.size() < 1) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		}
		if (results.size() > 1) {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersList";
		} else {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}
	}

	@RequestMapping(value = "/owners/ownersByCriteria", method = RequestMethod.GET)
	public String processFindFormBycriteria(Owner owner, BindingResult result,
			Map<String, Object> model) {

		model.put("selections", this.clinicService.findByCriteriaQuery(owner));

		return "owners/ownersList";
	}

	@RequestMapping(value = "/owners/{ownerId}/edit", method = RequestMethod.GET)
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId,
			Model model) {
		Owner owner = this.clinicService.findOwnerById(ownerId);
		model.addAttribute(owner);
		return "owners/createOrUpdateOwnerForm";
	}

	@RequestMapping(value = "/owners/{ownerId}/edit", method = RequestMethod.POST)
	public String processUpdateOwnerForm(@Valid Owner owner,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) throws IOException {

		String imgRoot = request.getSession().getServletContext()
				.getRealPath("");
		imgRoot = imgRoot + File.separator + "resources" + File.separator
				+ "ownerImages" + File.separator;

		if (result.hasErrors()) {
			return "owners/createOrUpdateOwnerForm";
		} else {

			if (owner.getImage() != null && owner.getImage().getBytes() != null
					&& owner.getImage().getBytes().length != 0) {

				this.uploadUtil.uploadImage(owner.getImage().getBytes(),
						imgRoot + owner.getImage().getOriginalFilename());
				String imageName = owner.getImage().getOriginalFilename();

				OwnerImage oimage = new OwnerImage();
				oimage.setImageName(imageName);
				oimage.setOwner(owner);
				owner.getOwnerImage().add(oimage);
			}

			this.clinicService.saveOwner(owner);
			status.setComplete();
			return "redirect:/owners/{ownerId}";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param ownerId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Owner owner = this.clinicService.findOwnerById(ownerId);
		System.out.println("image sizeeeeeeeee--------"
				+ owner.getOwnerImage().size());
		mav.addObject(owner);
		return mav;
	}

	/**
	 * Remove filter
	 * 
	 * @param filter
	 * @return
	 */
	@RequestMapping("/owners/removeFilter/{filter}")
	public String removeFilter(@PathVariable("filter") String filter,
			HttpSession session) {
		
		Owner owner = (Owner) session.getAttribute("owner");
		Pet pet = owner.getPets().get(0);

		// First name.
		if ("firstName".equals(filter)) {
			owner.setFirstName("");
		}
		
		// Last name
		if ("lastName".equals(filter)) {
			owner.setLastName("");
		}
		
		// Pet name
		if ("petName".equals(filter)) {
			pet.setName("");
		}
		
		// Birth date
		if ("birthDate".equals(filter)) {
			pet.setFromBirthDate(null);
			pet.setToBirthDate(null);
		}

		return "redirect:/owners/ownersByCriteria";
	}

}

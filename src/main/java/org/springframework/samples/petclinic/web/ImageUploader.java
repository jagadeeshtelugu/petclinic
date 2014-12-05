package org.springframework.samples.petclinic.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerImage;
import org.springframework.samples.petclinic.model.UploadImage;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImageUploader {

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private UploadUtil uploadUtil;

	@RequestMapping(value = "/processUploadImage", method = RequestMethod.POST)
	public String uploadImage(UploadImage uploadImage,
			@RequestParam("file") Part file, HttpServletRequest request) throws IOException {

		if (file.getSize() > 0) {
			
			String imgFileName = getFileName(file);

			String imgRoot = request.getSession().getServletContext()
					.getRealPath("");
			imgRoot = imgRoot + File.separator + "resources" + File.separator
					+ "ownerImages" + File.separator;

			//
			uploadUtil.uploadImage(
					IOUtils.toByteArray(file.getInputStream()),
					imgRoot + imgFileName);

			Owner owner = clinicService.findOwnerById(uploadImage.getOwnerID());

			OwnerImage ownerImage = new OwnerImage();
			ownerImage.setOwner(owner);
			ownerImage.setImageName(imgFileName);

			owner.addOwnerImage(ownerImage);
			
			clinicService.saveOwner(owner);

		}
		
		return "redirect:/owners";
	}
	
	private String getFileName(Part part) {
		String partHeader = part.getHeader("content-disposition");
		System.out.println(partHeader);
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
}

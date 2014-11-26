/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.web.validator.multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.samples.petclinic.web.validator.*;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class MultipartValidator implements ConstraintValidator<Multipart, MultipartFile> {

    Multipart multipart;
    
    @Override
    public void initialize(Multipart multipart) {
        this.multipart = multipart;
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext ctx) {
        String[] allowedFileTypes = multipart.fileTypes();
        byte[] fileByte = null;
        
        if(multipartFile == null){
            return false;
        }else {
            try {
                String fileExt = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

                // Check file extension allowed or not.
                if(! Arrays.asList(allowedFileTypes).contains(fileExt)){
                    return false;
                }
                fileByte = multipartFile.getBytes();
                
                // Check file size.
                if(fileByte.length == 0){
                    return false;
                }
                return true;
            } catch (IOException ex) {
                Logger.getLogger(MultipartValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}

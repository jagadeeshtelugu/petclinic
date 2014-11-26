/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.web.validator;

import org.springframework.samples.petclinic.model.Users;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Users users = (Users) target;

        String pwd = users.getPassword();
        String cofirmPwd = users.getConfirmPassword();

        if (pwd != null && cofirmPwd != null && !pwd.isEmpty()
                && !cofirmPwd.isEmpty()) {
            if (!pwd.equals(cofirmPwd)) {
                errors.rejectValue("confirmPassword", "password.notMatched");
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.web;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.samples.petclinic.model.Login;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Users;
import org.springframework.samples.petclinic.web.validator.UserValidator;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author sambitc
 */
@Controller
public class UserController {

    @Autowired
    private UserValidator userValidator;

    /* It will override annotation dirven validation.
     If you want to use both type of validaton then 
     call it manuallly (for ex : userValidator.validate(users, result))
     */
//    @InitBinder
//    private void initBinder(WebDataBinder binder) {
//        binder.setValidator(userValidator);
//    }
    private final ClinicService clinicService;

    @Autowired
    public UserController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("user_id");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView initUserLogin() {
//        Users users = new Users();
//        model.put("users", users);
        Login login = new Login();

        ModelAndView mav = new ModelAndView();
        mav.addObject("login", login);
        mav.setViewName("welcome");

        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ModelAndView processUserLoginForm(HttpServletRequest request,
            @Valid Login login, BindingResult result,
            SessionStatus status) {

        ModelAndView mav = new ModelAndView();
        
        Users users = null;

        if (result.hasErrors()) {
            mav.setViewName("welcome");
            return mav;
        } else {
            users = this.clinicService.findUser(login);
            
            if (users == null) {
                mav.addObject("invalidUser", "Invalid User");
                mav.setViewName("welcome");
                return mav;
            } else {
                request.getSession().setAttribute("users", users);
                status.setComplete();
                mav.setViewName("redirect:/owners/find");
                return mav;
            }
        }
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.GET)
    public ModelAndView initCreationForm() {

        Users user = new Users();
//        model.put("users", users);

        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);
        mav.setViewName("users/createOrUpdateUserForm");

        return mav;
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.POST)
    public ModelAndView processCreationForm(@Valid @ModelAttribute("user") Users user,
            BindingResult result, SessionStatus status) {

        // Custom validation.
        userValidator.validate(user, result);

        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);

        if (result.hasErrors()) {
            mav.setViewName("users/createOrUpdateUserForm");
            return mav;
        } else {

            // Find user by username.
            Users exitUser = this.clinicService.findUserByUsername(user.getUsername());
            if (exitUser != null) {
                mav.setViewName("users/createOrUpdateUserForm");
                mav.addObject("userExit", "exit.user");
                return mav;

            } else {
                this.clinicService.saveUser(user);
                status.setComplete();
                mav.setViewName("redirect:/");
                return mav;
            }
        }
    }
}

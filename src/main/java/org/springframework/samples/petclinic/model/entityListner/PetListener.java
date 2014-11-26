/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.model.entityListner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.joda.time.DateTime;
import org.springframework.samples.petclinic.model.Pet;

/**
 *
 * @author sambitc
 */
public class PetListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateAge(Pet pet) {

        DateTime dob = pet.getBirthDate();
        if (dob == null) {
            return;
        }

        Calendar birth = new GregorianCalendar();
        birth.setTimeInMillis(dob.getMillis());

        Calendar now = new GregorianCalendar();
        now.setTimeInMillis(new DateTime().getMillis());

        int diff = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        if (diff < 0) {
           return;
        } else {
            pet.setAge(diff);
        }
    }
}

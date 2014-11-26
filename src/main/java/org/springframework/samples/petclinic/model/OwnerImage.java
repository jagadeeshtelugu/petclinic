/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author sambitc
 */
@Entity
@Table(name = "owner_image")
public class OwnerImage extends BaseEntity {

    @Column(name = "image_name")
    @NotEmpty
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    
    
    

}

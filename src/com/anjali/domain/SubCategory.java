/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ramesh
 */
@Entity
@XmlRootElement
public class SubCategory implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7568057069336521859L;
	@Id
    @GeneratedValue
    private int id;
    private String name;
    private boolean active;
    @OneToOne
    private SubCategoryDetails subCategoryDetails;
    @ManyToOne
    private Category category;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubCategoryDetails getSubCategoryDetails() {
        return subCategoryDetails;
    }

    public void setSubCategoryDetails(SubCategoryDetails subCategoryDetails) {
        this.subCategoryDetails = subCategoryDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubCategory other = (SubCategory) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
        return hash;
    }

    @Override
    public String toString() {
        return "SubCategory{" + "id=" + id + ", name=" + name + ", active=" + active + ", subCategoryDetails=" + subCategoryDetails + ", category=" + category + '}';
    }

    
}

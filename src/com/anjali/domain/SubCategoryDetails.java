/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ramesh
 */
@Entity
@XmlRootElement
public class SubCategoryDetails implements Serializable{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 2135177114804058859L;
	@Id
    @GeneratedValue
    private int id;
    private String about;
    @Lob
    @Column(nullable=true, length=2097152)
    private byte[] image;
    @OneToOne
    private SubCategory subCategory;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubCategoryDetails other = (SubCategoryDetails) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public String toString() {
        return "SubCategoryDetails{" + "id=" + id + ", about=" + about + ", image=" + image + '}';
    }
    
    
    
}

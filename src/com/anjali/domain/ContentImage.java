/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 
 * @author shekharkumar
 */
@Entity
@XmlRootElement
public class ContentImage implements Serializable {

    /**
     *  
     */
    private static final long serialVersionUID = -1078742792049596448L;
    @Id
    @GeneratedValue
    private long id;
    @Lob
    @Column(columnDefinition = "mediumblob", length = 20971520)
    @JsonIgnore
    private byte[] image;
    private String description;
    private long contentId;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public byte[] getImage() {
	return image;
    }

    public void setImage(byte[] image) {
	this.image = image;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ContentImage other = (ContentImage) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ContentImage [id=" + id + ", description=" + description
		+ ", contentId=" + contentId + "]";
    }


}

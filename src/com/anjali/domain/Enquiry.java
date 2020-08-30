/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shekharkumar
 */
@Entity
@XmlRootElement
public class Enquiry implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8035476837347475860L;
	@Id
    @GeneratedValue
    private long id;
    private String consumerQuestion;
    private String email;
    private String mobileNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne
    private Login enquiredBy;
    @ManyToOne
    private Content content;
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConsumerQuestion() {
        return consumerQuestion;
    }

    public void setConsumerQuestion(String consumerQuestion) {
        this.consumerQuestion = consumerQuestion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Login getEnquiredBy() {
        return enquiredBy;
    }

    public void setEnquiredBy(Login enquiredBy) {
        this.enquiredBy = enquiredBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Enquiry other = (Enquiry) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}

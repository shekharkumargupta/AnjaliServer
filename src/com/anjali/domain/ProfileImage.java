package com.anjali.domain;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ProfileImage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7045998314794880387L;
    @Id
    @GeneratedValue
    private long id;
    @Lob
    @Column(columnDefinition = "blob", length = 20971520)
    private byte[] image;
    @OneToOne
    private Person person;

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

    public Person getPerson() {
	return person;
    }

    public void setPerson(Person person) {
	this.person = person;
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
	ProfileImage other = (ProfileImage) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ProfileImage [id=" + id + ", image=" + Arrays.toString(image)
		+ ", person=" + person + "]";
    }

}

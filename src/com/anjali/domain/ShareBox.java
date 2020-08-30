package com.anjali.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ShareBox implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7061085657721222600L;

	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private Content content;
	@OneToOne
	private Login sharedBy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Login getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(Login sharedBy) {
		this.sharedBy = sharedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
		ShareBox other = (ShareBox) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ShareBox [id=" + id + ", content=" + content + ", sharedBy=" + sharedBy + ", createdAt=" + createdAt
				+ "]";
	}

	
}

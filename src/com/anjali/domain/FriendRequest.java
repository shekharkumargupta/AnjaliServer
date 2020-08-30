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

@Entity
@XmlRootElement
public class FriendRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private Login requestFrom;
	@ManyToOne
	private Login requestTo;
	private int status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Login getRequestFrom() {
		return requestFrom;
	}

	public void setRequestFrom(Login requestFrom) {
		this.requestFrom = requestFrom;
	}

	public Login getRequestTo() {
		return requestTo;
	}

	public void setRequestTo(Login requestTo) {
		this.requestTo = requestTo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getRequestedAt() {
		return requestedAt;
	}

	public void setRequestedAt(Date requestedAt) {
		this.requestedAt = requestedAt;
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
		FriendRequest other = (FriendRequest) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FriendRequest [id=" + id + ", requestFrom=" + requestFrom + ", requestTo=" + requestTo + ", status="
				+ status + ", requestedAt=" + requestedAt + "]";
	}

}

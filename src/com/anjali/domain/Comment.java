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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Ramesh
 */
@Entity
@XmlRootElement
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7465395932096491482L;
	@Id
	@GeneratedValue
	private long id;
	private String text;
	private byte[] media;
	@OneToOne
	private Login commentedBy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentedAt;
	private int likeCounter;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getMedia() {
		return media;
	}

	public void setMedia(byte[] media) {
		this.media = media;
	}

	public Login getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(Login commentedBy) {
		this.commentedBy = commentedBy;
	}

	public Date getCommentedAt() {
		return commentedAt;
	}

	public void setCommentedAt(Date commentedAt) {
		this.commentedAt = commentedAt;
	}

	public int getLikeCounter() {
		return likeCounter;
	}

	public void setLikeCounter(int likeCounter) {
		this.likeCounter = likeCounter;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
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
		final Comment other = (Comment) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Comment{" + "id=" + id + ", text=" + text + ", media=" + media + ", commentedBy=" + commentedBy
				+ ", commentedAt=" + commentedAt + ", likeCounter=" + likeCounter + '}';
	}

}

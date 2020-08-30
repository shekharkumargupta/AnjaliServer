/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author shekharkumar
 */
@Entity
@XmlRootElement
public class Content implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4537235842501798831L;
	@Id
	@GeneratedValue
	private long id;
	private String heading;
	@Column(length = 4000)
	private String meterial;

	@ManyToOne
	private Category category;
	@ManyToOne
	private SubCategory subCategory;
	@ManyToOne
	private Login createdBy;

	private String categoryName;
	private String subCategoryName;
	private String country;
	private String city;
	private String searchKeys;

	@OneToMany(fetch = FetchType.LAZY)
	private List<ContentImage> images;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Video> videos;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Login> likes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	private boolean active;
	private boolean containsImage;
	
	@Transient
	private Login sharedBy;
	@Transient
	private List<Login> sharerList;

	
	public void addImage(ContentImage image){
		images.add(image);
	}
	
	public void addLike(Login login){
		likes.add(login);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Login getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Login createdBy) {
		this.createdBy = createdBy;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getSearchKeys() {
		return searchKeys;
	}

	public void setSearchKeys(String searchKeys) {
		this.searchKeys = searchKeys;
	}

	public String getMeterial() {
		return meterial;
	}

	public void setMeterial(String meterial) {
		this.meterial = meterial;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public boolean isContainsImage() {
		return containsImage;
	}

	public void setContainsImage(boolean containsImage) {
		this.containsImage = containsImage;
	}

	public List<ContentImage> getImages() {
		return images;
	}

	public void setImages(List<ContentImage> images) {
		this.images = images;
	}

	public List<Login> getLikes() {
		return likes;
	}

	public void setLikes(List<Login> likes) {
		this.likes = likes;
	}

	public Login getSharedBy() {
		return sharedBy;
	}

	public void setSharedBy(Login sharedBy) {
		this.sharedBy = sharedBy;
	}
	
	public List<Login> getSharerList() {
	    return sharerList;
	}

	public void setSharerList(List<Login> sharerList) {
	    this.sharerList = sharerList;
	}

	@Override
	public int hashCode() {
		int hash = 5;
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
		final Content other = (Content) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 
 * @author Ramesh
 */
@Entity
@XmlRootElement
public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2037554227962645752L;
	@Id
	@GeneratedValue
	private long id;
	private String loginId;
	private String password;
	private boolean active;
	private int profilePrivacyPolicy;
	@OneToOne
	private UserType userType;
	@OneToOne(cascade = CascadeType.ALL)
	private Person person;
	@JsonIgnore
	@ManyToMany
	private List<Login> friends;
	@Transient
	private List<Message> messages;

	@Transient
	private boolean friend;
	@Transient
	private boolean requested;

	public void addFriend(Login login) {
		friends.add(login);
	}
	
	public void addMessage(Message message){
		messages.add(message);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public int getProfilePrivacyPolicy() {
		return profilePrivacyPolicy;
	}

	public void setProfilePrivacyPolicy(int profilePrivacyPolicy) {
		this.profilePrivacyPolicy = profilePrivacyPolicy;
	}

	public List<Login> getFriends() {
		return friends;
	}

	public void setFriends(List<Login> friends) {
		this.friends = friends;
	}

	public boolean isFriend() {
		return friend;
	}

	public void setFriend(boolean friend) {
		this.friend = friend;
	}

	public boolean isRequested() {
		return requested;
	}

	public void setRequested(boolean requested) {
		this.requested = requested;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Login other = (Login) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
		return hash;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", loginId=" + loginId + ", password=" + password + ", active=" + active
				+ ", profilePrivacyPolicy=" + profilePrivacyPolicy + ", userType=" + userType + ", person=" + person
				+ "]";
	}

}

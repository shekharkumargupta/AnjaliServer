/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.anjali.constants.ApplicationConstants;
import com.anjali.constants.SessionConstants;
import com.anjali.dao.LoginDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.LoginDAOImpl;
import com.anjali.domain.Login;
import com.anjali.domain.Person;
import com.anjali.services.wsserver.ChatServerEndpoint;

/**
 * 
 * @author Ramesh
 */
@Path(value = "login")
public class LoginResource {

    private final LoginDAO loginDAO;

    /**
	 * 
	 */
    public LoginResource() {
	loginDAO = new LoginDAOImpl();
    }

    /**
     * 
     * @param person
     * @return
     */
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Login create(Person person) {
	Login loginCreated = null;
	try {
	    Login login = new Login();
	    login.setLoginId(person.getEmail());
	    login.setPassword(ApplicationConstants.DEFAULT_PASSWORD);
	    login.setPerson(person);
	    loginCreated = loginDAO.create(login);
	    /*
	     * Uncomment this line for sending the email to user on User
	     * Registration
	     */
	    // EmailServices.sendWelcomeMessage(login.getLoginId());
	} catch (DBException ex) {
	    Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return loginCreated;
    }

    /**
     * 
     * @param person
     * @return
     */
    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Login update(Person person) {
	System.out.println("Update: " + person);
	Login login = null;
	try {
	    login = loginDAO.findByLoginId(person.getEmail());
	    login.getPerson().setCity(person.getCity());
	    login.getPerson().setProfession(person.getProfession());
	    login.getPerson().setEmail(person.getEmail());
	    login.getPerson().setMobileNumber(person.getMobileNumber());
	    login.getPerson().setJobProfile(person.getJobProfile());
	    login.getPerson().setKeySkills(person.getKeySkills());

	    login = loginDAO.update(login);
	} catch (DBException ex) {
	    Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return login;
    }

    /**
     * 
     * @param login
     * @return
     */
    @POST
    @Path("verify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Login verify(Login login) {
	System.out.println("Request: " + login);
	try {
	    login = loginDAO
		    .verifyUser(login.getLoginId(), login.getPassword());
	    System.out.println("Database: " + login);
	    if (login == null) {
		login = new Login();
	    }
	} catch (DBException ex) {
	    Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return login;
    }

    /**
     * 
     * @param loginId
     * @return
     */
    @GET
    @Path("isOnline/{loginId}")
    @Produces(value = "application/json")
    public boolean isOnline(@PathParam(value = "loginId") String loginId) {
	return ChatServerEndpoint.getOnlineUsers().contains(loginId);
    }

    /**
     * 
     * @param loginId
     * @return
     */
    @GET
    @Path("friendList/{loginId}")
    @Produces(value = "application/json")
    public List<Login> friendList(@PathParam(value = "loginId") String loginId) {
	List<Login> friendList = null;
	try {
	    friendList = loginDAO.friendList(loginId);
	} catch (DBException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return friendList;
    }

    /**
     * 
     * @param request
     * @return
     */
    @GET
    @Path("loggedInUser")
    @Produces(value = "application/json")
    public Login getLoggedInUser(@Context HttpServletRequest request) {
	HttpSession session = request.getSession(false);
	return (Login) session.getAttribute(SessionConstants.LOGIN);
    }

    /**
     * 
     * @return
     */
    @GET
    @Path("findAll")
    @Produces(value = "application/json")
    public List<Login> findAll() {
	List<Login> loginList = null;
	try {
	    loginList = loginDAO.findAll();
	} catch (DBException ex) {
	    Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return loginList;
    }

    /**
     * 
     * @return
     */
    @GET
    @Path("findByProfession/{profession}")
    @Produces(value = "application/json")
    public List<Login> findByProfession(
	    @PathParam(value = "profession") String profession) {
	List<Login> loginList = null;
	try {
	    loginList = loginDAO.findAllByProfession(profession);
	} catch (DBException ex) {
	    Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return loginList;
    }

    /**
     * 
     * @return
     */
    @GET
    @Path("search/{searchString}")
    @Produces(value = "application/json")
    public List<Login> search(
	    @PathParam(value = "searchString") String searchString) {
	List<Login> loginList = null;
	try {
	    loginList = loginDAO.search(searchString);
	} catch (DBException ex) {
	    Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return loginList;
    }

    /**
     * 
     * @return
     */
    @GET
    @Path("findByLoginId/{loginId}")
    @Produces(value = "application/json")
    public Login findByLoginId(@PathParam(value = "loginId") String loginId) {
	Login login = null;
	try {
	    login = loginDAO.findByLoginId(loginId);
	    System.out.println("findByLoginId: " + login);
	} catch (DBException ex) {
	    Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return login;
    }

    @GET
    @Path("findOnlineUsers/{loginId}")
    @Produces(value = "application/json")
    public Set<String> findOnlineUsers(
	    @PathParam(value = "loginId") String loginId) {
	return ChatServerEndpoint.getOnlineUsers();
    }

}

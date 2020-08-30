/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anjali.dao.CategoryDAO;
import com.anjali.dao.ContentDAO;
import com.anjali.dao.LoginDAO;
import com.anjali.dao.ShareBoxDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.CategoryDAOImpl;
import com.anjali.dao.impl.ContentDAOImpl;
import com.anjali.dao.impl.LoginDAOImpl;
import com.anjali.dao.impl.ShareBoxDAOImpl;
import com.anjali.domain.Category;
import com.anjali.domain.Content;
import com.anjali.domain.Login;

/**
 * 
 * @author shekharkumar
 */
@Path(value = "content")
public class ContentResource {

    private final ContentDAO contentDAO;
    private final ShareBoxDAO shareBoxDAO;
    private final CategoryDAO categoryDAO;
    private final LoginDAO loginDAO;

    public ContentResource() {
	contentDAO = new ContentDAOImpl();
	shareBoxDAO = new ShareBoxDAOImpl();
	categoryDAO = new CategoryDAOImpl();
	loginDAO = new LoginDAOImpl();
    }

    @GET
    @Path("findById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Content findById(@PathParam(value = "id") Long id) {
	Content content = null;
	try {
	    content = contentDAO.find(id);
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return content;
    }

    @GET
    @Path("findAll/{loginId}/{page}")
    @Produces(value = { MediaType.APPLICATION_JSON })
    public List<Content> findAll(@PathParam(value = "loginId") String loginId,
	    @PathParam(value = "page") int page) {

	List<Content> contentList = new ArrayList<Content>();
	try {
	    List<Content> sharedList = shareBoxDAO.sharedByFriends(loginId);
	    if (sharedList != null) {
		contentList.addAll(sharedList);
	    }
	    contentList.addAll(contentDAO.findAll(page));
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	
	for(Content content: contentList){
	    try {
		content.setSharerList(shareBoxDAO.getSharer(content.getId()));
	    } catch (DBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	

	return contentList;
    }

    @POST
    @Path("share/{contentId}/{sharedByLoginId}")
    @Consumes(value = { MediaType.APPLICATION_JSON })
    @Produces(value = { MediaType.APPLICATION_JSON })
    public Content share(@PathParam(value = "contentId") long contentId,
	    @PathParam(value = "sharedByLoginId") String sharedByLoginId) {
	System.out.println("ContentId: " + contentId);
	System.out.println("Shared By: " + sharedByLoginId);
	Content content = null;
	try {
	    shareBoxDAO.create(contentId, sharedByLoginId);
	} catch (DBException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return content;
    }

    @GET
    @Path("findAllShared/{loginId}/{page}")
    @Produces(value = { MediaType.APPLICATION_JSON })
    public List<Content> findAllShared(
	    @PathParam(value = "loginId") String loginId,
	    @PathParam(value = "page") int page) {
	List<Content> contentProxyList = new ArrayList<Content>();
	List<Content> contentList = null;
	try {
	    contentList = shareBoxDAO.contentsByLoginId(loginId);
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	for (Content content : contentList) {
	    contentProxyList.add(content);
	}
	return contentProxyList;
    }
    
    @GET
    @Path("sharer/{contentId}")
    @Produces(value = { MediaType.APPLICATION_JSON })
    public List<Login> sharer(
	    @PathParam(value = "contentId") long contentId) {
	List<Login> loginProxyList = new ArrayList<Login>();
	List<Login> loginList = null;
	try {
	    loginList = shareBoxDAO.getSharer(contentId);
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	for (Login login : loginList) {
	    loginProxyList.add(login);
	}
	return loginProxyList;
    }


    @GET
    @Path("findAllByLoginId/{loginId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Content> findAllLoginId(
	    @PathParam(value = "loginId") long loginId) {
	List<Content> ideaProxyList = new ArrayList<Content>();
	List<Content> ideaList = null;
	try {
	    ideaList = contentDAO.findAllByLoginId(loginId);
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}

	for (Content content : ideaList) {
	    ideaProxyList.add(content);
	}
	return ideaProxyList;
    }

    @GET
    @Path("searchProfessional/{searchString}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Content> searchProfessional(
	    @PathParam(value = "searchString") String searchString) {
	System.out.println("ContentResource >> search: " + searchString);
	List<Content> contentList = null;
	try {
	    contentList = contentDAO.search(searchString);
	    System.out.println("search >> number of returned record: "
		    + contentList.size());
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return contentList;
    }

    @GET
    @Path("searchContent/{searchString}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Content> searchContent(
	    @PathParam(value = "searchString") String searchString) {
	System.out.println("ContentResource >> searchContent: " + searchString);
	List<Content> contentList = null;
	try {
	    contentList = contentDAO.search(searchString);
	    System.out.println("search >> number of returned record: "
		    + contentList.size());
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return contentList;
    }

    @GET
    @Path("searchAdvance/{categoryId}/{city}/{maxInvestment}/{minProfit}/{businessType}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Content> searchAdvance(
	    @PathParam(value = "categoryId") long categoryId,
	    @PathParam(value = "city") String city,
	    @PathParam(value = "maxInvestment") long maxInvestment,
	    @PathParam(value = "minProfit") long minProfit,
	    @PathParam(value = "businessType") int businessType,
	    @PathParam(value = "page") int page) {
	System.out.println("searchAdvance");
	List<Content> ideaList = null;
	try {
	    ideaList = contentDAO.searchAdvance(categoryId, city,
		    maxInvestment, minProfit, businessType, page);
	    System.out.println("search >> number of returned record: "
		    + ideaList.size());
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return ideaList;
    }

    @POST
    @Path("create/{loginId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Content create(Content content,
	    @PathParam(value = "loginId") String loginId) throws DBException {

	Login createdBy = loginDAO.findByLoginId(loginId);
	Category category = categoryDAO.findByName(content.getCategoryName());
	content.setHeading(content.getHeading());
	content.setMeterial(content.getMeterial());
	content.setCategory(category);
	content.setCreatedBy(createdBy);
	try {
	    contentDAO.create(content);
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return content;
    }

    @POST
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Content update(Content content) throws DBException {
	Category category = categoryDAO.find(content.getCategory().getId());
	content.setCategory(category);
	try {
	    contentDAO.update(content);
	} catch (DBException ex) {
	    Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return content;
    }

    @POST
    @Path("like/{contentId}/{loginId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public int like(@PathParam(value = "contentId") long contentId,
	    @PathParam(value = "loginId") String loginId) throws DBException {

	int totalLike = contentDAO.addLike(contentId, loginId);
	return totalLike;
    }
}

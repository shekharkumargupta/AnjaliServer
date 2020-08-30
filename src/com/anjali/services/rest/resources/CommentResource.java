/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.services.rest.resources;

import java.util.Calendar;
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

import com.anjali.dao.ContentDAO;
import com.anjali.dao.LoginDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.ContentDAOImpl;
import com.anjali.dao.impl.LoginDAOImpl;
import com.anjali.domain.Comment;
import com.anjali.domain.Login;

/**
 * 
 * @author shekharkumar
 */
@Path(value = "comment")
public class CommentResource {

    private final ContentDAO contentDAO;
    private final LoginDAO loginDAO;

    public CommentResource() {
	contentDAO = new ContentDAOImpl();
	loginDAO = new LoginDAOImpl();
    }

    @GET
    @Path(value = "findAll/{contentId}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<Comment> findAllByIdea(@PathParam("contentId") long contentId) {
	List<Comment> comments = null;
	try {
	    comments = contentDAO.find(contentId).getComments();
	} catch (DBException ex) {
	    Logger.getLogger(CommentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return comments;
    }

    @POST
    @Path(value = "create/{contentId}")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Comment addComment(@PathParam(value = "contentId") long contentId,
	    Comment comment) {
	try {
	    Login login = (Login) loginDAO.findByLoginId(comment
		    .getCommentedBy().getLoginId());
	    comment.setCommentedBy(login);
	    comment.setCommentedAt(Calendar.getInstance().getTime());
	    contentDAO.addComment(contentId, comment);
	} catch (DBException ex) {
	    Logger.getLogger(CommentResource.class.getName()).log(Level.SEVERE,
		    null, ex);
	}
	return comment;
    }
}

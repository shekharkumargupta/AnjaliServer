package com.anjali.services.rest.resources;

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

import com.anjali.dao.MessageDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.MessageDAOImpl;
import com.anjali.domain.Message;

@Path(value = "message")
public class MessageResource {

	private final MessageDAO messageDAO;

	public MessageResource() {
		messageDAO = new MessageDAOImpl();
	}

	@GET
	@Path("from/{loginId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> messageFrom(@PathParam(value = "loginId") String loginId) {
		List<Message> messages = null;
		try {
			messages = messageDAO.findAllFrom(loginId);
		} catch (DBException ex) {
			Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return messages;
	}

	@GET
	@Path("to/{loginId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> messageTo(@PathParam(value = "loginId") String loginId) {
		List<Message> messages = null;
		try {
			messages = messageDAO.findAllTo(loginId);
		} catch (DBException ex) {
			Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return messages;
	}


	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message create(Message message) throws DBException {
		try {
			messageDAO.create(message);
		} catch (DBException ex) {
			Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return message;
	}

	@POST
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message update(Message message) throws DBException {
		try {
			messageDAO.update(message);
		} catch (DBException ex) {
			Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return message;
	}

	@POST
	@Path("remove")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message remove(Message message) throws DBException {
		try {
			message = messageDAO.remove(message.getId());
		} catch (DBException ex) {
			Logger.getLogger(ContentResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return message;
	}
}

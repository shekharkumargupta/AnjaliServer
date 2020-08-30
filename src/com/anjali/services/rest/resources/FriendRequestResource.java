package com.anjali.services.rest.resources;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anjali.constants.ApplicationConstants;
import com.anjali.dao.FriendRequestDAO;
import com.anjali.dao.LoginDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.dao.impl.FriendRequestDAOImpl;
import com.anjali.dao.impl.LoginDAOImpl;
import com.anjali.domain.FriendRequest;
import com.anjali.domain.Login;

@Path(value = "friendRequest")
public class FriendRequestResource {

	private final LoginDAO loginDAO;
	private final FriendRequestDAO friendRequestDAO;
	
	public FriendRequestResource(){
		loginDAO = new LoginDAOImpl();
		friendRequestDAO = new FriendRequestDAOImpl();
	}
	
	
	/**
	 * 
	 * @param requestFromLoginId
	 * @param requestToLoginId
	 * @return
	 */
	@GET
	@Path("makeFriendRequest/{requestFromLoginId}/{requestToLoginId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public FriendRequest makeFriendRequest(@PathParam(value = "requestFromLoginId") String requestFromLoginId,
			@PathParam(value = "requestToLoginId") String requestToLoginId) {
		System.out.println("makeFriendRequest >> requestFromLoginId:" + requestFromLoginId + " requestToLoginId"+requestToLoginId);
		Login requestFrom = null;
		Login requestTo = null;
		FriendRequest friendRequest = new FriendRequest();
		try {
			requestFrom = loginDAO.findByLoginId(requestFromLoginId);
			requestTo = loginDAO.findByLoginId(requestToLoginId);
			friendRequest.setRequestFrom(requestFrom);
			friendRequest.setRequestTo(requestTo);
			friendRequest.setStatus(ApplicationConstants.FRIEND_REQUEST_STATUS.PENDING.ordinal());
			friendRequest = friendRequestDAO.create(friendRequest);
		} catch (DBException ex) {
			Logger.getLogger(FriendRequestResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return friendRequest;
	}
	
	
	/**
	 * 
	 * @param requestFromLoginId
	 * @param requestToLoginId
	 * @return
	 */
	@GET
	@Path("confirm/{friendRequestId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public FriendRequest confirmFriendRequest(@PathParam(value = "friendRequestId") long friendRequestId) {
		System.out.println("confirmFriendRequest >> friendRequestId:" + friendRequestId);
		FriendRequest friendRequest = null;
		try {
			friendRequest = friendRequestDAO.confirmRequest(friendRequestId);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return friendRequest;
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("friendRequestTo/{loginId}")
	@Produces(value = "application/json")
	public List<FriendRequest> friendRequestTo(@PathParam(value = "loginId")String loginId) {
		List<FriendRequest> friendReuqestList = null;
		try {
			friendReuqestList = friendRequestDAO.findAllByRequestTo(loginId);	
		} catch (DBException ex) {
			Logger.getLogger(FriendRequestResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return friendReuqestList;
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("friendRequestFrom/{loginId}")
	@Produces(value = "application/json")
	public List<FriendRequest> friendRequestFrom(@PathParam(value = "loginId")String loginId) {
		List<FriendRequest> friendReuqestList = null;
		try {
			friendReuqestList = friendRequestDAO.findAllByRequestFrom(loginId);	
		} catch (DBException ex) {
			Logger.getLogger(FriendRequestResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return friendReuqestList;
	}
	
}

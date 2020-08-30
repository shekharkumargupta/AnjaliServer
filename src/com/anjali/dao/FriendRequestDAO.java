package com.anjali.dao;

import java.util.List;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.FriendRequest;

public interface FriendRequestDAO {

	public FriendRequest create(FriendRequest friendRequest) throws DBException;
	public FriendRequest update(FriendRequest friendRequest) throws DBException;
	public FriendRequest remove(FriendRequest friendRequest) throws DBException;
	
	public FriendRequest confirmRequest(long friendRequestId) throws DBException;
    
    public List<FriendRequest> findAll() throws DBException;
    public List<FriendRequest> findAllByRequestFrom(String requestFromLoginId) throws DBException;
    public List<FriendRequest> findAllByRequestTo(String requestTooginId) throws DBException;
    
    public FriendRequest find(Long friendRequestId) throws DBException;
}

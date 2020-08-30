package com.anjali.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.anjali.constants.ApplicationConstants;
import com.anjali.dao.FriendRequestDAO;
import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.FriendRequest;
import com.anjali.domain.Login;
import com.anjali.util.HibernateUtil;

public class FriendRequestDAOImpl implements FriendRequestDAO{

	private Session sessionObj;

	private Session getSession() {
		if (sessionObj != null && sessionObj.isOpen()) {
			sessionObj = HibernateUtil.getSessionFactory().getCurrentSession();
		} else {
			sessionObj = HibernateUtil.getSessionFactory().openSession();
		}
		return sessionObj;
	}
	
	@Override
	public FriendRequest create(FriendRequest friendRequest) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		
		friendRequest.setStatus(ApplicationConstants.FRIEND_REQUEST_STATUS.PENDING.ordinal());
		friendRequest.setRequestedAt(Calendar.getInstance().getTime());
		session.save(friendRequest);
		session.getTransaction().commit();
		session.close();
		return friendRequest;
	}

	@Override
	public FriendRequest update(FriendRequest friendRequest) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		session.update(friendRequest);
		session.getTransaction().commit();
		session.close();
		return friendRequest;
	}

	@Override
	public FriendRequest remove(FriendRequest friendRequest) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		session.delete(friendRequest);
		session.getTransaction().commit();
		session.close();
		return friendRequest;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FriendRequest> findAll() throws DBException {
		Session session = getSession();
		Query query = session.createQuery("Select f from FriendRequest f order by f.requestedAt desc");
		// query.setFirstResult(((page * pageSize) - pageSize));
		// query.setMaxResults(pageSize);
		List<FriendRequest> friendRequestList = query.list();
		// session.close();
		return friendRequestList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FriendRequest> findAllByRequestFrom(String requestFromLoginId) throws DBException {
		Session session = getSession();
		Login requestFrom  = (Login)session.createQuery("select l from Login l where l.loginId = ?")
				.setParameter(0, requestFromLoginId)
				.uniqueResult();
		Query query = session.createQuery("Select f from FriendRequest f where f.requestFrom  = "+requestFrom.getId()+" order by f.requestedAt desc");
		List<FriendRequest> friendRequestList = query.list();
		return friendRequestList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FriendRequest> findAllByRequestTo(String requestToLoginId) throws DBException {
		Session session = getSession();
		Login requestTo  = (Login)session.createQuery("select l from Login l where l.loginId = ?")
				.setParameter(0, requestToLoginId)
				.uniqueResult();
		Query query = session.createQuery("Select f from FriendRequest f where f.requestTo.id  = ? and f.status = ? order by f.requestedAt desc");
		query.setParameter(0, requestTo.getId());
		query.setParameter(1, 0);
		List<FriendRequest> friendRequestList = query.list();
		return friendRequestList;
	}

	@Override
	public FriendRequest find(Long friendRequestId) throws DBException {
		Session session = getSession();
		Query query = session.createQuery("select f from FriendRequest f where f.id = ?")
				.setParameter(0, friendRequestId);
		FriendRequest request = (FriendRequest) query.uniqueResult();
		session.close();
		return request;
	}

	@Override
	public FriendRequest confirmRequest(long friendRequestId) throws DBException {
		Session session = getSession();
		session.beginTransaction();
		Query friendRequestQuery = session.createQuery("select f from FriendRequest f where f.id = ?")
				.setParameter(0, friendRequestId);
		FriendRequest friendRequest = (FriendRequest) friendRequestQuery.uniqueResult();
		friendRequest.setStatus(ApplicationConstants.FRIEND_REQUEST_STATUS.CONFIRMED.ordinal());
		session.update(friendRequest);
		
		Login requestFrom = friendRequest.getRequestFrom();
		Login requestTo = friendRequest.getRequestTo();
		
		requestTo.addFriend(requestFrom);
		session.update(requestTo);
		
		requestFrom.addFriend(requestTo);
		session.update(requestFrom);
		session.getTransaction().commit();
		session.close();
		return friendRequest;
	}

}

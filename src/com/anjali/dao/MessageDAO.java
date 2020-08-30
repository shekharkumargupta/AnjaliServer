package com.anjali.dao;

import java.util.List;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Message;

public interface MessageDAO {

	public Message create(Message message) throws DBException;

	public Message update(Message message) throws DBException;

	public Message remove(Long id) throws DBException;

	public Message find(Long id) throws DBException;

	public List<Message> findAll() throws DBException;

	public List<Message> findAllFrom(String loginId) throws DBException;
	
	public List<Message> findAllTo(String loginId) throws DBException;
}

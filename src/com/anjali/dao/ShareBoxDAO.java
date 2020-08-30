package com.anjali.dao;

import java.util.List;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Content;
import com.anjali.domain.Login;
import com.anjali.domain.ShareBox;

public interface ShareBoxDAO {

	public ShareBox create(ShareBox shareBox) throws DBException;
	public ShareBox create(long contentId, String sharedByLoginId) throws DBException;
	public List<Content> contentsByLoginId(String loginId) throws DBException;
	public List<Content> sharedByFriends(String loginId) throws DBException;
	public List<Login> getSharer(long contentId) throws DBException;
}

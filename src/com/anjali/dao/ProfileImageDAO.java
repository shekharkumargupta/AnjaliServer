package com.anjali.dao;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.ProfileImage;

public interface ProfileImageDAO {

    public ProfileImage create(ProfileImage image) throws DBException;
    public ProfileImage update(ProfileImage image) throws DBException;
    public ProfileImage findByLoginId(String loginId) throws DBException;
}

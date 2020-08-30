/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao;

import com.anjali.dao.exceptions.DBException;
import com.anjali.domain.Comment;
import com.anjali.domain.Document;
import com.anjali.domain.Content;
import com.anjali.domain.Video;

import java.util.List;

/**
 *
 * @author shekharkumar
 */
public interface ContentDAO {

    public Content create(Content content) throws DBException;

    public Content update(Content content) throws DBException;

    public Content remove(Long id) throws DBException;

    public Content find(Long id) throws DBException;

    public List<Content> findAll(int page) throws DBException;

    public List<Content> findAllByLoginId(long id) throws DBException;

    public List<Content> search(String seachString) throws DBException;

    public List<Content> searchAdvance(long categoryId, String city,
            long maxInvestment, long minProfit, int businessType, int page) throws DBException;

    public List<Content> searchByCategory(String category) throws DBException;

    public Content addDocument(long contentId, Document document) throws DBException;

    public Content addVideo(long contentId, Video video) throws DBException;

    public Content addComment(long contentId, Comment comment) throws DBException;
    
    public int addLike(long contentId, String loginId) throws DBException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.dao;

import com.anjali.domain.Document;

/**
 *
 * @author Ramesh
 */
public interface DocumentDAO {
    
    public Document findById(Long id);
}

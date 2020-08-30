/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anjali.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shekharkumar
 */
public class IOUtil {
    
    public static byte[] getBytes(InputStream uploadedInputStream, long contentSize) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read = 0;        
        byte[] data = new byte[16384];
        try {
            while ((read = uploadedInputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }
        } catch (IOException ex) {
            Logger.getLogger(IOUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffer.toByteArray();
    }
}

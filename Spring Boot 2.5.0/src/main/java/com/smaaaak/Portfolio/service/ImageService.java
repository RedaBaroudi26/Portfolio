package com.smaaaak.Portfolio.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

     byte[] getImage( Long idImage , Long idObject , String classname) throws Exception ;

     void updateImage(Long idImage , Long idObject , String className , MultipartFile file) ;

}

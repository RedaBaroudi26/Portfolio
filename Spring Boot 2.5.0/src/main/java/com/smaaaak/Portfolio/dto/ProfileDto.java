package com.smaaaak.Portfolio.dto;

import com.smaaaak.Portfolio.model.SocialMedia;
import lombok.Data;

@Data
public class ProfileDto {

    private String fullName ;
    private String about ;
    private String phone ;
    private String address ;
    private String job ;
    private String country ;
    private String company ;
    private SocialMedia socialMediaDto ;

}

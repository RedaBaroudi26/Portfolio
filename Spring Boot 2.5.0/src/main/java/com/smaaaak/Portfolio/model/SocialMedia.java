package com.smaaaak.Portfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="social_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSocialMedia ;

    private String twitterProfile ;

    private String facebookProfile ;

    private String instagramProfile ;

    private String linkedinProfile ;


}

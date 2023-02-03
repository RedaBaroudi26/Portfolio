package com.smaaaak.Portfolio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser ;

    private String fullName ;

    @Column(columnDefinition = "TEXT")
    private String about ;

    private String phone ;

    private String address ;

    private String job ;

    private String country ;

    private String company ;

    @OneToOne
    @JoinColumn(name = "idSocialMedia")
    private SocialMedia socialMedia ;

    @OneToOne
    @JoinColumn(name="idAccount")
    private Account account ;

}

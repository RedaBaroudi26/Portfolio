package com.smaaaak.Portfolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount ;

    @Column(unique = true)
    private String username ;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password ;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateCreation ;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>() ;


}

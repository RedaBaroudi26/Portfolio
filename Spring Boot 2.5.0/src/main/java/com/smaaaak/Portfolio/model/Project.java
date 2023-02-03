package com.smaaaak.Portfolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProject ;

    private String projectName ;

    private String description ;

    private String content ;

    private int rating ;

    private String clientName ;

    private boolean completed ;

    private String webSiteUrl ;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date completedDate ;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>() ;

}

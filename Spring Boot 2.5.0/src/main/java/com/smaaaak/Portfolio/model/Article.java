package com.smaaaak.Portfolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticle ;

    private String title ;

    private String description ;

    @Column(columnDefinition = "TEXT")
    private String content ;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateCreation ;

    private String writerName ;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category ;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>() ;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>() ;

}

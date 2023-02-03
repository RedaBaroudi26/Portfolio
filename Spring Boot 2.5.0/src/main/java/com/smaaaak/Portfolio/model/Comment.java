package com.smaaaak.Portfolio.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment ;

    private String name ;

    private String email ;

    private String message ;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreation ;

    @OneToMany(mappedBy = "comment")
    @JsonManagedReference
    private List<Reply> replies = new ArrayList<>() ;

    @ManyToOne
    @JoinColumn(name = "id_article")
    @JsonBackReference
    private Article article ;

}

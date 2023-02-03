package com.smaaaak.Portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore ;
import lombok.AllArgsConstructor ;
import lombok.Data ;
import lombok.NoArgsConstructor ;

import javax.persistence.* ;
import java.util.ArrayList ;
import java.util.List ;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory ;

    private String categoryName ;

    @OneToMany(mappedBy = "category"  , cascade = CascadeType.REMOVE )
    @JsonIgnore
    private List<Article> articles = new ArrayList<>() ;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getNumberOfArticles(){
        return this.articles.size() ;
    }

}



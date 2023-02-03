package com.smaaaak.Portfolio.controller;

import com.smaaaak.Portfolio.model.Category;
import com.smaaaak.Portfolio.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private CategoryService categoryService ;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(this.categoryService.getAllCategories() , HttpStatus.OK) ;
    }

    @GetMapping("/categoriesByPages/{offset}/{size}")
    public ResponseEntity<Page<Category>> getAllCategories(@PathVariable("offset") int offset , @PathVariable("size") int size ){
        return new ResponseEntity<>(this.categoryService.getCategoriesByPage(offset, size) , HttpStatus.OK) ;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCategoryCount(){
        return new ResponseEntity<>(this.categoryService.getCategoriesCount() , HttpStatus.OK) ;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@RequestBody Category newCategory){
         return new ResponseEntity<>(this.categoryService.addNewCategory(newCategory) , HttpStatus.CREATED) ;
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        return new ResponseEntity<>(this.categoryService.updateCategory(category) , HttpStatus.OK) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long idCategory){
        this.categoryService.deleteCategory(idCategory) ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

}

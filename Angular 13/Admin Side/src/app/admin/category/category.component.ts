import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ICategory, ICategoryPages } from 'src/app/shared/model/Category';
import { CategoryService } from 'src/app/shared/services/category.service';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  categories !: Observable<ICategoryPages> ;
  categoryForm !: FormGroup ;
  categoryName !: FormControl ;
  loading : boolean = false ;

  selectedCategory : ICategory = { idCategory : 0 , categoryName : "" } ; 

  messageError !: string ;

  constructor( private categoryService : CategoryService , private router : Router , private cd : ChangeDetectorRef) { 
  }

  ngOnInit(): void {

    this.categoryName = new FormControl( '' , Validators.required) ;
    this.categoryForm = new FormGroup({
       categoryName : this.categoryName 
    }) 

    this.getAllCategories() ;

  }

  getAllCategories(offset : number = 0 , size : number = 8){
    this.categories = this.categoryService.getCategoriesByPages(offset , size).pipe( tap( data => { this.loading = true ; this.cd.detectChanges() } ) ) ;
  }

  addCategory(){
    this.loading = false ;
    this.categoryService.addCategory(this.categoryForm.value).subscribe(
      (response : any) => { this.loading = true ;  this.getAllCategories() ; this.categoryForm.reset() ; this.messageError = "" ; document.getElementById('home-tab')?.click() ;  } ,
      (error : HttpErrorResponse ) => { this.loading = true ; this.messageError = error.error.message }
    ) ;
    
  }

  updateCategory(){

    this.loading = false ;
    this.categoryService.updateCategory(this.selectedCategory).subscribe(
      ( response : any ) => { this.loading = true ;  this.getAllCategories() ;  this.messageError = "" ; document.getElementById('closeModalUpdate')?.click() } ,
      (error : HttpErrorResponse ) => { this.loading = true ;  this.messageError = error.error.message }
    ) ;

  }

  deleteCategory(){

    this.loading = false ;
    this.categoryService.deleteCategory(this.selectedCategory.idCategory).subscribe(
      (response : any) => { this.loading = true ;  this.getAllCategories() ; document.getElementById('closeModalDelete')?.click() ; this.messageError = "" } ,
      (error : any) => { this.loading = true ;  this.messageError = error.error.message }
    ) ;

  }

  counterNumber(i: number) {
    return new Array(i);
  }

  buttonClick(category : ICategory){
    this.selectedCategory = category;
    this.messageError = '';
  }

}

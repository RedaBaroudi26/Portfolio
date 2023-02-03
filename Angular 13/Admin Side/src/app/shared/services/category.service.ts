import { HttpClient, HttpContext, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICategory, ICategoryPages } from '../model/Category';
import { WITH_TOKEN } from './token-interceptor.service';

@Injectable({
  providedIn: 'root'
})

export class CategoryService {

  private apiServerUrl = environment.apiBaseUrl ;

  constructor(private http : HttpClient) { }


  public getCategories() : Observable<ICategory[]>{
    return this.http.get<ICategory[]>(`${this.apiServerUrl}/api/category/all`);
  }

  public getCategoriesByPages(offset : number , size : number) : Observable<ICategoryPages>{
    return this.http.get<ICategoryPages>(`${this.apiServerUrl}/api/category/categoriesByPages/${offset}/${size}`);
  }

  public getCategorCount() : Observable<number>{
    return this.http.get<number>(`${this.apiServerUrl}/api/category/count`);
  }

  public addCategory( newCategory : ICategory ) : Observable<ICategory>{
     return this.http.post<ICategory>(`${this.apiServerUrl}/api/category/add` , newCategory ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    }) ;
  }

  public updateCategory( updatedCategory : ICategory) : Observable<ICategory>{
     return this.http.put<ICategory>(`${this.apiServerUrl}/api/category/update` , updatedCategory ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }

  public deleteCategory( idCategory : number ) : Observable<void>{
     return this.http.delete<void>(`${this.apiServerUrl}/api/category/delete/${idCategory}` ,  { 
      context : new HttpContext().set(WITH_TOKEN , true) 
    } ) ;
  }


  categories : Observable <ICategory[]> = this.getCategories() ;


}

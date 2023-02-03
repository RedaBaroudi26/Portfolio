import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICategory, ICategoryPages } from '../model/Category';

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


  categories : Observable <ICategory[]> = this.getCategories() ;


}

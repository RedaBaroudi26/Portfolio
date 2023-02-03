import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable, of, tap } from 'rxjs';
import { IUser } from 'src/app/shared/model/User';
import { UserService } from 'src/app/shared/services/user.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  isAuth !: boolean ; 

  constructor(private userService: UserService , private router : Router ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
     
      this.userLoggedIn().pipe(
        map( (val : boolean) => { val ? this.isAuth = val : this.router.navigate(['/admin/login']) } )
      )

     return  this.userLoggedIn() ;
  }

  userLoggedIn() : Observable<boolean> {

    if ( localStorage.getItem('accessToken') && localStorage.getItem('username') ) {
      this.userService.isAuth =  this.userService.IsAuth() ; 
    }else{
       this.router.navigate(['/admin/login']) ;
       return of(false) ; 
    }

    return this.userService.isAuth ;
  }

}

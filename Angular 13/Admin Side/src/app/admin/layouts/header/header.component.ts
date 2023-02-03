import { Component, OnInit, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common'
import { UserService } from 'src/app/shared/services/user.service';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { IUser } from 'src/app/shared/model/User';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

  username : string | undefined ;

  user$ !: Observable<IUser> ;

  constructor(@Inject(DOCUMENT) private document: Document , private userSerice : UserService , private router : Router) { }


  ngOnInit(): void {
    this.username = localStorage.getItem('username')?.toString() ; 
    this.user$ = this.userSerice.profile() ;
  }
  
  sidebarToggle()
  {
    //toggle sidebar function
    this.document.body.classList.toggle('toggle-sidebar');
  }

  signOut(){
    localStorage.clear() ;
    this.userSerice.isAuth = of(false) ; 
    this.router.navigate(['/admin/dashboard']) ;
  }

}

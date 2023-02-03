import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import * as Aos from 'aos';
import { Observable, tap } from 'rxjs';
import { IUser } from 'src/app/shared/model/User';
import { SeoService } from 'src/app/shared/services/seo.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit {

  user$ !: Observable<IUser> ; 
  loading : boolean = false ; 

  constructor(private userService : UserService , private seo : SeoService) { }

  ngOnInit(): void {
    this.seo.updateMetaInformationForPage( 'About Me , freelancer , Building WebSites , Seo Optimize , Web Design' 
       , 'a person who can build your WebSite to introduce yourSelf to World' , 'About Me' ) ;
    Aos.init();
    this.user$ = this.userService.user$.pipe( tap( data => {this.loading = true} ) ) ;

  }

}

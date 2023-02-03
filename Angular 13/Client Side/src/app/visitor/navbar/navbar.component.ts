import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';

declare const myFunc : any ;

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private userService : UserService) { }

  ngOnInit(): void {
    myFunc();
    this.userService.user$ = this.userService.profile() ;
  }

}

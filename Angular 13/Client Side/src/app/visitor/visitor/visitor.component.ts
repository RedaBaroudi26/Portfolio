import { Component, OnInit } from '@angular/core';

import * as Aos from 'aos';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-visitor',
  templateUrl: './visitor.component.html'
})
export class VisitorComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    Aos.init();
  }

  onActivate() {
    window.scroll({ 
            top: 0, 
            left: 0, 
            behavior: 'smooth' 
     });
 }

}

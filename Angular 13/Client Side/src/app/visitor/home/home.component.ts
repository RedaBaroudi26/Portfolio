import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import * as Aos from 'aos';
import { SeoService } from 'src/app/shared/services/seo.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private seo: SeoService) { }

  ngOnInit(): void {
    Aos.init();
    this.seo.initDefaultMetaInformation() ;
  }

}

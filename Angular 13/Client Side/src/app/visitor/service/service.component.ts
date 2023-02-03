import { Component, OnInit } from '@angular/core';
import * as Aos from 'aos';
import { SeoService } from 'src/app/shared/services/seo.service';

@Component({
  selector: 'app-service',
  templateUrl: './service.component.html',
  styleUrls: ['./service.component.scss']
})
export class ServiceComponent implements OnInit {

  constructor(private seo : SeoService) { }

  ngOnInit(): void {
    Aos.init();
    this.seo.updateMetaInformationForPage( ' Services , Web design , Web Development , Seo Optimise ' , ' Create a Web Site ByCoding ' , ' My Services ' ) ;
  }

}

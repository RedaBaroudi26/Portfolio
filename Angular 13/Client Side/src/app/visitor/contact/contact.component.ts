import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import * as Aos from 'aos';
import { Observable, tap } from 'rxjs';
import { IUser } from 'src/app/shared/model/User';
import { SeoService } from 'src/app/shared/services/seo.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {

  contactForm : FormGroup = new FormGroup({});
  name : FormControl | undefined ;
  email : FormControl | undefined ;
  subject : FormControl | undefined ;
  message : FormControl | undefined ;
  loading : boolean = false ; 

  user$ !: Observable<IUser> ;
  

  constructor(private seo : SeoService ,private http : HttpClient , private userService : UserService) { }

  ngOnInit(): void {

    this.seo.updateMetaInformationForPage( 'Contact Me , Reda Baroudi , Smaaaak , Smaak , Smaaak' , ' contact me to create you web Site ' , 'Contact Me' );

    Aos.init() ;

    this.user$ = this.userService.user$.pipe( tap( data => { this.loading = true } ) ) ;

    this.name = new FormControl('' , Validators.required) ;
    this.subject = new FormControl('' , Validators.required) ;
    this.message = new FormControl('' , Validators.required) ;
    this.email = new FormControl('' , [ Validators.required , Validators.email]) ;

    this.contactForm = new FormGroup( {
          name : this.name ,
          email : this.email ,
          subject : this.subject ,
          message : this.message 
        } ) 

  }


  onSubmit(contactForm: any) {
    if (contactForm.valid) {
      const email = contactForm.value;
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      this.http.post('https://formspree.io/f/mpznqwbg',
        { name : email.name , replyto : email.email , message : email.message , subject : email.subject },
        { 'headers': headers }).subscribe(
         ( response : any ) => {
            // console.log(response); 
            this.contactForm.reset() ;
          }
        );
    }
  }

}

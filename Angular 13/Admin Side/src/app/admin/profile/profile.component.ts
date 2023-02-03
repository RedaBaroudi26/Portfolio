import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { IUser } from 'src/app/shared/model/User';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

   user$ !: Observable<IUser> ;

   messageError !: string ;
   loading : boolean = true ;

  constructor(private userService : UserService , private router : Router) { }

  ngOnInit(): void {
    this.profile() ;
  }

  profile(){
   this.user$ = this.userService.profile() ;
  }

  editProfile(formValue : any ){

    this.loading = false ;

    let user : IUser= {
      idUser: formValue.idUser,
      fullName: formValue.fullName ,
      socialMedia: { 
        idSocialMedia : formValue.idSocialMedia ,
        twitterProfile : formValue.twitterProfile ,
        instagramProfile : formValue.instagramProfile , 
        facebookProfile : formValue.facebookProfile , 
        linkedinProfile : formValue.linkedinProfile 
      },
      about: formValue.about ,
      phone: formValue.phone,
      address: formValue.address,
      job: formValue.job,
      country:formValue.country,
      company: formValue.company,
      account : {idAccount : formValue.idAccount}
    } ; 

    this.userService.updateProfile(user).subscribe(
      (response : any) => { this.loading = true ; document.getElementById('overview')?.click() ; } , 
      (error : HttpErrorResponse) => { this.loading = true ; this.messageError = error.error.message ; console.log(error) }
    )

  }

}

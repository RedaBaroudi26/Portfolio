import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { TokenService } from 'src/app/shared/services/token.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  userGroup !: FormGroup;
  username !: FormControl;
  password !: FormControl;
  errorMessage !: string;
  loading : boolean = true ; 

  constructor(private userSerice: UserService, private router: Router, private tokenService: TokenService) { }

  ngOnInit(): void {

    this.username = new FormControl('Smaaaak', Validators.required);
    this.password = new FormControl('VNR9s2KLL8DA8STZ', Validators.required);
    this.userGroup = new FormGroup({
      username: this.username,
      password: this.password
    })

  }

  submitForm() {

    this.loading = false ;
    this.userSerice.login(this.userGroup.controls['username'].value, this.userGroup.controls['password'].value).subscribe(
      (response: any) => {
        this.tokenService.setTokens(response.accessToken, response.refreshToken)
        localStorage.setItem("username", this.userGroup.controls['username'].value);
        this.userSerice.isAuth = of(true);
        this.loading = true ;
        this.userGroup.reset();
        this.router.navigate(['/admin/dashboard']);
      },
      (error: HttpErrorResponse) => { this.errorMessage = error.error.message ;  this.loading = true ; }
    )

  }

}


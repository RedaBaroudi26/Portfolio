import { Injectable } from '@angular/core';
import { HttpContextToken, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { catchError, Observable, switchMap, throwError } from 'rxjs';
import { TokenService } from './token.service';
import { UserService } from './user.service';


export const WITH_TOKEN = new HttpContextToken<boolean>(() => false);

@Injectable()

export class TokenInterceptorService implements HttpInterceptor {

  constructor(private tokenService: TokenService, private userService: UserService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let newReq: any = req.clone();

    if (newReq.context.get(WITH_TOKEN)) {
      newReq = req.clone({
        setHeaders: { 'Authorization': "Bearer " + localStorage.getItem('accessToken') }
      });
    }

    return next.handle(newReq).pipe(
      catchError((error: HttpErrorResponse) => {

        if (error.error.message.slice(0, 21) == 'The Token has expired') {

          return this.userService.refreshAccessToken().pipe(
            switchMap((data: any) => {
              this.tokenService.setAccessToken(data.accessToken);
              this.tokenService.setRefreshToken(data.refreshToken);
              return next.handle(newReq = req.clone({
                setHeaders: { 'Authorization': "Bearer " + localStorage.getItem('accessToken') }
              }))
            })
          )

        }

        return throwError(error) ;
      })
    );

  }


}

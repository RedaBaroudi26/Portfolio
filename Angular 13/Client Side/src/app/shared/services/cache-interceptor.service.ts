import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';
import { HttpCacheService } from './http-cache.service';

@Injectable()

export class CacheInterceptorService implements HttpInterceptor {

  constructor(private cacheService: HttpCacheService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (req.method !== 'GET') {

      return next.handle(req).pipe(
        tap(event => {
          if (event instanceof HttpResponse) {
            let url = req.url.slice(26).split('/')[0];
            for (let request in this.cacheService.requests) {
              if (request.slice(26).split('/')[0] == url) {
                this.cacheService.invalidateUrl(request);
              }
            }
          }
        })
      );

    }

    const cacheResponse: HttpResponse<any> = this.cacheService.get(req.url);

    if (cacheResponse) {
      return of(cacheResponse);
    }


    return next.handle(req).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {
          this.cacheService.put(req.url, event);
        }
      })
    );
    
  }



}

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Page404Component } from './shared/page404/page404.component';
import { CacheInterceptorService } from './shared/services/cache-interceptor.service';



@NgModule({
  declarations: [
    AppComponent ,
    Page404Component
  ],
  imports: [
    BrowserModule ,
    AppRoutingModule ,
    HttpClientModule 
  ],
  providers: [
    { provide : HTTP_INTERCEPTORS , useClass : CacheInterceptorService , multi : true } 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

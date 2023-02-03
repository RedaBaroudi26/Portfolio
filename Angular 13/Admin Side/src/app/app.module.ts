import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CacheInterceptorService } from './shared/services/cache-interceptor.service';
import { TokenInterceptorService } from './shared/services/token-interceptor.service';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule ,
    AppRoutingModule
  ],
  providers: [
    { provide : HTTP_INTERCEPTORS , useClass : TokenInterceptorService , multi : true },
    { provide : HTTP_INTERCEPTORS , useClass : CacheInterceptorService , multi : true } 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

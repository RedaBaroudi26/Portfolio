import { Component, ElementRef, OnInit } from '@angular/core';


@Component({
  selector: 'app-full',
  templateUrl: './full.component.html',
  styleUrls: ['./full.component.scss']
})
export class FullComponent implements OnInit {

  constructor(private elementRef: ElementRef) { }

  ngOnInit() {
    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../assets/Admin/js/main.js";
    this.elementRef.nativeElement.appendChild(s);
  }

}

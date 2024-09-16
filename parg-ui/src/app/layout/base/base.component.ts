import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { Router, NavigationStart, RouteConfigLoadStart, RouteConfigLoadEnd, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html',
  styleUrl: './base.component.scss'
})
export class BaseComponent implements AfterViewInit {

  @ViewChild('mainPanel') mainPanel!: ElementRef;
  @ViewChild('pageBodyWrapper') pageBodyWrapper!: ElementRef;
  @ViewChild('contentWrapper') contentWrapper!: ElementRef;

  showSidebar: boolean = true;
  showNavbar: boolean = true;
  showFooter: boolean = true;
  isLoading: boolean = false;

  constructor(private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        const url = (event as NavigationStart).url;
        this.updateSidebarVisibility(url);
      } else if (event instanceof RouteConfigLoadStart) {
        this.isLoading = true;
      } else if (event instanceof RouteConfigLoadEnd) {
        this.isLoading = false;
      }
    });
  }

  ngOnInit() {
    // Scroll to top after route change
    this.router.events.subscribe((evt) => {
      if (evt instanceof NavigationEnd) {
        window.scrollTo(0, 0);
      }
    });
  }

  ngAfterViewInit() {
    // Check current URL on initial load
    this.updateSidebarVisibility(this.router.url);
  }

  private updateSidebarVisibility(url: string) {}

}

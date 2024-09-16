import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  public iconOnlyToggled = false;
  public sidebarToggled = false;
  isToggleIconClicked: boolean = false;
  userDetail: any;
  userId: number | undefined;

  constructor(config: NgbDropdownConfig,
    private router: Router,
  ) {
    config.placement = 'bottom-right';
  }

  ngOnInit() {
    //getting tenant id from the localStorage
    this.userId = +(localStorage.getItem('userId') ?? '');

    this.getUserDetail(this.userId);
  }

  // toggle sidebar in small devices
  toggleOffcanvas() {
    const sidebar = document.querySelector('.sidebar-offcanvas') as HTMLElement;
    sidebar.classList.toggle('active');
  }

  // toggle sidebar
  toggleSidebar() {
    this.isToggleIconClicked = !this.isToggleIconClicked;
    
    let body = document.querySelector('body') as HTMLElement;
    if ((!body.classList.contains('sidebar-toggle-display')) && (!body.classList.contains('sidebar-absolute'))) {
      this.iconOnlyToggled = !this.iconOnlyToggled;
      if (this.iconOnlyToggled) {
        body.classList.add('sidebar-icon-only');
      } else {
        body.classList.remove('sidebar-icon-only');
      }
    } else {
      this.sidebarToggled = !this.sidebarToggled;
      if (this.sidebarToggled) {
        body.classList.add('sidebar-hidden');
      } else {
        body.classList.remove('sidebar-hidden');
      }
    }
  }

  // To get the detail of the user
  getUserDetail(userId: number) {
    
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/']);
  }

}

import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgbCollapse } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {
  @ViewChild('.settings .dropdown-menu', { static: true }) settingsDropdown!: ElementRef;
  
  public submenuStates: { [key: string]: boolean } = {};
  public secondSubmenuStates: { [key: string]: boolean } = {};
  
  sidebarMenus: any[] = [
    {
      menuName: 'Dashboard',
      menuIcon: 'mdi mdi-view-dashboard',
      menuRoute: '/dashboard',
      childMenus: []
    },
    {
      menuName: 'School',
      menuIcon: 'mdi mdi-school',
      menuRoute: '/school',
      childMenus: []
    },
    {
      menuName: 'Student Management',
      menuIcon: 'mdi mdi-account-group',
      childMenus: [
        { menuName: 'Student List', menuRoute: '/students/list' },
        { menuName: 'New Admission', menuRoute: '/students/admission' },
        { menuName: 'Attendance', menuRoute: '/students/attendance' }
      ]
    },
    {
      menuName: 'Exam Management',
      menuIcon: 'mdi mdi-clipboard-text',
      childMenus: [
        { menuName: 'Exam Schedule', menuRoute: '/exams/schedule' },
        { menuName: 'Create Exam', menuRoute: '/exams/create' },
        { menuName: 'Hall Allocation', menuRoute: '/exams/hall-allocation' }
      ]
    },
    {
      menuName: 'Result Management',
      menuIcon: 'mdi mdi-chart-bar',
      childMenus: [
        { menuName: 'Result Entry', menuRoute: '/results/entry' },
        { menuName: 'Result Analysis', menuRoute: '/results/analysis' },
        { menuName: 'Publish Results', menuRoute: '/results/publish' }
      ]
    },
    {
      menuName: 'Reports',
      menuIcon: 'mdi mdi-file-document',
      menuRoute: '/reports',
      childMenus: []
    },
    {
      menuName: 'Settings',
      menuIcon: 'mdi mdi-cog',
      menuRoute: '/settings',
      childMenus: []
    }
  ];

  constructor() {
    // Initialize submenu states
    this.sidebarMenus.forEach(menu => {
      if (menu.childMenus.length > 0) {
        this.submenuStates[menu.menuName] = false;
      }
    });
  }

  ngOnInit() {
    // Any initialization logic can go here
  }

  toggleSubmenuCollapse(menuName: string): void {
    this.submenuStates[menuName] = !this.submenuStates[menuName];
  }

  toggleSecondSubmenuCollapse(menuName: string): void {
    this.secondSubmenuStates[menuName] = !this.secondSubmenuStates[menuName];
  }
}
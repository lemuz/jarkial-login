import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';



@NgModule({
  declarations: [
    HeaderComponent,
    SidebarMenuCOmponent,
    LoadingComponent,
    YeNoPipe,
    ValidateFieldDirective,
    SortDirective,
    ResizeColumnDirective,
    NoDataComponent
  ],
  imports: [
    MatDialogModule,
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule
  ],
  exports: [
    HeaderComponent,
    SidebarMenuCOmponent,
    LoadingComponent,
    YeNoPipe,
    ValidateFieldDirective,
    SortDirective,
    ResizeColumnDirective,
    NoDataComponent
  ]
})
export class SharedModule { }

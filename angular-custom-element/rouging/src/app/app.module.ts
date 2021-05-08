import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HelloComponent } from './dashboard/hello/hello.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {PagiComponent} from './dashboard/pagi/pagi.component';

@NgModule({
  declarations: [
    AppComponent,
    HelloComponent,
    DashboardComponent,
    PagiComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

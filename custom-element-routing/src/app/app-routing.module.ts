import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from './app.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import { HelloComponent } from './dashboard/hello/hello.component';
import {PagiComponent} from './dashboard/pagi/pagi.component';

const routes: Routes = [
  { path: '', component: AppComponent },
  { path: 'hello', component: HelloComponent },
  { path: 'pagi', component: PagiComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { SpinnerService } from '../spinner.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss']
})
export class SpinnerComponent implements OnInit {

  public color = 'primary';
  public mode  = 'indeterminate';
  public value = 50;
  public isLoading: Subject<boolean> = this.spinnerService.isLoading;

  constructor(
    private spinnerService: SpinnerService
  ) { }

  ngOnInit(): void {
  }

}

import { TestBed } from '@angular/core/testing';

import { SpinnerInterceptor } from './spinner-interceptor';

describe('SpinnerInterceptor', () => {
  let service: SpinnerInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpinnerInterceptor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

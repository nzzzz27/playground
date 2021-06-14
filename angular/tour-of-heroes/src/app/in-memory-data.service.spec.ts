import { TestBed } from '@angular/core/testing';

import { InMemoryDataService } from './in-memory-data.service';
import { Hero } from './hero';

describe('InMemoryDataService', () => {
  let service: InMemoryDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InMemoryDataService);
  });

  it('should return Hero Id', () => {
    let hero: Hero[] = [{ id: 1, name: "sample Hero" }];
    expect(service.genId(hero)).toEqual(2);

    let heroesEmpty: Hero[] = [];
    expect(service.genId(heroesEmpty)).toEqual(11);

    let heroes: Hero[] = [
      { id: 1, name: "sample Hero" },
      { id: 2, name: "sample Hero" },
      { id: 3, name: "sample Hero" },
      { id: 4, name: "sample Hero" },
    ];
    expect(service.genId(heroes)).toEqual(5);
  });
});

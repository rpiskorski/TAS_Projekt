import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TheBestComponent } from './the-best.component';

describe('TheBestComponent', () => {
  let component: TheBestComponent;
  let fixture: ComponentFixture<TheBestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TheBestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TheBestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

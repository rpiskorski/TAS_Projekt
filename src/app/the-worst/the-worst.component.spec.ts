import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TheWorstComponent } from './the-worst.component';

describe('TheWorstComponent', () => {
  let component: TheWorstComponent;
  let fixture: ComponentFixture<TheWorstComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TheWorstComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TheWorstComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

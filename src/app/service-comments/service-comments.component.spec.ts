import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceCommentsComponent } from './service-comments.component';

describe('ServiceCommentsComponent', () => {
  let component: ServiceCommentsComponent;
  let fixture: ComponentFixture<ServiceCommentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceCommentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

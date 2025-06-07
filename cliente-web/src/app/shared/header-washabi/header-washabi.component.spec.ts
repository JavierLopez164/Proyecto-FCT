import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderWashabiComponent } from './header-washabi.component';

describe('HeaderWashabiComponent', () => {
  let component: HeaderWashabiComponent;
  let fixture: ComponentFixture<HeaderWashabiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderWashabiComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderWashabiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

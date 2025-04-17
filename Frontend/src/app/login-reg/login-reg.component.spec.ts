import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginRegComponent } from './login-reg.component';

describe('LoginRegComponent', () => {
  let component: LoginRegComponent;
  let fixture: ComponentFixture<LoginRegComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginRegComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginRegComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrettingstextComponent } from './grettingstext.component';

describe('GrettingstextComponent', () => {
  let component: GrettingstextComponent;
  let fixture: ComponentFixture<GrettingstextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrettingstextComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GrettingstextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

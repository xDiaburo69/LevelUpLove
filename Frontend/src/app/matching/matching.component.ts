import {
  Component,
  OnInit,
  AfterViewInit,
  ViewChild,
  ElementRef,
  Renderer2,
  HostListener
} from '@angular/core';

interface Profile {
  name: string;
  type: string;
  location: string;
  img: string;
}

@Component({
  selector: 'app-match-adventure',
  templateUrl: './matching.component.html',
  styleUrls: ['./matching.component.css']
})
export class MatchingComponent implements OnInit, AfterViewInit {

  profiles: Profile[] = [
    { name: 'Pikachu',  type: 'Elektro', location: 'Alabastia', img: 'https://placekitten.com/150/150' },
    { name: 'Glumanda', type: 'Feuer',   location: 'Lavandia', img: 'https://placekitten.com/150/151' },
    { name: 'Schiggy',  type: 'Wasser',  location: 'Azuria City', img: 'https://placekitten.com/150/152' }
  ];
  currentIndex = 0;
  current!: Profile;

  isDragging = false;
  startX = 0;

  @ViewChild('card', { static: true }) card!: ElementRef<HTMLDivElement>;
  @ViewChild('levelUp', { static: true }) levelUp!: ElementRef<HTMLDivElement>;
  @ViewChild('sadFace', { static: true }) sadFace!: ElementRef<HTMLDivElement>;
  @ViewChild('particles', { static: true }) particles!: ElementRef<HTMLDivElement>;

  constructor(private renderer: Renderer2) {}

  ngOnInit(): void {
    this.showProfile();
  }

  ngAfterViewInit(): void {
    // Initial styles
    this.resetCard();
  }

  showProfile(): void {
    this.current = this.profiles[this.currentIndex];
  }

  like(): void {
    this.swipe('right');
  }

  dislike(): void {
    this.swipe('left');
  }

  private swipe(direction: 'left'|'right'): void {
    const translateX = direction === 'right' ? 500 : -500;
    const rotate = direction === 'right' ? 20 : -20;
    this.renderer.setStyle(this.card.nativeElement, 'transition', 'transform 0.5s ease, opacity 0.5s ease');
    this.renderer.setStyle(this.card.nativeElement, 'transform', `translateX(${translateX}px) rotate(${rotate}deg)`);
    this.renderer.setStyle(this.card.nativeElement, 'opacity', '0');

    if (direction === 'right') {
      setTimeout(() => {
        this.triggerMatch();
        this.nextProfile();
      }, 500);
    } else {
      this.triggerSadFace();
      setTimeout(() => this.nextProfile(), 500);
    }
  }

  private triggerSadFace(): void {
    const el = this.sadFace.nativeElement;
    this.renderer.removeClass(el, 'hidden');
    this.renderer.addClass(el, 'show-sad');
    setTimeout(() => {
      this.renderer.removeClass(el, 'show-sad');
      this.renderer.addClass(el, 'hidden');
    }, 1500);
  }

  private triggerMatch(): void {
    if (Math.random() > 0.5) {
      const lvl = this.levelUp.nativeElement;
      this.renderer.removeClass(lvl, 'hidden');
      this.renderer.addClass(lvl, 'show-level-up');
      this.createParticles();
      setTimeout(() => {
        this.renderer.removeClass(lvl, 'show-level-up');
        this.renderer.addClass(lvl, 'hidden');
        this.clearParticles();
      }, 2500);
    }
  }

  private createParticles(): void {
    const container = this.particles.nativeElement;
    for (let i = 0; i < 30; i++) {
      const p = this.renderer.createElement('div');
      this.renderer.addClass(p, 'particle');
      this.renderer.setStyle(p, '--randX', Math.random());
      this.renderer.setStyle(p, '--randY', Math.random());
      this.renderer.appendChild(container, p);
    }
  }

  private clearParticles(): void {
    this.renderer.setProperty(this.particles.nativeElement, 'innerHTML', '');
  }

  private nextProfile(): void {
    this.currentIndex = (this.currentIndex + 1) % this.profiles.length;
    this.showProfile();
    this.resetCard();
  }

  private resetCard(): void {
    const el = this.card.nativeElement;
    this.renderer.setStyle(el, 'transition', 'none');
    this.renderer.setStyle(el, 'transform', 'translateX(0) rotate(0)');
    this.renderer.setStyle(el, 'opacity', '1');
  }

  // === Drag & Drop ===
  onMouseDown(event: MouseEvent): void {
    this.isDragging = true;
    this.startX = event.clientX;
    this.renderer.setStyle(this.card.nativeElement, 'transition', 'none');
  }

  @HostListener('document:mouseup', ['$event'])
  onMouseUp(event: MouseEvent): void {
    if (!this.isDragging) return;
    this.isDragging = false;
    const diffX = event.clientX - this.startX;
    if (diffX > 150) {
      this.swipe('right');
    } else if (diffX < -150) {
      this.swipe('left');
    } else {
      this.resetCard();
    }
  }

  @HostListener('document:mousemove', ['$event'])
  onMouseMove(event: MouseEvent): void {
    if (!this.isDragging) return;
    const diffX = event.clientX - this.startX;
    this.renderer.setStyle(
      this.card.nativeElement,
      'transform',
      `translateX(${diffX}px) rotate(${diffX / 20}deg)`
    );
  }
}

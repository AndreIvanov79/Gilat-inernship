import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FrameService } from '../frame.service';

@Component({
  selector: 'ship',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ship.component.html',
  styleUrl: './ship.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ShipComponent {
  constructor(private frameService: FrameService) {}

  top: number = 60;
  left: number = 20;
  rotation: number = 0;
  speedX: number = 0;
  speedY: number = 0;
  rotaitionSpeed: number = 0;
  width: number = 10;
  heght: number = 10;
  type: string = '';
}

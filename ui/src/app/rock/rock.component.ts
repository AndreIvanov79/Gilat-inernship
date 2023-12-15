import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FrameService } from '../frame.service';
import { Frame } from '../frame.service';

@Component({
  selector: 'rock',
  standalone: true,
  imports: [CommonModule],
  templateUrl: `./rock.component.html`,
  styleUrl: './rock.component.css',
})
export class RockComponent implements OnInit {

  constructor(private frameService: FrameService) {}

  top: number = 0;
  left: number = 0;
  rotation: number = 0;
  speedX: number = 0;
  speedY: number = 0;
  rotaitionSpeed: number = 0;
  width: number = 0;
  heght: number = 0;
  type: string = "";

  ngOnInit(): void {
    setInterval(() => {
    this.frameService.item.then((data) => {
          for (let i = 0; i < data.items.length; i++) {
            console.log('VAL: ', data.items[i]);
              this.top = data.items[i].top;
              this.left = data.items[i].left;
              this.rotation = data.items[i].rotation;
              this.heght = data.items[i].heght;
              this.width = data.items[i].width;
              this.speedX = data.items[i].speedX;
              this.speedY = data.items[i].speedY;
              this.rotaitionSpeed = data.items[i].rotaitionSpeed;
              this.type = data.items[i].type;
          }

    });
  }, 1000);
}
}

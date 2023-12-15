import { Injectable } from '@angular/core';

export interface Frame {
  top: number ;
  left: number;
  rotation: number;
  speedX: number;
  speedY: number;
  rotaitionSpeed: number;
  width: number ;
  heght: number;
  type: string ;
}


@Injectable({
  providedIn: 'root'
})
export class FrameService {

  constructor() { }

   item =  fetch('http://localhost:8080/update')
        .then((response) => response.json())
        .then((data) => {
          return data;
        });
    }





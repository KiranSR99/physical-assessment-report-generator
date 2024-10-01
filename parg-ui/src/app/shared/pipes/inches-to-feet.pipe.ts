import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'inchesToFeet'
})
export class InchesToFeetPipe implements PipeTransform {

  transform(value: number): string {
    if (!value || value < 0) {
      return 'Invalid input';
    }

    const feet = Math.floor(value / 12); // Calculate the number of feet
    const inches = value % 12;           // Calculate the remaining inches

    return `${feet} ft ${inches}"`;         // Return the result in "X' Y"" format
  }

}

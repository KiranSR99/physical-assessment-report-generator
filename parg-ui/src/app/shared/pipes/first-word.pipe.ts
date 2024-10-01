import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'firstWord'
})
export class FirstWordPipe implements PipeTransform {

  transform(value: string): string {
    if (!value) {
      return '';
    }
    // Split the string by spaces and return the first element
    const firstWord = value.split(' ')[0];
    return firstWord;
  }

}

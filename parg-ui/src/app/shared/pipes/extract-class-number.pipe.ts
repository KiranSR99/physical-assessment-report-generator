import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'extractClassNumber'
})
export class ExtractClassNumberPipe implements PipeTransform {

  transform(value: string): string | null {
    if (!value) {
      return null;
    }

    // Regex to match both numbers and Roman numerals
    const numberMatch = value.match(/\b\d+\b/);    // Matches numbers like 1, 2, 3
    const romanMatch = value.match(/\b[IVXLCDM]+\b/); // Matches Roman numerals like IV, V, VI

    if (numberMatch) {
      return numberMatch[0]; // If it's a number, return it
    } else if (romanMatch) {
      return romanMatch[0]; // If it's Roman numeral, return it
    }

    return null; // If no number or Roman numeral is found
  }
}

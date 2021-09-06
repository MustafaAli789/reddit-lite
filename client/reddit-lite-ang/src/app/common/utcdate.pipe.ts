import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'utcdate'
})
export class UtcdatePipe implements PipeTransform {

  transform(utcSecs: number | undefined, ...args: unknown[]): string {
    let a = new Date(0)
    a.setUTCSeconds(utcSecs ? utcSecs : 0)
    return a.toISOString().slice(0, 19).replace('T', ' ')
  }

}

import {ProjectApi} from '../components/projectapi';

export class Dataupload {
  static inject = [ProjectApi];
  constructor(pa) {
    this.pa = pa;
  }

}

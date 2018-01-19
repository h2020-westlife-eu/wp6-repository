import {ProjectApi} from '../components/projectapi';

export class Datasettable {
  static inject = [ProjectApi];

  constructor(pa) {
    this.pa=pa;
    this.alldatasets = [];
    this.showDatasets =true;
  }

  attached() {
    this.pa.getDatasets().then(data => {
      this.alldatasets = data;
    });
  }

}

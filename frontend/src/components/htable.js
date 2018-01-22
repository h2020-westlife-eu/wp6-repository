import {Handsontable} from "handsontable";

export class htable {
  attached(){
    this.data = [[1,2],[1,3]];
    this.ht = new Handsontable(this.idtable, {
      data: this.data,
      rowHeaders: true,
      colHeaders: true
    });
  }
}

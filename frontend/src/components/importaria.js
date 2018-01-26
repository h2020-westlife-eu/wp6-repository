import {Ariaapi} from '../components/ariaapi';

/* Dashboard receives list of projects and list of datasets, in case the project or dataset is selected either by click or within url it is filtered */
export class Importaria {
  static inject = [Ariaapi];
  constructor(ariaapi){
    this.ariaapi = ariaapi;
    this.ariaurl = "";
    this.showspinner=true;
  }

  attached () {
    this.ariaapi.getAriaLink().then(
      linkdata => {
        console.log('importaria.attached() recieved linkdata');
        console.log(linkdata);
        this.ariaurl = linkdata.url;
        this.showspinner=false;
      }
    )
    //this.ariaurl=this.aoaservice+

  }
}

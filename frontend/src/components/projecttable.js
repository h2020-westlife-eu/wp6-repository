import {Webdavresource} from "../components/messages";
import {ProjectApi} from './projectapi';

export class Projecttable {
  static inject = [ProjectApi];

  constructor(pa) {
    this.pa=pa;
    this.projects = [];
    this.showProposals=true;
  }

  attached() {
    console.log("projecttableattached()")
    this.pa.getProjects().then(data => {
      this.projects = data;
    });
  }
}

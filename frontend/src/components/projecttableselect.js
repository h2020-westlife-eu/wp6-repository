import {EventAggregator} from 'aurelia-event-aggregator';
import {ProjectApi} from './projectapi';
import {Selectedproject} from "../components/messages";

export class Projecttableselect {
  static inject = [ProjectApi,EventAggregator];

  constructor(pa,ea) {
    this.pa = pa;
    this.ea=ea;
    this.projects = [];
    this.selectedProject=null;
  }

  attached() {
    console.log("projecttableattached()")
    this.pa.getProjects().then(data => {
      this.projects = data;
    });
  }

  selectProject(project){
    this.selectedProject=project;
    this.ea.publish(new Selectedproject(project))

  }
  deselectProject(){
    this.selectedProject=null;
    this.ea.publish(new Selectedproject(null))
  }


}

import {Webdavresource} from "../components/messages";
import {ProjectApi} from './projectapi';
import {EventAggregator} from 'aurelia-event-aggregator';
import {Selectedproject,Preselectedproject} from "./messages";

export class Projecttable {
  static inject = [ProjectApi,EventAggregator];

  constructor(pa,ea) {
    console.log("Projecttable()");
    this.pa = pa;
    this.ea=ea;
    this.projects = [];
    this.selectedProject=null;
    this.selectedProjectId=0;
    console.log("Projecttable() subscribe");
    this.ea.subscribe(Preselectedproject,msg =>this.filterSelectedProposal(msg.projectid));
  }

  attached() {
    console.log("projecttableattached()")
    this.pa.getProjects().then(data => {
      this.projects = data;
      //data retrieved and selectedProject already set e.g. by activate, call filter again
      this.selectedProjectId=this.pa.getSelectedProject();
      if (this.selectedProjectId>0) this.filterSelectedProposal(this.selectedProjectId);
    });
  }

  /* it is triggered in first click on project - url is generated
* or when url is submitted directly to browser
  activate(params, routeConfig, navigationInstruction) {
    console.log("Visitingscientist activate()")
    if (params && params.projectid) {
      this.filterSelectedProposal(params.projectid);
    }
  }
*/
  /* this is used when new project proposal is selected - it filters project by id and datasets by id*/
  filterSelectedProposal(id) {
    //this.selectedProposal=item;
    this.selectedProjectId=id;
    console.log("projecttable.filterProject()");
    console.log(this.projects);
    console.log(id);
    if (this.projects.length>0) {
      this.selectedProject = this.projects.filter(i => i.id == id)[0];
    } else {
      console.log("projects are not yet retrieved");
    }
  }

  selectProject(project){
    console.log("selectProject()");
    this.selectedProject=project;
    this.ea.publish(new Selectedproject(project))
    return true;
  }

  deselectProject(){
    console.log("deselectProject()");
    this.selectedProject=null;
    this.ea.publish(new Selectedproject(null))
    return true;
  }


}

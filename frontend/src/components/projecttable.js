import {ProjectApi} from './projectapi';
import {EventAggregator} from 'aurelia-event-aggregator';
import {Selectedproject,FilterProject,FilterProjectByDataset,Addproject} from "./messages";
import {bindable} from 'aurelia-framework';

export class Projecttable {
  static inject = [ProjectApi,EventAggregator];
  @bindable userid;

  constructor(pa,ea) {
    console.log("Projecttable()");
    this.pa = pa;
    this.ea=ea;
    this.projects = [];
    this.selectedProject=null;
    this.selectedProjectId=0;
    console.log("Projecttable() subscribe");
    this.ea.subscribe(FilterProject,msg =>this.filterSelectedProposal(msg.id));
    this.ea.subscribe(Addproject,msg =>this.addProposal(msg.project));
    this.ea.subscribe(FilterProjectByDataset,msg =>this.filterSelectedProposal(msg.id));
    console.log("Projecttable() subscribe 2");
  }

  bind() {
    console.log("projecttable bind()",this.userid);
    this.pa.getProjects(this.userid).then(data => {
      this.projects = data;
      //data retrieved and selectedProject already set e.g. by activate, call filter again
      this.selectedProjectId=this.pa.getSelectedProject();
      console.log(this.selectedProjectId);
      if (this.selectedProjectId>0) this.filterSelectedProposal(this.selectedProjectId);
    });
  }


  /* this is used when new project proposal is selected - it filters project by id and datasets by id*/
  filterSelectedProposal(id) {
    //this.selectedProposal=item;
    this.selectedProjectId=id;
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

  addProposal(pr) {
    console.log("addproposal()",pr);
    this.projects.unshift(pr)
  }


}

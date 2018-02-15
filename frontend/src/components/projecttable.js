import {Webdavresource} from "../components/messages";
import {ProjectApi} from './projectapi';

export class Projecttable {
  static inject = [ProjectApi];

  constructor(pa) {
    this.pa = pa;
    this.projects = [];
    this.showProposals = true;
  }

  attached() {
    console.log("projecttableattached()")
    this.pa.getProjects().then(data => {
      this.projects = data;
    });
  }

  /* it is triggered in first click on project - url is generated
* or when url is submitted directly to browser */
  activate(params, routeConfig, navigationInstruction) {
    console.log("Visitingscientist activate()")
    if (params && params.projectid) {
      this.filterSelectedProposal(params.projectid);
    }
  }

  /* this is used when new project proposal is selected - it filters project by id and datasets by id*/
  filterSelectedProposal(id) {
    //this.selectedProposal=item;
    this.selectedProjectId=id;
    this.filterProject();
    this.filterDataset();
  }
  /* helper class called from filterSelecterProposal - or when attached() so activate() call before didn't have
  * the projects and datasets sets from REST api*/
  filterProject(){
    console.log("filterProject()");
    console.log(this.projects);
    console.log(this.selectedProjectId);
    if (this.projects.length>0) {
      this.selectedProject = this.projects.filter(i => i.id == this.selectedProjectId)[0];
      //this.filterDataset();
      this.showProposals = false;
    }
  }

  //filters dataset based on selectedProjectId
  filterDataset(){
    console.log("filterDataset()");
    console.log(this.alldatasets);
    this.datasets = this.alldatasets.filter(filtereditem => filtereditem.projectId == this.selectedProjectId);
    this.emptyDatasets = this.datasets.length == 0;
    console.log(this.datasets);
  }

}

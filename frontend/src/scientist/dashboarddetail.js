import {Selectedproject, Selecteddataset, Webdavresource, FilterDataset,FilterProject,FilterDatasetByProject} from "../components/messages";
import {EventAggregator} from 'aurelia-event-aggregator';
import {ProjectApi} from '../components/projectapi';

/* Dashboarddetails shows details of projects/datasets */
export class Dashboarddetail {
  static inject = [EventAggregator,ProjectApi];

  constructor(ea,pa) {
    this.ea = ea;
    this.pa=pa;
    console.log("subscribe()");
    this.ea.subscribe(Selectedproject,msg =>this.selectProject(msg.project));
    this.ea.subscribe(Selecteddataset,msg => this.selectDataset(msg.dataset));
  }

  /* it is triggered on first click on project - url is generated
  * or when url is submitted directly to browser */
  activate(params, routeConfig, navigationInstruction){
    console.log("dashboarddetail.activate()")
    if (params && params.projectid) {
      console.log("dashboarddetail projectid:"+params.projectid);
      this.filterProject(params.projectid);
    }
    if (params && params.datasetid) {
      console.log("dashboarddetail datasetid:"+params.datasetid);
      this.filterDataset(params.datasetid);
    }
  }

  /* triggered when the view is shown to user - it requests projects and dataset
  * available for user from REST api*/
  attached() {
    console.log("Dashboard.attached()")
    this.selectedDataset=this.pa.getSelectedDataset()>0;
    //console.log(this.pa);
  }

  //triggered when a project is clicked -
  // if the same project is selected activate() is not launched - enough to hide all other projects
  selectProject(project) {
    this.selectedProject=project;
    this.selectedProjectId=project.id;
    this.pa.setSelectedProject(projectid);
    this.ea.publish(new FilterDatasetByProject(project.id));
    return true;
  }

  /* helper class called from filterSelecterProposal - or when attached() so activate() call before didn't have
  * the projects and datasets sets from REST api*/
  filterProject(projectid){
    console.log("dashboarddetail.filterProject()");
    this.pa.setSelectedProject(projectid);
    this.ea.publish(new FilterProject(projectid));
    this.ea.publish(new FilterDatasetByProject(projectid));
  }

  //filters dataset based on selectedProjectId
  filterDataset(datasetid){
    console.log("dashboarddetail.filterDataset()");
    this.pa.setSelectedDataset(datasetid);
    this.ea.publish(new FilterDataset(datasetid));
  }

  //shows only one dataset and publish webdavresource with dataset's url
  selectDataset(item) {
    console.log("received selectDataset signal");
    console.log(item);
    this.selectedDataset=item;
    if (!item) {deselectDataset(); return true;}
    this.selectedDatasetId=item.id;
    console.log("selectDataset");
    console.log(item);

    this.pa.dataseturl=item.webdavurl;
    this.ea.publish(new Webdavresource(item.webdavurl))
    return true; //continues to process <a href>
  }


}

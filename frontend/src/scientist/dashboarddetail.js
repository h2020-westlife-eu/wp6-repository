import {Selectedproject, Selecteddataset, Webdavresource, FilterDataset,FilterProject,FilterDatasetByProject,FilterProjectByDataset} from "../components/messages";
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
    this.filterSelectedProposal(project.id);
    return true;
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
    //this.ea.publish(new FilterProjectByDataset()) -- call from filterDataset -
  }

  //deselect project proposal, shows all proposals and datasets
  deselectProposal() {
    //this.selectedProposal=item;
    //this.datasets = this.alldatasets;
    //return true; //continues to process <a href>
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

    //TODO replace URL by the one obtained from API
    this.pa.dataseturl=item.webdavurl;
    this.ea.publish(new Webdavresource(item.webdavurl))
    return true; //continues to process <a href>
  }

  selectFile(file){
    console.log("SelectFile()");
    console.log(file);
  }

  deleteDataset() {
    this.pa.deleteDataset(this.selectedDatasetId)
    then(data => {
      this.deselectDataset();
      let i=this.datasets.map(function(e) {return e.id;}).indexOf(data.id);
      this.datasets.splice(i,1);
      i=this.alldatasets.map(function(e) {return e.id;}).indexOf(data.id);
      this.alldatasets.splice(i,1);
    })
  }
}

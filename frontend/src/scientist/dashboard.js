import {Webdavresource} from "../components/messages";
import {EventAggregator} from 'aurelia-event-aggregator';
import {ProjectApi} from '../components/projectapi';

/* Dashboard receives list of projects and list of datasets, in case the project or dataset is selected either by click or within url it is filtered */
export class Dashboard {
  static inject = [EventAggregator,ProjectApi];

  constructor(ea,pa) {
    this.ea = ea;
    this.pa=pa;

    this.projects = [];

    this.files=[];
    this.datasets = [];
    this.alldatasets = [];
    this.showProposals=true;
    this.showDatasets=true;
    this.dataseturl="";
    this.selectedProjectId=0;
    this.selectedDatasetId=0;
    this.selectedProject={};
  }

  /* it is triggered in first click on project - url is generated
  * or when url is submitted directly to browser */
  activate(params, routeConfig, navigationInstruction){
    //console.log("Visitingscientist activate()")
    if (params && params.projectid) {
      console.log("Activate() with selectedProject");
      console.log(params.projectid);
      this.filterSelectedProposal(params.projectid);
    }
    if (params && params.datasetid) {
      console.log("Activate() with selectedDataset");
      console.log(params.datasetid);
      this.filterSelectedDataset(params.datasetid);
    }
  }

  /* triggered when the view is shown to user - it requests projects and dataset
  * available for user from REST api*/
  attached() {
    //console.log("Dashboard.attached(): pa:")
    //console.log(this.pa);
    this.pa.getProjects().then(data => {
          console.log("attached(), getProjects():");
          this.projects = data;//this.proposalsall.slice(0,3);
          if (this.selectedProjectId>0) {this.filterProject()}
      });
    this.pa.getDatasets().then(data => {
          this.alldatasets = data;       
          if (this.selectedDatasetId>0) {this.filterMyDataset()} //triggered when accessed by url dashboard/dataset/1
          else 
          if (this.selectedProjectId>0) {this.filterDataset()}  //triggered when accessed by url dashboard/project/1
          //  this.datasets = this.alldatasets.filter(filtereditem => filtereditem.projectId == this.selectedProjectId);
    });
  }

  //triggered when a project is clicked -
  // if the same project is selected activate() is not launched - enough to hide all other projects
  selectProposal(id) {
    this.showProposals=false;
    this.showDatasets=true;
    return true;
  }

  /* this is used when new project proposal is selected - it filters project by id and datasets by id*/
  filterSelectedProposal(id) {
    //this.selectedProposal=item;
    console.log("filterSelectProposal()");
    console.log(id);
    this.selectedProjectId=id;
    this.filterProject();
    this.filterDataset();
  }

  filterSelectedDataset(id) {
    //this.selectedProposal=item;
    console.log("filterSelectDataset()");
    console.log(id);
    this.selectedDatasetId=id;
    //this.filterMyProject();
    this.filterMyDataset();
  }
  
  //expects that selectedDatasetId contains dataset which should be viewed, based on it it selects projectid
  //filters project which is owning the dataset and publish webdavresource event with dataset webdavurl
  filterMyDataset(){
    this.datasets= this.alldatasets.filter(i=> i.id === this.selectedDatasetId);
    if (this.datasets.length>0){
      this.selectedProjectId=this.datasets[0].projectId;
      this.filterProject();
      this.showProposals=false;
      this.selectDataset(this.datasets[0]);
    }
  }

  /* helper class called from filterSelecterProposal - or when attached() so activate() call before didn't have
  * the projects and datasets sets from REST api*/
  filterProject(){
    this.selectedProject=this.projects.filter(i=>i.id==this.selectedProjectId)[0];
    this.showProposals = false;
    //console.log(this.selectedProject);
  }

  //filters dataset based on selectedProjectId
  filterDataset(){
    this.datasets = this.alldatasets.filter(filtereditem => filtereditem.projectId === this.selectedProjectId);
    //console.log(this.datasets);
  }

  //deselect project proposal, shows all proposals and datasets
  deselectProposal() {
    //this.selectedProposal=item;
    this.datasets = this.alldatasets;
    this.showProposals = true;
    return true; //continues to process <a href>
  }

  //shows only one dataset and publish webdavresource with dataset's url
  selectDataset(item) {
    this.showDatasets=false;
    this.selectedDataset=item;
    //TODO replace URL by the one obtained from API
    this.dataseturl=item.webdavurl;
    this.ea.publish(new Webdavresource(item.webdavurl))
    return true; //continues to process <a href>
  }

  deselectDataset() {
    this.showDatasets=true;
  }

  selectFile(file){
    console.log("SelectFile()");
    console.log(file);
    //not yet implemented
  }
}

import {Webdavresource} from "../components/messages";
import {EventAggregator} from 'aurelia-event-aggregator';
import {ProjectApi} from '../components/projectapi';

/* Dashboarddetails shows details of projects/datasets */
export class Dashboarddetail {
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
    console.log("Visitingscientist activate()")
    if (params && params.projectid) {
      this.filterSelectedProposal(params.projectid);
    }
    if (params && params.datasetid) {
      this.filterSelectedDataset(params.datasetid);
    }
  }

  /* triggered when the view is shown to user - it requests projects and dataset
  * available for user from REST api*/
  attached() {
    console.log("Dashboard.attached(): pa:")
    console.log(this.pa);
    this.pa.getProjects().then(data => {
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
    this.selectedProjectId=id;
    this.filterProject();
    this.filterDataset();
  }

  filterSelectedDataset(id) {
    //this.selectedProposal=item;
    this.selectedDatasetId=id;
    //this.filterMyProject();
    this.filterMyDataset();
  }
  
  //expects that selectedDatasetId contains dataset which should be viewed, based on it it selects projectid
  //filters project which is owning the dataset and publish webdavresource event with dataset webdavurl
  filterMyDataset(){
    //get the selected dataset
    console.log("filterMyDataset()");
    console.log(this.alldatasets);
    let mydataset= this.alldatasets.filter(i=> i.id == this.selectedDatasetId);
    console.log(mydataset);
    if (mydataset.length>0){
      //get project related to dataset
      this.selectedProjectId=mydataset[0].projectId;
      console.log(this.selectedProjectId);
      //filter datasets of the project
      this.datasets=this.alldatasets.filter(i => i.projectId == this.selectedProjectId);
      console.log("datasets:");
      console.log(this.datasets);
      this.showProposals=false;
      this.filterProject();
      this.selectDataset(mydataset[0]);
    }
  }

  /* helper class called from filterSelecterProposal - or when attached() so activate() call before didn't have
  * the projects and datasets sets from REST api*/
  filterProject(){
    console.log("filterProject()");
    console.log(this.projects);
    if (this.projects.length>0) {
      this.selectedProject = this.projects.filter(i => i.id == this.selectedProjectId)[0];
      this.showProposals = false;
    }
    console.log(this.selectedProject.summary);
  }

  //filters dataset based on selectedProjectId
  filterDataset(){
    this.datasets = this.alldatasets.filter(filtereditem => filtereditem.projectId === this.selectedProjectId);
    console.log(this.datasets);
  }

  //deselect project proposal, shows all proposals and datasets
  deselectProposal() {
    //this.selectedProposal=item;
    this.datasets = this.alldatasets;
    this.showProposals = true;
    this.showDatasets = true;
    //return true; //continues to process <a href>
  }

  //shows only one dataset and publish webdavresource with dataset's url
  selectDataset(item) {
    console.log("selectDataset");
    console.log(item);
    this.showDatasets=false;
    this.selectedDataset=item;
    //TODO replace URL by the one obtained from API
    this.dataseturl=item.webdavurl;
    this.ea.publish(new Webdavresource(item.webdavurl))
    return true; //continues to process <a href>
  }

  deselectDataset() {
    console.log("deselectDataset()");
    console.log(this.datasets);
    console.log(this.alldatasets);
    this.showDatasets=true;
  }

  selectFile(file){
    console.log("SelectFile()");
    console.log(file);
  }
}

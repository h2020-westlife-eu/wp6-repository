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

  /* triggered when the view is shown to user - it requests projects and dataset
  * available for user from REST api*/
  attached() {
    //console.log("Dashboard.attached(): pa:")
    //console.log(this.pa);
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

}

import {ProjectApi} from '../components/projectapi';
import {EventAggregator} from 'aurelia-event-aggregator';
import {
  Selecteddataset, FilterDataset, FilterDatasetByProject, FilterProjectByDataset,
  Webdavresource
} from "./messages";

export class Datasettable {
  static inject = [ProjectApi,EventAggregator];

  constructor(pa,ea) {
    this.pa=pa;
    this.alldatasets = [];
    //this.showDatasets =true;
    this.ea=ea;
    this.ea.subscribe(FilterDataset,msg =>this.filterSelectedDataset(msg.id));
    this.ea.subscribe(FilterDatasetByProject,msg =>this.filterProjectDatasets(msg.id));
    this.selectedDatasetId=0;
    this.selectedProjectId=0;
  }

  attached() {
    console.log("datasettable attached()");
    this.pa.getDatasets().then(data => {
      this.alldatasets = data;
      this.datasets=this.alldatasets;
      this.selectedDatasetId=this.pa.getSelectedDataset();
      if (this.selectedDatasetId>0) this.filterSelectedDataset(this.selectedDatasetId);
      if (this.selectedProjectId>0) this.filterProjectDatasets2(this.selectedProjectId);
    });
  }
/*
  activate(params, routeConfig, navigationInstruction){
    console.log("Visitingscientist activate()")
    if (params && params.datasetid) {
      this.filterSelectedDataset(params.datasetid);
    }
  }
  */

  selectDataset(dataset){
    console.log("selectDataset() sending signal");
    console.log(dataset)
    this.selectedDataset=dataset;
    this.ea.publish(new Selecteddataset(dataset));
    //if (dataset) this.ea.publish(new Webdavresource(dataset.webdavurl));
    return true;

  }
  deselectDataset(){

    console.log("deselectDataset()");
    this.filterProjectDatasets(this.selectedDataset.projectId);
    this.ea.publish(new Selecteddataset(null))
    return true;
  }


  filterProjectDatasets2(id) {
    //this.selectedProposal=item;
    //this.selectedDatasetId=id;
    //this.filterMyProject();
    //get the selected dataset
    this.selectedDataset=null;
    this.selectedDatasetId=0;
    this.selectedProjectId=id;
    if (id==0) {
      this.datasets=this.alldatasets;
      return;
    }
    console.log("datasettable.filterProjectDatasets()");
    console.log(id);
    console.log(this.alldatasets);
    this.datasets=this.alldatasets.filter(i => i.projectId == id);
    console.log(this.datasets)
  }

  filterProjectDatasets(id){
    this.filterProjectDatasets2(id);
  }

  filterSelectedDataset(id){
    this.selectedDatasetId=id;
    if (id==0){
      this.selectedDataset=null;
      this.datasets=this.alldatasets;
      return;
    }
    this.datasets=this.alldatasets.filter(i => i.id == id);
    if (this.datasets.length>0){
      this.selectedDataset=this.datasets[0];
      this.pa.setSelectedProject(this.datasets[0].projectId);
      this.ea.publish(new FilterProjectByDataset(this.datasets[0].projectId));
      this.pa.dataseturl=this.datasets[0].webdavurl;
      this.ea.publish(new Webdavresource(this.pa.dataseturl));
    }
  }



}
